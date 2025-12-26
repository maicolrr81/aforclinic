package com.xenialsoft.api.core.attachment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment {

    /**
     * 대상 식별자
     */
    private String targetId;

    /**
     * 대상 타입
     */
    private String targetType;

    /**
     * 파일 식별자
     */
    private String fileId;

    /**
     * 파일명
     */
    private String filename;

    /**
     * 크기
     */
    private long size;

    /**
     * 데이터 변환
     * 
     * @param attachment
     * @return
     */
    public static Attachment from(AttachmentRequest data) {
        Attachment attachment = new Attachment();
        attachment.targetId = data.getTargetId();
        attachment.targetType = data.getTargetType();
        attachment.fileId = data.getFileId();
        return attachment;
    }

}
