package com.xenialsoft.api.core.file.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileMetadataResponse {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 데이터로 변환
     * 
     * @param file
     * @return
     */
    public static FileMetadataResponse from(FileMetadata file) {
        FileMetadataResponse data = new FileMetadataResponse();
        data.id = file.getId();
        data.filename = file.getFilename();
        data.path = file.getPath();
        data.size = file.getSize();
        data.createdAt = file.getCreatedAt();
        return data;
    }

}
