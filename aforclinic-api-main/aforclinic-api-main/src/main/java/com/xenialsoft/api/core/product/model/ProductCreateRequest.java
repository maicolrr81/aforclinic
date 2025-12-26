package com.xenialsoft.api.core.product.model;

import java.util.List;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    /**
     * 번들 여부
     */
    private boolean bundle;

    /**
     * 이름
     */
    @NotBlank(message = "상품명이 입력되지 않았습니다.")
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
     * 상태
     */
    @Setter
    @JsonIgnore
    private ProductStatus status;
    
    /**
     * 등록자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;
        
    /**
     * 카테고리
     */
    private List<Category> categoryList;
    
    /**
     * 하위 상품 목록
     */
    private List<ProductBundleRequest> productBundleList;


    public static Product from(ProductCreateRequest request) {
        Product product = new Product();
        product.setId(IdUtils.generate());
        product.setBundle(request.isBundle());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImageId(request.getImageId());
        product.setAdjustedPrice(request.getAdjustedPrice());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setStatus(ProductStatus.ACTIVE);
        product.setCreatedBy(request.getCreatedBy());
        product.setCategoryList(request.getCategoryList());        
        
        if(request.getProductBundleList() != null && request.getProductBundleList().size() > 0)
            product.setProductBundleList(request.getProductBundleList().stream().map(ProductBundleRequest::from).toList());
        return product;
    }
}
