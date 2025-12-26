package com.xenialsoft.api.core.product.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CategoryUpdateRequest {

    /**
     * 카테고리 식별자
     */
    @Setter
    @JsonIgnore
    private String id;

    /**
     * 카테고리명
     */
    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String name;

    /**
     * 카테고리 설명
     */
    private String description;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param category
     * @return
     */
    public static Category from(CategoryUpdateRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setModifiedBy(request.getModifiedBy());
        return category;
    }

}
