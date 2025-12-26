package com.xenialsoft.api.core.product.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCategoriesResponse {
    
    
    /**
     * 상품 고유 식별자
     */
    private String productId;
    
    /**
     * 카테고리 식별자
     */
    private String categoryId;
    
    /**
     * 카테고리 이름
     */
    private String categoryName;
 
    
    public static ProductCategoriesResponse from(ProductCategories productCategories) {
    	ProductCategoriesResponse res = new ProductCategoriesResponse();
    	res.productId = productCategories.getProductId();
    	res.categoryId = productCategories.getCategoryId();
    	res.categoryName = productCategories.getCategory().getName();
    	return res;
    }    
    
}
