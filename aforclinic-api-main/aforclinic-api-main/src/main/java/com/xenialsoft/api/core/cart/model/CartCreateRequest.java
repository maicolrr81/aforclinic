package com.xenialsoft.api.core.cart.model;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartCreateRequest {

    /**
     * 사용자 식별자
     */
    @Setter
    @JsonIgnore
    private String userId;

    /**
     * 상품 식별자
     */
    private String productId;

    /**
     * 등록자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Cart from(CartCreateRequest data) {
        Cart cart = new Cart();
        cart.setUserId(data.getUserId());
        cart.setProductId(data.getProductId());
        cart.setCreatedBy(data.getCreatedBy());
        return cart;
    }

}
