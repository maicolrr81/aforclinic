package com.xenialsoft.api.core.file.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileMetadata extends Auditable {

    /**
     * 파일 식별자
     */
    private String id;

    /**
     * 파일명
     */
    private String filename;

    /**
     * 저장된 경로
     */
    private String path;

    /**
     * 크기(바이트 단위)
     */
    private long size;

}
