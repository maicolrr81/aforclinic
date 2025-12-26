package com.xenialsoft.api.custom.product.model;

import com.xenialsoft.api.custom.event.model.CustomEventResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomProductResponse {

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
     * 이벤트 정보
     */
    private CustomEventResponse event;

    /**
     * 데이터 변환
     * 
     * @param product
     * @return
     */
    public static CustomProductResponse from(CustomProduct product) {
        if (product == null) {
            return null;
        }
        CustomProductResponse data = new CustomProductResponse();
        data.id = product.getId();
        data.name = product.getName();
        data.description = product.getDescription();
        data.adjustedPrice = product.getAdjustedPrice();
        data.discountedPrice = product.getDiscountedPrice();
        data.event = CustomEventResponse.from(product.getEvent());
        return data;
    }
}
