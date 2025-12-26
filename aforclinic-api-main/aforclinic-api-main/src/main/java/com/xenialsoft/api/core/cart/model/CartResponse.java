package com.xenialsoft.api.core.cart.model;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartResponse {

    /**
     * 사용자 식별자
     */
    private String userId;

    /**
     * 상품 식별자
     */
    private String productId;

    /**
     * 등록일시
     */
    private LocalDateTime createdAt;

    /**
     * 등록자
     */
    private String createdBy;

    /**
     * 데이터 변환
     * 
     * @param cart
     * @return
     */
    public static CartResponse from(Cart cart) {
        CartResponse data = new CartResponse();
        data.userId = cart.getUserId();
        data.productId = cart.getProductId();
        data.createdAt = cart.getCreatedAt();
        data.createdBy = cart.getCreatedBy();
        return data;
    }

}
