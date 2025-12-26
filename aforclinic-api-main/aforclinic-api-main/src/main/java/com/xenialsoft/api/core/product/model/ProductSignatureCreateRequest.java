package com.xenialsoft.api.core.product.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSignatureCreateRequest {

    /**
     * 이름
     */
    @NotBlank
    private String id;
    
}
