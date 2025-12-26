package com.xenialsoft.api.custom.product.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.core.product.model.ProductSignatureResponse;
import com.xenialsoft.api.core.product.service.ProductSignatureService;
import com.xenialsoft.api.custom.product.model.CustomProductPageRequest;
import com.xenialsoft.api.custom.product.service.CustomProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/products/signatures")
public class CustomProductSignatureController {

    private final ProductSignatureService service;

    private final CustomProductService productService;

    @GetMapping
    public ResponseEntity<?> getSignatureList() {
        List<ProductSignatureResponse> signatures = service.getProductSignatureList();
        List<String> productIds = signatures.stream().map(ProductSignatureResponse::getProductId).toList();

        CustomProductPageRequest request = new CustomProductPageRequest();
        request.setProductIds(productIds);

        return ApiPagedResponse.ok(productService.getProductList(request));
    }

}
