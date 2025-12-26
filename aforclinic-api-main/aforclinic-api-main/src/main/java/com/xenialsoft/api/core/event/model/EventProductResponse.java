package com.xenialsoft.api.core.event.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventProductResponse {

    /**
     * 이벤트 식별자
     */
    private String eventId;

    /**
     * 상품 식별자
     */
    private String productId;

    /**
     * 상품명
     */
    private String productName;

    /**
     * 상품설명
     */
    private String productDescription;

    /**
     * 조정 가격
     */
    private long adjustedPrice;

    /**
     * 할인 가격
     */
    private long discountedPrice;
    
    /**
     * 메인화면 표시 여부
     */
    private boolean mainVisible;
    
    /**
     * 화면 표시 순서
     */
    private int displayOrder;

    /**
     * 데이터 변환
     * 
     * @param product
     * @return
     */
    public static EventProductResponse from(EventProduct product) {
        EventProductResponse data = new EventProductResponse();
        data.eventId = product.getEventId();
        data.productId = product.getProductId();
        data.productName = product.getProduct().getName();
        data.productDescription = product.getProduct().getDescription();
        data.adjustedPrice = product.getAdjustedPrice();
        data.discountedPrice = product.getDiscountedPrice();
        data.mainVisible = product.isMainVisible();
        data.displayOrder = product.getDisplayOrder();
        return data;
    }

}
