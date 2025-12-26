package com.xenialsoft.api.core.product.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CategoryUpdateStatusRequest {

    /**
     * 상품 카테고리 식별자
     */
    @Setter
    @JsonIgnore
    private String id;

    /**
     * 상태
     */
    @Setter
    @JsonIgnore
    private CategoryStatus status;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param request
     * @return
     */
    public static Category from(CategoryUpdateStatusRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setStatus(request.getStatus());
        category.setModifiedBy(request.getModifiedBy());
        return category;
    }

}
