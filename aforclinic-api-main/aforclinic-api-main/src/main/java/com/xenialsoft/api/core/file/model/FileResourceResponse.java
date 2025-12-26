package com.xenialsoft.api.core.file.model;

import java.time.LocalDateTime;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileResourceResponse {

    /**
     * 파일 식별자
     */
    private String id;

    /**
     * 파일명
     */
    private String filename;

    /**
     * 파일 경로
     */
    private String path;

    /**
     * 파일 크기 (바이트 단위)
     */
    private long size;

    /**
     * 파일 생성 일자
     */
    private LocalDateTime createdAt;

    /**
     * 파일 수정 일자
     */
    private LocalDateTime modifiedAt;

    /**
     * 파일의 콘텐츠 타입 (MIME 타입)
     */
    @Setter
    private String contentType;

    /**
     * 파일 리소스
     */
    @Setter
    private Resource resource;

    /**
     * 데이터로 변환
     * 
     * @param file
     * @return
     */
    public static FileResourceResponse from(FileMetadataResponse file) {
        FileResourceResponse data = new FileResourceResponse();
        data.id = file.getId();
        data.filename = file.getFilename();
        data.size = file.getSize();
        data.createdAt = file.getCreatedAt();
        return data;
    }

}
