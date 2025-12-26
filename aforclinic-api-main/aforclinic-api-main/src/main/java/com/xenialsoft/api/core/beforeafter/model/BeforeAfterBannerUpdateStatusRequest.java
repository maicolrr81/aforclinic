package com.xenialsoft.api.core.beforeafter.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class BeforeAfterBannerUpdateStatusRequest {

    @Setter
    @JsonIgnore
    private String id;
    
    @Setter
    @JsonIgnore
    private BeforeAfterBannerStatus status;
    
    @LastModifiedBy
    @JsonIgnore
    private String modifiedBy;
    
    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static BeforeAfterBanner from(BeforeAfterBannerUpdateStatusRequest request) {
        BeforeAfterBanner beforeAfterBanner = new BeforeAfterBanner();
        beforeAfterBanner.setId(request.getId());
        beforeAfterBanner.setStatus(request.getStatus());
        beforeAfterBanner.setModifiedBy(request.getModifiedBy());
        return beforeAfterBanner;
    }

}
