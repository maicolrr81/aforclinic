package com.xenialsoft.api.custom.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.custom.product.mapper.CustomProductMapper;
import com.xenialsoft.api.custom.product.model.CustomProductPageRequest;
import com.xenialsoft.api.custom.product.model.CustomProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomProductService {

    private final CustomProductMapper mapper;

    @Transactional(readOnly = true)
    public List<CustomProductResponse> getProductList(CustomProductPageRequest request) {
        return mapper.selectList(request).stream().map(CustomProductResponse::from).toList();
    }

}
