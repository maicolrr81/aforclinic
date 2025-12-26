package com.xenialsoft.api.custom.cart.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.cart.model.CartCreateRequest;
import com.xenialsoft.api.core.cart.model.CartResponse;
import com.xenialsoft.api.core.cart.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/cart")
@PreAuthorize("isAuthenticated()")
public class CustomCartController {

    private final CartService service;

    /**
     * 장바구니 목록
     * 
     * @param principal
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getCartList(@AuthenticationPrincipal CustomUserDetails principal) {
        List<CartResponse> content = service.getCartList(principal.getId());
        return ApiPagedResponse.ok(content);
    }

    /**
     * 장바구니 추가
     * 
     * @param principal
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createCart(@AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody @Valid CartCreateRequest request) {

        request.setUserId(principal.getId());
        service.createCart(request);

        return ApiResponse.ok();
    }

    /**
     * 장바구니 삭제
     * 
     * @param principal
     * @param ids
     * @return
     */
    @DeleteMapping
    public ResponseEntity<?> deleteCartList(@AuthenticationPrincipal CustomUserDetails principal,
            @RequestParam(name = "productId") List<String> ids) {

        service.deleteCartList(principal.getId(), ids);

        return ApiResponse.ok();
    }

}
