package com.xenialsoft.api.core.product.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPagedResponse implements Sequenceable {

    @Setter
    private Long sequence;

    /**
     * 고유 식별자
     */
    private String id;

    /**
     * 카테고리명
     */
    private String categoryName;

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

    public static ProductPagedResponse from(Product product) {
        ProductPagedResponse res = new ProductPagedResponse();
        res.id = product.getId();

        String categoryName = "";
        if(product.getCategoryList() != null && product.getCategoryList().size() > 0) {
            
            for(Category category: product.getCategoryList()) {
                if(!"".equals(categoryName)) {
                    categoryName += ",";
                }
                if(category != null) {
                    categoryName += category.getName();
                }
            }            
        }
        if(!"".equals(categoryName)) {
            res.categoryName = categoryName;
        }

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
        return res;
    }
}
