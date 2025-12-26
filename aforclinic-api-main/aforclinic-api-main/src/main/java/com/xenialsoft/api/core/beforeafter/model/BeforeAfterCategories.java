package com.xenialsoft.api.core.beforeafter.model;

import com.xenialsoft.api.core.product.model.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeforeAfterCategories {

    private String beforeAfterId;

    private String categoryId;
    
    private Category category;

}
