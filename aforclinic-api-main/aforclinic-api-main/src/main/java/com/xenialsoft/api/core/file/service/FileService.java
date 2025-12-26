package com.xenialsoft.api.core.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.util.IdUtils;
import com.xenialsoft.api.config.properties.StorageProperties;
import com.xenialsoft.api.core.file.mapper.FileMetadataMapper;
import com.xenialsoft.api.core.file.model.FileMetadata;
import com.xenialsoft.api.core.file.model.FileMetadataPageRequest;
import com.xenialsoft.api.core.file.model.FileMetadataResponse;
import com.xenialsoft.api.core.file.model.FileResourceResponse;
import com.xenialsoft.api.core.file.model.FileUploadResponse;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일 저장 및 관리 서비스를 담당하는 클래스입니다.
 * <p>
 * 이 서비스는 파일을 저장할 때, 파일의 해시값을 기반으로 기존에 저장된 파일이 있는지 확인하고, 이미 존재하는 파일이라면 해당 파일의
 * 정보를 반환하며, 없으면 새로운 파일을 저장합니다.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final StorageProperties properties;

    private final FileMetadataMapper mapper;

    @Transactional(readOnly = true)
    public Optional<FileMetadataResponse> getFileMetadataById(String id) {
        return Optional.ofNullable(mapper.selectById(id)).map(FileMetadataResponse::from);
    }

    @Transactional(readOnly = true)
    public List<FileMetadataResponse> getFileMetadataList(FileMetadataPageRequest search, ApiPageRequest page) {
        // @formatter:off
        return mapper.selectList(search, page)
                .stream()
                .map(FileMetadataResponse::from)
                .toList();
        // @formatter:on
    }

    @Transactional(readOnly = true)
    public long getFileMetadataTotalCount(FileMetadataPageRequest search) {
        return mapper.selectTotalCount(search);
    }

    @Transactional(readOnly = true)
    public FileResourceResponse loadFile(String id) {

        FileMetadataResponse metadata = getFileMetadataById(id).orElseThrow(() -> {
            throw new ServiceException("File Metadata not found");
        });
        FileResourceResponse response = FileResourceResponse.from(metadata);

        Path path = Paths.get(metadata.getPath());
        Resource resource = new FileSystemResource(path);
        if (resource.exists() && resource.isReadable()) {
            response.setResource(resource);
        } else {
            throw new ServiceException("File Resource not found");
        }

        try {
            String contentType = Files.probeContentType(path);
            response.setContentType(contentType != null ? contentType : "application/octet-stream");
        } catch (IOException e) {
            log.error("Failed to determine content type for file: {}", response.getPath(), e);
            response.setContentType("application/octet-stream");
        }

        return response;
    }

    /**
     * 여러 개의 파일을 저장합니다.
     * <p>
     * 파일 리스트가 비어있거나 {@code null}인 경우에는 빈 리스트를 반환합니다.
     * </p>
     * 
     * @param files 저장할 파일들의 목록
     * @return 저장된 파일들의 {@link FileResourceResponse} 리스트
     */
    @Transactional
    public List<FileUploadResponse> storeFile(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }
        return files.stream().map(this::storeFile).toList();
    }

    /**
     * 하나의 파일을 저장합니다.
     * <p>
     * 저장할 파일의 해시값을 생성하여 이미 저장된 파일이 있는지 확인하고, 이미 존재하는 파일이라면 해당 정보를 반환하고,
     * 
     * 없으면 새로 저장한 후 반환합니다.
     * </p>
     * 
     * @param file 저장할 파일
     * @return 저장된 파일의 {@link FileMetadataResponse}
     * @throws IllegalArgumentException  파일이 {@code null}이거나 비어 있는 경우
     * @throws FileUploadFailedException 파일 업로드 중 오류가 발생한 경우
     */
    @Transactional
    public FileUploadResponse storeFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }

        // 파일명
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            throw new IllegalArgumentException("File name cannot be null");
        }

        // 파일 크기
        long size = file.getSize();

        // 메타데이터
        FileMetadata metadata = new FileMetadata();
        metadata.setId(IdUtils.generate());
        metadata.setFilename(filename);
        metadata.setSize(size);

        try {

            // 해시
            String hash = generateSHA256(file);

            // 경로
            // @formatter:off
            Path dir = Paths.get(properties.getRootPath(),
                    hash.substring(0, 2),
                    hash.substring(2, 4));
            // @formatter:on

            // 경로 확인 및 생성
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }

            // 파일 경로
            Path dest = dir.resolve(hash + "." + FilenameUtils.getExtension(filename));

            // 파일 확인 및 생성(중복 저장하지 않음)
            if (Files.notExists(dest)) {
                file.transferTo(dest);
            }

            metadata.setPath(dest.toString());

        } catch (NoSuchAlgorithmException | IOException e) {
            throw new ServiceException("Failed to upload file due to IO or hashing error.");
        }

        // 파일 메타데이터 저장
        int count = mapper.insert(metadata);
        if (count != 1) {
            throw new ServiceException("Failed to save file metadata.");
        }

        // DB 동기화를 위해 조회하여 반환
        metadata = mapper.selectById(metadata.getId());

        return FileUploadResponse.from(metadata);
    }

    /**
     * 주어진 파일의 SHA-256 해시값을 생성합니다.
     * <p>
     * 이 메서드는 {@link MultipartFile} 객체를 읽어들여, 해당 파일의 SHA-256 해시값을 계산하여 반환합니다. 반환되는
     * 해시값은 16진수 형식의 문자열입니다.
     * </p>
     * 
     * @param file 해시값을 계산할 파일
     * @return 파일의 SHA-256 해시값을 나타내는 16진수 문자열
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    private String generateSHA256(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        // SHA-256 해시 알고리즘을 사용한 MessageDigest 객체 생성
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (InputStream is = file.getInputStream()) {
            byte[] bytes = new byte[1024];
            int br;

            // 파일을 읽어서 해시값 계산
            while ((br = is.read(bytes)) != -1) {
                digest.update(bytes, 0, br);
            }

        }

        // 해시값을 바이트 배열로 받아 Hex 형식으로 변환
        byte[] bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
