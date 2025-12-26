package com.xenialsoft.api.core.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventProductRequest {

    /**
     * 이벤트 식별자
     */
    @Setter
    @JsonIgnore
    private String eventId;

    /**
     * 상품 식별자
     */
    private String productId;

    /**
     * 정가에서 내부 조정된 가격
     */
    private Long adjustedPrice;

    /**
     * 실제 할인 적용된 가격
     */
    private Long discountedPrice;
    
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
     * @param data
     * @return
     */
    public static EventProduct from(EventProductRequest data) {
        EventProduct product = new EventProduct();
        product.setEventId(data.getEventId());
        product.setProductId(data.getProductId());
        product.setAdjustedPrice(data.getAdjustedPrice());
        product.setDiscountedPrice(data.getDiscountedPrice());
        product.setMainVisible(data.isMainVisible());
        product.setDisplayOrder(data.getDisplayOrder());
        return product;
    }

}
