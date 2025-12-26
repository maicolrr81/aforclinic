package com.xenialsoft.api.core.product.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.common.util.SequenceUtils;
import com.xenialsoft.api.core.product.model.Product;
import com.xenialsoft.api.core.product.model.ProductCategoriesRequest;
import com.xenialsoft.api.core.product.model.ProductCreateRequest;
import com.xenialsoft.api.core.product.model.ProductPageRequest;
import com.xenialsoft.api.core.product.model.ProductPagedResponse;
import com.xenialsoft.api.core.product.model.ProductResponse;
import com.xenialsoft.api.core.product.model.ProductStatus;
import com.xenialsoft.api.core.product.model.ProductUpdateRequest;
import com.xenialsoft.api.core.product.model.ProductUpdateStatusRequest;
import com.xenialsoft.api.core.product.service.ProductService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@PreAuthorize("hasRole('ADMIN')")
public class ProductController {

    private final ProductService service;

    /**
     * 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getProductList(ProductPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        // 페이징 처리 안하는 조건
        if( request.getCategoryId() != null 
        		&& request.getBundle() == null 
        		&& (request.getText() == null || request.getText().trim().length() == 0 ) ) {
        	paging.setUnpaged(true);
        }

        List<ProductPagedResponse> content = service.getProductList(request, paging);

        // 순번 설정
        if(paging.isUnpaged())
            SequenceUtils.assign(content);
        else
            SequenceUtils.assign(content, paging.getStartSequence(totalCount), true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    /**
     * 상세 조회
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {

        ProductResponse data = service.getProductById(id).orElseThrow(() -> {
            throw new NotFoundException("상품이 없습니다. id: {}", id);
        });

        return ApiResponse.ok(data);
    }

    /**
     * 패키지에 등록할 상품 목록 조회
     * 
     * @param categoryId
     * @param searchText
     * @return
     */
    @GetMapping("/bundle-product")
    public ResponseEntity<?> getBundleProduct(@RequestParam(required = true) String categoryId,
            @RequestParam(required = false) String searchText) {

        List<Product> productList = service.getBundleProduct(categoryId, searchText);

        return ApiResponse.ok(productList);
    }

    /**
     * 이벤트에 등록할 상품 목록 조회
     * 
     * @param searchText
     * @return
     */
    @GetMapping("/event-product")
    public ResponseEntity<?> getEventProduct(@RequestParam(required = false) String categoryId,
            @RequestParam String searchText) {

        List<Product> productList = service.getEventProduct(categoryId, searchText);

        return ApiResponse.ok(productList);
    }

    /**
     * 추가
     * 
     * @param principal
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateRequest request) {

        service.createProduct(request);

        return ApiResponse.ok();
    }

    /**
     * 수정
     * 
     * @param principal
     * @param id
     * @param product
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody ProductUpdateRequest product) {

        product.setId(id);

        service.updateProduct(product);

        return ApiResponse.ok();
    }

    /**
     * 카테고리의 화면표시순서 변경
     * @param request
     * @return
     */
    @PutMapping("/change-displayorder")
    public ResponseEntity<?> reorderProductCategories(
            @RequestBody List<@Valid ProductCategoriesRequest> request) {
        
        service.reorderProductCategories(request);
        return ApiResponse.ok();
    }
    
    /**
     * deleted 삭제
     * 
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDeletedProduct(@PathVariable String id) {

        ProductUpdateStatusRequest request = new ProductUpdateStatusRequest();
        request.setId(id);
        request.setStatus(ProductStatus.DELETED);

        service.updateDeletedProduct(request);

        return ApiResponse.ok();
    }

}
