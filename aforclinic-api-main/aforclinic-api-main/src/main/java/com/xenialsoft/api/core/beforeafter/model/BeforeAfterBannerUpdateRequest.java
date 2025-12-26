package com.xenialsoft.api.core.beforeafter.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterBannerUpdateRequest {

    @JsonIgnore
    @Setter
    private String id;
    
    private String title;
    
    private String description;
    
    private BeforeAfterBannerMediaType mediaType;
    
    private String mediaContent;
    
    @LastModifiedBy
    @JsonIgnore
    private String modifiedBy;
    
    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static BeforeAfterBanner from(BeforeAfterBannerUpdateRequest request) {
        BeforeAfterBanner beforeAfterBanner = new BeforeAfterBanner();
        beforeAfterBanner.setId(request.getId());
        beforeAfterBanner.setTitle(request.getTitle());
        beforeAfterBanner.setDescription(request.getDescription());
        beforeAfterBanner.setMediaType(request.getMediaType());
        beforeAfterBanner.setMediaContent(request.getMediaContent());
        beforeAfterBanner.setModifiedBy(request.getModifiedBy());
        return beforeAfterBanner;
    }

}
