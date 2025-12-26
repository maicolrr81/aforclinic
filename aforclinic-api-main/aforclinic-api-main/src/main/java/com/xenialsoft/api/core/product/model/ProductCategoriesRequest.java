package com.xenialsoft.api.core.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoriesRequest {
    
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

    
    public static ProductCategories from(ProductCategoriesRequest req) {
        ProductCategories bundle = new ProductCategories();
        bundle.setCategoryId(req.getCategoryId());
        bundle.setProductId(req.getProductId());
        bundle.setDisplayOrder(req.getDisplayOrder());
        return bundle;
    }
}
