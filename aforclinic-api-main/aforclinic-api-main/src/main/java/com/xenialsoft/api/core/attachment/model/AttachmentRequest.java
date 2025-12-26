package com.xenialsoft.api.core.attachment.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentRequest {

    /**
     * 대상 식별자
     */
    @Setter
    private String targetId;

    /**
     * 대상 타입
     */
    @Setter
    private String targetType;

    /**
     * 파일 식별자
     */
    @NotBlank
    private String fileId;

    public static AttachmentRequest of(String targetId, String targetType) {
        AttachmentRequest request = new AttachmentRequest();
        request.targetId = targetId;
        request.targetType = targetType;
        return request;
    }

}
