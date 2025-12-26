package com.xenialsoft.api.core.product.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponse {
    
    /**
     * 상품 식별자
     */
    private String id;
        
    /**
     * 번들 여부
     */
    private boolean bundle;

    /**
     * 이름
     */
    private String name;
    
    /**
     * 설명
     */
    private String description;

    /**
     * 이미지
     */
    private String imageId;
        
    /**
     * 정가에서 조정된 가격
     */
    private int adjustedPrice;
    
    /**
     * 할인된 가격
     */
    private int discountedPrice;
    
    /**
     * 상태
     */
    private ProductStatus status;
    
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
     * 카테고리
     */
    @Setter
    private List<Category> categoryList;
    
    /**
     * 하위 상품 목록
     */
    @Setter
    private List<ProductBundleResponse> productBundleList;
    
    
    public static ProductResponse from(Product product) {
    	ProductResponse res = new ProductResponse();
    	res.id = product.getId();
    	res.bundle = product.isBundle();
    	res.name = product.getName();
    	res.description = product.getDescription();
    	res.imageId = product.getImageId();
    	res.adjustedPrice = product.getAdjustedPrice();
    	res.discountedPrice = product.getDiscountedPrice();
    	res.status = product.getStatus();
    	res.createdAt = product.getCreatedAt();
    	res.createdBy = product.getCreatedBy();
    	res.modifiedAt = product.getModifiedAt();
    	res.modifiedBy = product.getModifiedBy();
    	res.categoryList = product.getCategoryList();
    	res.productBundleList = new ArrayList<ProductBundleResponse>();
    	return res;
    }
}
