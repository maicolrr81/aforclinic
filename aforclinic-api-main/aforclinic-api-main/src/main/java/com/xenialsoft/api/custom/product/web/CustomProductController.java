package com.xenialsoft.api.custom.product.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.custom.product.model.CustomProductPageRequest;
import com.xenialsoft.api.custom.product.service.CustomProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/products")
public class CustomProductController {

    private final CustomProductService service;

    @GetMapping
    public ResponseEntity<?> getProductList(CustomProductPageRequest request) {
        return ApiPagedResponse.ok(service.getProductList(request));
    }

}
