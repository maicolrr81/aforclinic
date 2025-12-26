package com.xenialsoft.api.core.beforeafter.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterBannerResponse {

    private String id;
    
    private String title;
    
    private String description;
    
    private BeforeAfterBannerMediaType mediaType;
    
    private String mediaContent;
    
    private BeforeAfterBannerStatus status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param beforeAfterBanner
     * @return
     */
    public static BeforeAfterBannerResponse from(BeforeAfterBanner beforeAfterBanner) {
        BeforeAfterBannerResponse res = new BeforeAfterBannerResponse();
        res.id = beforeAfterBanner.getId();
        res.title = beforeAfterBanner.getTitle();
        res.description = beforeAfterBanner.getDescription();
        res.mediaType = beforeAfterBanner.getMediaType();
        res.mediaContent = beforeAfterBanner.getMediaContent();
        res.status = beforeAfterBanner.getStatus();
        res.createdAt = beforeAfterBanner.getCreatedAt();
        res.createdBy = beforeAfterBanner.getCreatedBy();
        res.modifiedAt = beforeAfterBanner.getModifiedAt();
        res.modifiedBy = beforeAfterBanner.getModifiedBy();
        return res;
    }
}
