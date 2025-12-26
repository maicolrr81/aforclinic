package com.xenialsoft.api.core.product.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.product.model.ProductSignatureRequest;
import com.xenialsoft.api.core.product.model.ProductSignatureResponse;
import com.xenialsoft.api.core.product.service.ProductSignatureService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products/signatures")
@PreAuthorize("hasRole('ADMIN')")
public class ProductSignatureController {

    private final ProductSignatureService service;

    /**
     * 목록 찾기
     * 
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getSignature() {

        List<ProductSignatureResponse> ProductSignatureList = service.getProductSignatureList();
        return ApiResponse.ok(ProductSignatureList);
    }

    /**
     * 저장
     * 
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> updateCategoryStatus(@RequestBody @Valid ProductSignatureRequest request) {

        // 저장
        service.saveSignature(request.getProductList());
        return ApiResponse.ok();
    }

}
