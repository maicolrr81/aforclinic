package com.xenialsoft.api.core.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.product.mapper.CategoryMapper;
import com.xenialsoft.api.core.product.model.Category;
import com.xenialsoft.api.core.product.model.CategoryCreateRequest;
import com.xenialsoft.api.core.product.model.CategoryPageRequest;
import com.xenialsoft.api.core.product.model.CategoryPagedResponse;
import com.xenialsoft.api.core.product.model.CategoryReorderRequest;
import com.xenialsoft.api.core.product.model.CategoryResponse;
import com.xenialsoft.api.core.product.model.CategoryUpdateRequest;
import com.xenialsoft.api.core.product.model.CategoryUpdateStatusRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;

    /**
     * 목록 개수 조회
     * 
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public long getTotalCount(CategoryPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    /**
     * 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @Transactional(readOnly = true)
    public List<CategoryPagedResponse> getCategoryList(CategoryPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(CategoryPagedResponse::from).toList();
    }

    /**
     * id로 조회
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<CategoryResponse> getCategoryById(String id) {
        return Optional.ofNullable(mapper.selectById(id)).map(CategoryResponse::from);
    }

    /**
     * 이름 중복 체크
     * 
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public boolean checkDuplicateName(String name) {
        return mapper.selectByName(name) > 0;
    }

    /**
     * 생성
     * 
     * @param category
     */
    @Auditable
    @Transactional
    public void createCategory(CategoryCreateRequest request) {

        if (checkDuplicateName(request.getName()))
            throw new ServiceException("카테고리 이름이 중복됩니다.");

        Category category = CategoryCreateRequest.from(request);

        int count = mapper.insert(category);

        if (count != 1) {
            throw new ServiceException("생성 중 오류가 발생하였습니다.");
        }
    }

    /**
     * 수정
     * 
     * @param request
     */
    @Auditable
    @Transactional
    public void updateCategory(CategoryUpdateRequest request) {
        int count = mapper.update(CategoryUpdateRequest.from(request));
        if (count != 1) {
            throw new ServiceException("카테고리 수정 중 오류가 발생하였습니다.");
        }
    }

    /**
     * 수정
     * 
     * @param request
     */
    @Auditable
    @Transactional
    public void reorderCategories(List<CategoryReorderRequest> request) {
        request.stream().forEach(this::reorderCategory);
    }

    /**
     * 수정
     * 
     * @param request
     */
    @Auditable
    @Transactional
    public void reorderCategory(CategoryReorderRequest request) {
        int count = mapper.reorder(CategoryReorderRequest.from(request));
        if (count != 1) {
            throw new ServiceException("카테고리 수정 중 오류가 발생하였습니다.");
        }
    }

    /**
     * 삭제
     * 
     * @param id
     */
    @Auditable
    @Transactional
    public void deleteCategory(CategoryUpdateStatusRequest request) {

        String id = request.getId();

        if (mapper.selectById(id) == null)
            throw new ServiceException("존재하지 않은 카테고리입니다.");

        if (mapper.hasProduct(id) > 0)
            throw new ServiceException("카테고리에 등록된 상품을 먼저 삭제하세요.");

        if (mapper.hasBeforeAfter(id) > 0)
            throw new ServiceException("카테고리에 등록된 전후사진을 먼저 삭제하세요.");

        Category category = CategoryUpdateStatusRequest.from(request);

        int count = mapper.updateStatus(category);

        if (count != 1) {
            throw new ServiceException("카테고리 삭제 중 오류가 발생했습니다.");
        }
    }

}
