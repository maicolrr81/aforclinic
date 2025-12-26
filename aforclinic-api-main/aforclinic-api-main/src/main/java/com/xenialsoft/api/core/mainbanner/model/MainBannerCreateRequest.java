package com.xenialsoft.api.core.mainbanner.model;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainBannerCreateRequest {
    
	@JsonIgnore
	@Setter
	private String id;	
	
    private String title;
        
    private String imageId;
    
    private String linkUri;
    
    private int displayOrder;
    
    @JsonIgnore
    @Setter
    private MainBannerStatus status;
    
    @JsonIgnore
    @CreatedBy
    private String createdBy;

    
    /**
     * 데이터 변환
     * 
     * @param request
     * @return
     */
    public static MainBanner from(MainBannerCreateRequest request) {
    	MainBanner mainBanner = new MainBanner();
    	mainBanner.setId(IdUtils.generate());
    	mainBanner.setTitle(request.getTitle());
    	mainBanner.setImageId(request.getImageId());
    	mainBanner.setLinkUri(request.getLinkUri());
    	mainBanner.setDisplayOrder(request.getDisplayOrder());
    	mainBanner.setStatus(MainBannerStatus.ACTIVE);
    	mainBanner.setCreatedBy(request.getCreatedBy());
        return mainBanner;
    }

}
