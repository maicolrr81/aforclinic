package com.xenialsoft.api.core.mainbanner.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MainBannerUpdateRequest {

    @Setter
    private String id;

    private String title;
        
    private String imageId;
    
    private String linkUri;
    
    private int displayOrder;
    
    @Setter
    private MainBannerStatus status;
    
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;
    
    /**
     * 데이터 변환
     * 
     * @param request
     * @return
     */
    public static MainBanner from(MainBannerUpdateRequest request) {
    	MainBanner mainBanner = new MainBanner();
    	mainBanner.setId(request.getId());
    	mainBanner.setTitle(request.getTitle());
    	mainBanner.setImageId(request.getImageId());
    	mainBanner.setLinkUri(request.getLinkUri());
    	mainBanner.setDisplayOrder(request.getDisplayOrder());
    	mainBanner.setStatus(request.getStatus());
    	mainBanner.setModifiedBy(request.getModifiedBy());
    	return mainBanner;
    }

}
