package com.xenialsoft.api.core.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductBundleUpdateRequest {
    
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
    
}
