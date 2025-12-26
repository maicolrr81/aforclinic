package com.xenialsoft.api.core.beforeafter.model;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterBannerCreateRequest {

    private String title;
    
    private String description;
    
    private BeforeAfterBannerMediaType mediaType;
    
    private String mediaContent;
    
    @JsonIgnore
    @Setter
    private BeforeAfterBannerStatus status;
    
    @JsonIgnore
    @CreatedBy
    private String createdBy;
    
    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static BeforeAfterBanner from(BeforeAfterBannerCreateRequest request) {
        BeforeAfterBanner beforeAfterBanner = new BeforeAfterBanner();
        beforeAfterBanner.setId(IdUtils.generate());
        beforeAfterBanner.setTitle(request.getTitle());
        beforeAfterBanner.setDescription(request.getDescription());
        beforeAfterBanner.setMediaType(request.getMediaType());
        beforeAfterBanner.setMediaContent(request.getMediaContent());
        beforeAfterBanner.setStatus(BeforeAfterBannerStatus.ACTIVE);
        beforeAfterBanner.setCreatedBy(request.getCreatedBy());
        return beforeAfterBanner;
    }

}
