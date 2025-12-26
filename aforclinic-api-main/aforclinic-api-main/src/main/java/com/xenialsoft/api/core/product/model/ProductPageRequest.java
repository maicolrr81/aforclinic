package com.xenialsoft.api.core.product.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductPageRequest {

    /**
     * 카테고리 식별자
     */
    private String categoryId;

    /**
     * 번들 여부
     */
    private Boolean bundle;

    /**
     * 상품 식별자 목록
     */
    private List<String> ids;

    /**
     * 검색어
     */
    private String text;

    /**
     * 상태
     */
    @JsonIgnore
    private ProductStatus status;

}
