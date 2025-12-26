package com.xenialsoft.api.core.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategories {

    /**
     * 상품 고유 식별자
     */
    private String productId;
    
    /**
     * 카테고리 식별자
     */
    private String categoryId;
    
    /**
     * 화면 표시 순서
     */
    private int displayOrder;
    
    /**
     * 카테고리 정보
     */
    private Category category;

}
