package com.xenialsoft.api.core.product.model;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryCreateRequest {

    /**
     * 이름
     */
    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String name;

    /**
     * 설명
     */
    private String description;

    /**
     * 화면표시순서
     */
    private int displayOrder;

    /**
     * 등록자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * 데이터 변환
     * 
     * @param category
     * @return
     */
    public static Category from(CategoryCreateRequest request) {
        Category category = new Category();
        category.setId(IdUtils.generate());
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setDisplayOrder(request.getDisplayOrder());
        category.setStatus(CategoryStatus.ACTIVE);
        category.setCreatedBy(request.getCreatedBy());
        return category;
    }
}
