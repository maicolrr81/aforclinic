package com.xenialsoft.api.core.product.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSignatureRequest {

    /**
     * 이름
     */
    private List<String> productList;
    
}
