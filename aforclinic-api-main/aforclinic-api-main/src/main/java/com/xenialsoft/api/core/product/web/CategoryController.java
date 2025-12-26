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
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.common.util.SequenceUtils;
import com.xenialsoft.api.core.product.model.CategoryCreateRequest;
import com.xenialsoft.api.core.product.model.CategoryPageRequest;
import com.xenialsoft.api.core.product.model.CategoryPagedResponse;
import com.xenialsoft.api.core.product.model.CategoryReorderRequest;
import com.xenialsoft.api.core.product.model.CategoryResponse;
import com.xenialsoft.api.core.product.model.CategoryStatus;
import com.xenialsoft.api.core.product.model.CategorySummaryInfo;
import com.xenialsoft.api.core.product.model.CategoryUpdateRequest;
import com.xenialsoft.api.core.product.model.CategoryUpdateStatusRequest;
import com.xenialsoft.api.core.product.service.CategoryService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {

    private final CategoryService service;

    /**
     * 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getCategoryList(CategoryPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<CategoryPagedResponse> content = service.getCategoryList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    /**
     * 목록 조회 (/public/products/categories 와 동일)
     * 
     * @return
     */
    @GetMapping("/names")
    public ResponseEntity<?> getProductCategoryList() {

        ApiPageRequest paging = new ApiPageRequest();
        paging.setUnpaged(true);

        CategoryPageRequest request = new CategoryPageRequest();

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiResponse.ok();
        }

        List<CategoryPagedResponse> content = service.getCategoryList(request, paging);

        return ApiResponse.ok(content.stream().map(CategorySummaryInfo::from).toList());
    }

    /**
     * 상세 찾기
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id) {

        CategoryResponse data = service.getCategoryById(id).orElseThrow(() -> {
            throw new NotFoundException("카테고리가 없습니다.");
        });
        return ApiResponse.ok(data);
    }

    /**
     * 추가
     * 
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        service.createCategory(request);
        return ApiResponse.ok();
    }

    /**
     * 카테고리 재정렬
     * 
     * @return
     */
    @PutMapping
    public ResponseEntity<?> updateCategories(@RequestBody List<@Valid CategoryReorderRequest> request) {
        service.reorderCategories(request);
        return ApiResponse.ok();
    }

    /**
     * 수정
     * 
     * @param id
     * @param category
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id,
            @RequestBody @Valid CategoryUpdateRequest category) {

        // id 설정
        category.setId(id);

        // 업데이트
        service.updateCategory(category);

        return ApiResponse.ok();
    }

    /**
     * 상태 업데이트
     * 
     * @param id
     * @param request
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategoryStatus(@PathVariable String id) {

        CategoryUpdateStatusRequest request = new CategoryUpdateStatusRequest();

        // id 설정
        request.setId(id);
        request.setStatus(CategoryStatus.DELETED);

        // 삭제
        service.deleteCategory(request);

        return ApiResponse.ok();
    }

}
