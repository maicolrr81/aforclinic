package com.xenialsoft.api.custom.product.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.LabelValuePair;
import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.product.model.CategoryPageRequest;
import com.xenialsoft.api.core.product.model.CategoryPagedResponse;
import com.xenialsoft.api.core.product.model.CategorySummaryInfo;
import com.xenialsoft.api.core.product.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/categories")
public class CustomCategoryController {

    private final CategoryService service;

    /**
     * 카테고리 목록 조회
     * 
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getCategoryList() {

        CategoryPageRequest request = new CategoryPageRequest();

        ApiPageRequest paging = ApiPageRequest.unpaged();

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<CategoryPagedResponse> content = service.getCategoryList(request, paging);

        return ApiResponse.ok(content.stream().map(CategorySummaryInfo::from).toList());
    }

    /**
     * 카테고리 목록 조회
     * 
     * @return
     */
    @GetMapping("/options")
    public ResponseEntity<?> getCategoryOptions() {

        CategoryPageRequest request = new CategoryPageRequest();

        ApiPageRequest paging = ApiPageRequest.unpaged();

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<CategoryPagedResponse> content = service.getCategoryList(request, paging);

        List<LabelValuePair> options = content.stream().map(option -> {
            String label = option.getName();
            String value = option.getId();
            return new LabelValuePair(label, value);
        }).toList();

        return ApiResponse.ok(options);
    }

}
