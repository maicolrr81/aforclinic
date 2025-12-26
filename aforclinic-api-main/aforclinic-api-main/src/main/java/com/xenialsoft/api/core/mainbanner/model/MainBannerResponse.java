package com.xenialsoft.api.core.mainbanner.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainBannerResponse {

    private String id;
    
    private String title;
        
    private String imageId;
    
    private String linkUri;
    
    private int displayOrder;
    
    private MainBannerStatus status;
        
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param mainBanner
     * @return
     */
    public static MainBannerResponse from(MainBanner mainBanner) {
        MainBannerResponse res = new MainBannerResponse();
        res.id = mainBanner.getId();
        res.title = mainBanner.getTitle();
        res.imageId = mainBanner.getImageId();
        res.linkUri = mainBanner.getLinkUri();
        res.displayOrder = mainBanner.getDisplayOrder();
        res.status = mainBanner.getStatus();
        res.createdAt = mainBanner.getCreatedAt();
        res.createdBy = mainBanner.getCreatedBy();
        res.modifiedAt = mainBanner.getModifiedAt();
        res.modifiedBy = mainBanner.getModifiedBy();
        return res;
    }
}
