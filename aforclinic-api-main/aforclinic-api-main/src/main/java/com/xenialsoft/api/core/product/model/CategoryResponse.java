package com.xenialsoft.api.core.product.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryResponse {
    
    /**
     * 고유 식별자
     */
    private String id;
    
    /**
     * 이름
     */
    private String name;
    
    /**
     * 설명
     */
    private String description;
    
    /**
     * 화면표시순서
     */
    private int displayOrder;
    
    /**
     * 상태
     */
    private CategoryStatus status;
    
    /**
     * 등록 일시(YYYY-MM-DD HH:MM:SS)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    /**
     * 등록자
     */
    private String createdBy;
    
    /**
     * 수정 일시(YYYY-MM-DD HH:MM:SS)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    /**
     * 수정자
     */
    private String modifiedBy;
        
    /**
     * 엔티티 변환
     * @param category
     * @return
     */
    public static CategoryResponse from(Category category) {
    	CategoryResponse res = new CategoryResponse();
    	res.id = category.getId();
    	res.name = category.getName();
    	res.description = category.getDescription();
    	res.displayOrder = category.getDisplayOrder();
    	res.status = category.getStatus();
    	res.createdAt = category.getCreatedAt();
    	res.createdBy = category.getCreatedBy();
    	res.modifiedAt = category.getModifiedAt();
    	res.modifiedBy = category.getModifiedBy();
    	return res;
	}

}
