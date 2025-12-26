package com.xenialsoft.api.core.product.model;

import java.util.List;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product extends Auditable {

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
     * 이미지 고유 식별자
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
     * 카테고리
     */
    private List<Category> categoryList;

    /**
     * 하위 상품 목록
     */
    private List<ProductBundle> productBundleList;

}
