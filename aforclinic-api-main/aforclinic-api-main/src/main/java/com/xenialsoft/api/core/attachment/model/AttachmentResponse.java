package com.xenialsoft.api.core.attachment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentResponse {

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
     * 
     * @param attachment
     * @return
     */
    public static AttachmentResponse from(Attachment attachment) {
        AttachmentResponse data = new AttachmentResponse();
        data.fileId = attachment.getFileId();
        data.filename = attachment.getFilename();
        data.size = attachment.getSize();
        return data;
    }

}
