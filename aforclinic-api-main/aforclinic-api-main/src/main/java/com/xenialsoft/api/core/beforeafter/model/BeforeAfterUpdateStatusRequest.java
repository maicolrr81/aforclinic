package com.xenialsoft.api.core.beforeafter.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class BeforeAfterUpdateStatusRequest {

    @Setter
    @JsonIgnore
    private String id;
    
    @Setter
    @JsonIgnore
    private BeforeAfterStatus status;
    
    @LastModifiedBy
    @JsonIgnore
    private String modifiedBy;
    
    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static BeforeAfter from(BeforeAfterUpdateStatusRequest request) {
        BeforeAfter beforeAfter = new BeforeAfter();
        beforeAfter.setId(request.getId());
        beforeAfter.setStatus(request.getStatus());
        beforeAfter.setModifiedBy(request.getModifiedBy());
        return beforeAfter;
    }

}
