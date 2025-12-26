package com.xenialsoft.api.core.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductBundleRequest {
    
    /**
     * 번들 고유 식별자
     */
	@JsonIgnore
    private String bundleId;
    
    /**
     * 상품 고유 식별자
     */
    private String productId;

    /**
     * 수량
     */
    private int quantity;

    
    public static ProductBundle from(ProductBundleRequest req) {
        ProductBundle bundle = new ProductBundle();
        bundle.setBundleId(req.getBundleId());
        bundle.setProductId(req.getProductId());
        bundle.setQuantity(req.getQuantity());     
        return bundle;
    }
}
