package com.xenialsoft.api.core.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSignature {

    /**
     * 상품 식별자
     */
    private String productId;
    
    private Product product;
    
}
