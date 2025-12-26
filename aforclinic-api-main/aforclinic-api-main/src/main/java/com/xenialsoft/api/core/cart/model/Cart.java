package com.xenialsoft.api.core.cart.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Cart extends Auditable {
    
    private String userId;
    
    private String productId;

}
