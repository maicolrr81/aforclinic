package com.xenialsoft.api.core.mainbanner.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainBannerPagedResponse implements Sequenceable {

    /**
     * 순번
     */
    @Setter
    private Long sequence;

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
    public static MainBannerPagedResponse from(MainBanner mainBanner) {
    	MainBannerPagedResponse res = new MainBannerPagedResponse();
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
