package com.xenialsoft.api.core.product.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ProductUpdateStatusRequest {

	/**
	 * 고유 식별자
	 */
	@Setter
	@JsonIgnore
	private String id;

	/**
	 * 이름
	 */
	@Setter
	@JsonIgnore
	private ProductStatus status;

	/**
	 * 수정자
	 */
	@LastModifiedBy
	@JsonIgnore
	private String modifiedBy;

	

    public static Product from(ProductUpdateStatusRequest request) {
        Product product = new Product();
        product.setId(request.getId());
        product.setStatus(request.getStatus());
        product.setModifiedBy(request.getModifiedBy());
        return product;
    }
}
