package com.xenialsoft.api.core.product.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryReorderRequest {

    /**
     * 카테고리 식별자
     */
    @NotBlank(message = "카테고리 식별자가 없습니다.")
    private String id;

    /**
     * 화면 표시 순서
     */
    @NotNull
    @PositiveOrZero
    private Integer displayOrder;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Category from(CategoryReorderRequest data) {
        Category category = new Category();
        category.setId(data.getId());
        category.setDisplayOrder(data.getDisplayOrder());
        category.setModifiedBy(data.getModifiedBy());
        return category;
    }

}
