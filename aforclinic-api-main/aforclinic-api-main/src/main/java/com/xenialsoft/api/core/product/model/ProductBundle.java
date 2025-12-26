package com.xenialsoft.api.core.product.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductBundle {

    /**
     * 번들 고유 식별자
     */
    private String bundleId;

    /**
     * 상품 고유 식별자
     */
    private String productId;

    /**
     * 수량
     */
    private int quantity;
    
    /**
     * 상품 정보
     */
    private Product product;
    
    /**
     * 카테고리 식별자 목록
     */
    private List<String> categoryIdList;

}
