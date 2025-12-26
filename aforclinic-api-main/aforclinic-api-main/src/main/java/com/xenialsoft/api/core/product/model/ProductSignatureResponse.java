package com.xenialsoft.api.core.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSignatureResponse {

    /**
     * 상품 식별자
     */
    private String productId;
    
    private Product product;
    
    /**
     * 엔티티 변경
     * @param productSignature
     * @return
     */
    public static ProductSignatureResponse from(ProductSignature productSignature) {
        ProductSignatureResponse res = new ProductSignatureResponse();
        res.productId = productSignature.getProductId();
        res.product = productSignature.getProduct();
        return res;
    } 
}
