package com.xenialsoft.api.core.product.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category extends Auditable {

    /**
     * 상품 카테고리 식별자
     */
    private String id;

    /**
     * 이름
     */
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
     * 상태
     */
    private CategoryStatus status;

}
