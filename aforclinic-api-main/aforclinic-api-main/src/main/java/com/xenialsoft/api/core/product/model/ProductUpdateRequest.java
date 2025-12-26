package com.xenialsoft.api.core.product.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProductUpdateRequest {

    /**
     * 상품 식별자
     */
    @Setter
    @JsonIgnore
    private String id;

    /**
     * 이름
     */
    @NotBlank
    private String name;

    /**
     * 설명
     */
    private String description;

    /**
     * 이미지
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
     * 수정자
     */
    @LastModifiedBy
    @JsonIgnore
    private String modifiedBy;
    
    /**
     * 카테고리
     */
    private List<Category> categoryList = new ArrayList<Category>();

    /**
     * 번들 목록
     */
    private List<ProductBundleRequest> productBundleList = new ArrayList<ProductBundleRequest>();
    
    
    public static Product from(ProductUpdateRequest request) {
        Product product = new Product();
        product.setId(request.getId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImageId(request.getImageId());
        product.setAdjustedPrice(request.getAdjustedPrice());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setModifiedBy(request.getModifiedBy());
        product.setCategoryList(request.getCategoryList());
        product.setProductBundleList(request.getProductBundleList().stream().map(ProductBundleRequest::from).toList());
        return product;
    }
}
