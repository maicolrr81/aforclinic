package com.xenialsoft.api.core.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.cart.mapper.CartMapper;
import com.xenialsoft.api.core.cart.model.Cart;
import com.xenialsoft.api.core.cart.model.CartCreateRequest;
import com.xenialsoft.api.core.cart.model.CartResponse;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper mapper;

    /**
     * 조회
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public List<CartResponse> getCartList(String id) {
        return mapper.selectListById(id).stream().map(CartResponse::from).toList();
    }

    @Auditable
    @Transactional
    public void createCart(CartCreateRequest request) {
        Cart cart = CartCreateRequest.from(request);
        int count = mapper.insert(cart);
        if (count != 1) {
            throw new ServiceException("장바구니 추가 중 오류가 발생하였습니다.");
        }
    }

    @Transactional
    public void deleteCartList(String userId, List<String> productIds) {
        productIds.stream().forEach(productId -> deleteCart(userId, productId));
    }

    @Transactional
    public void deleteCart(String userId, String productId) {
        int count = mapper.delete(userId, productId);
        if (count != 1) {
            throw new ServiceException("장바구니 삭제 중 오류가 발생하였습니다.");
        }
    }

}
