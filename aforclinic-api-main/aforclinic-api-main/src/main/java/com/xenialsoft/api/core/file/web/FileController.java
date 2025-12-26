package com.xenialsoft.api.core.file.web;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.file.model.FileMetadataPageRequest;
import com.xenialsoft.api.core.file.model.FileMetadataResponse;
import com.xenialsoft.api.core.file.model.FileResourceResponse;
import com.xenialsoft.api.core.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    /**
     * 파일 목록을 검색 조건과 페이징 정보를 기반으로 조회합니다.
     * 
     * @param search 검색 조건을 담고 있는 {@link FileMetadataPageRequest} 객체. 검색할 파일의 이름
     *               등을 포함한 필터 정보를 담고 있습니다.
     * @param page   페이지 번호를 나타내는 파라미터로, 기본값은 1입니다. 클라이언트에서 요청한 페이지 번호이며, 내부적으로 0부터
     *               시작하는 인덱스로 변환됩니다.
     * @param size   페이지당 파일 개수를 나타내는 파라미터로, 기본값은 10입니다. 클라이언트에서 요청한 페이지 크기입니다.
     * 
     * @return {@link ResponseEntity} 파일 목록을 포함하는 페이지 응답을 반환합니다. 응답은
     *         `ApiPagedResponse.ok()`를 사용하여 처리되며, `FileResponse` 객체 목록과 페이지 관련
     *         메타데이터를 포함한 응답을 생성합니다.
     * 
     * @see ApiPagedResponse#ok(List, long, Pageable)
     */
    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getPagedFileList(
            // 조회 정보
            @ModelAttribute FileMetadataPageRequest search,
            // 페이지 정보
            @ModelAttribute ApiPageRequest page) {

        // 검색 조건으로 데이터 총 개수 조회
        long totalCout = service.getFileMetadataTotalCount(search);

        // 데이터가 없으면 페이지 정보만 반환
        if (totalCout == 0) {
            return ApiPagedResponse.ok(page);
        }

        // 검색 조건과 페이징 정보를 기반으로 파일 목록 조회
        List<FileMetadataResponse> content = service.getFileMetadataList(search, page);

        // 응답 반환
        return ApiPagedResponse.ok(content, totalCout, page);
    }

    /**
     * 여러 파일을 업로드합니다.
     * 
     * @param files 업로드할 파일들의 목록을 담고 있는 {@link List} 객체입니다. 각 파일은
     *              {@link MultipartFile} 객체로 제공되며, 클라이언트에서 여러 파일을 동시에 전송할 수 있습니다.
     * 
     * @return {@link ResponseEntity} 업로드된 파일들의 처리 결과를 반환합니다. 성공적으로 업로드된 파일들의 메타데이터를
     *         포함한 응답을 반환합니다.
     * 
     * @throws IllegalArgumentException 파일 목록이 비어 있을 경우 예외를 발생시킵니다. 업로드할 파일이 하나도 없는
     *                                  경우에 해당됩니다.
     */
    @PostMapping
    public ResponseEntity<?> upload(@RequestPart List<MultipartFile> files) {

        // 파일 목록이 없다면 오류 처리
        if (files.isEmpty()) {
            throw new IllegalArgumentException("No files provided");
        }

        return ApiResponse.ok(service.storeFile(files));
    }

    /**
     * 지정된 ID에 해당하는 파일을 다운로드합니다.
     * 
     * @param id          다운로드할 파일의 고유 ID입니다.
     * @param disposition 파일 다운로드 방식을 지정하는 파라미터로, 기본값은 "inline"입니다. "inline"은 브라우저
     *                    내에서 파일을 표시하려는 경우 사용되며, "attachment"는 파일을 다운로드하려는 경우 사용됩니다.
     * 
     * @return {@link ResponseEntity} 파일을 다운로드할 수 있는 응답을 반환합니다. {@link HttpHeaders}에
     *         파일 이름 및 다운로드 방식을 설정하고, 파일의 리소스를 {@link FileResponse} 객체에서 가져와 반환합니다.
     * 
     * @throws FileDownloadFailedException 파일 다운로드 중 오류가 발생한 경우 예외를 발생시킵니다.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> download(@PathVariable String id,
            @RequestParam(defaultValue = "inline") String disposition) {

        FileResourceResponse file = service.loadFile(id);

        String filename = UriUtils.encode(file.getFilename(), StandardCharsets.UTF_8);
        String contentDisposition = String.format("%s; filename=\"%s\"", disposition, filename);

        String contentType = file.getContentType();
        MediaType mediaType = MediaType.parseMediaType(contentType);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        // @formatter:off
        return ResponseEntity.ok()
                .contentType(mediaType)
                .headers(headers)
                .body(file.getResource());
        // @formatter:on
    }

}
