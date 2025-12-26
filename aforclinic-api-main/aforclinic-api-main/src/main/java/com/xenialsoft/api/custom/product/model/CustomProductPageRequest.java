package com.xenialsoft.api.custom.product.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomProductPageRequest {

    /**
     * 카테고리 식별자
     */
    private String categoryId;

    /**
     * 검색어
     */
    private String text;

    /**
     * 상품 식별자 목록
     */
    private List<String> productIds;

}
