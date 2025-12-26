package com.xenialsoft.api.custom.product.model;

import com.xenialsoft.api.custom.event.model.CustomEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomProduct {

    /**
     * 상품 식별자
     */
    private String id;

    /**
     * 상품명
     */
    private String name;

    /**
     * 상품 설명
     */
    private String description;

    /**
     * 조정 가격
     */
    private Long adjustedPrice;

    /**
     * 할인 가격
     */
    private Long discountedPrice;

    /**
     * 이벤트 여부
     */
    private CustomEvent event;

}
