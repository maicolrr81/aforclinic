package com.xenialsoft.api.core.event.model;

import com.xenialsoft.api.core.product.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventProduct {

    /**
     * 이벤트 식별자
     */
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
     * 상품 정보
     */
    private Product product;

}
