package com.xenialsoft.api.core.product.model;


import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductBundleResponse {
    
    /**
     * 번들 고유 식별자
     */
    private String bundleId;
    
    /**
     * 상품 고유 식별자
     */
    private String productId;
    
    /**
     * 상품 이름
     */
    private String productName;
        
    /**
     * 조정된 정가 가격
     */
    private int productAdjustedPrice;
    
    /**
     * 할인된 가격
     */
    private int productDiscountedPrice;

    /**
     * 수량
     */
    private int quantity;
    
    /**
     * 카테고리 식별자 목록
     */
    private List<String> categoryIdList;
 
    
    public static ProductBundleResponse from(ProductBundle productBundle) {
    	ProductBundleResponse res = new ProductBundleResponse();
    	res.bundleId = productBundle.getBundleId();
    	res.productId = productBundle.getProductId();
    	res.productName = (productBundle.getProduct() != null ? productBundle.getProduct().getName() : "");
    	res.productAdjustedPrice = (productBundle.getProduct() != null ? productBundle.getProduct().getAdjustedPrice() : 0);
    	res.productDiscountedPrice = (productBundle.getProduct() != null ? productBundle.getProduct().getDiscountedPrice() : 0);
    	res.quantity = productBundle.getQuantity();
    	res.categoryIdList = productBundle.getCategoryIdList();
    	return res;
    }    
    
}
