package com.xenialsoft.api.core.beforeafter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.beforeafter.mapper.BeforeAfterMapper;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfter;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterCategories;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterCreateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterPagedResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterTags;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterUpdateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterUpdateStatusRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeforeAfterService {

    private final BeforeAfterMapper mapper;
    private final BeforeAfterTagsService beforeAfterTagsService;
    private final BeforeAfterCategoriesService beforeAfterCategoriesService; 

    @Transactional(readOnly = true)
    public List<BeforeAfterPagedResponse> getBeforeAfterList(BeforeAfterPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(BeforeAfterPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public long getTotalCount(BeforeAfterPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    @Transactional(readOnly = true)
    public Optional<BeforeAfterResponse> getBeforeAfterById(String id) {

        BeforeAfter beforeAfter = mapper.selectById(id);
        if(beforeAfter.getBeforeAfterTagsList() != null 
            && beforeAfter.getBeforeAfterTagsList().size() == 1
            && beforeAfter.getBeforeAfterTagsList().get(0).getTag() == null) {
            beforeAfter.setBeforeAfterTagsList(null);
        }
        BeforeAfterResponse res = BeforeAfterResponse.from(beforeAfter);

        return Optional.ofNullable(res);
    }

    @Auditable
    @Transactional
    public void createBeforeAfter(BeforeAfterCreateRequest request) {

        BeforeAfter beforeAfter = BeforeAfterCreateRequest.from(request);

        int count = mapper.insert(beforeAfter);
        if (count != 1) {
            throw new ServiceException("전후사진 등록 중 오류가 발생했습니다.");
        }

        // 카테고리
        if(beforeAfter.getBeforeAfterCategoriesList() != null && beforeAfter.getBeforeAfterCategoriesList().size() > 0) {
        	for(BeforeAfterCategories beforeAfterCategories : beforeAfter.getBeforeAfterCategoriesList()) {
        		beforeAfterCategoriesService.create(beforeAfterCategories);
        	}
        }
        
        // 태그
        if (beforeAfter.getBeforeAfterTagsList() != null && beforeAfter.getBeforeAfterTagsList().size() > 0) {
            for (BeforeAfterTags beforeAfterTags : beforeAfter.getBeforeAfterTagsList()) {
                beforeAfterTagsService.create(beforeAfterTags);
            }
        }
    }

    @Auditable
    @Transactional
    public void updateBeforeAfter(BeforeAfterUpdateRequest request) {

        BeforeAfter beforeAfter = BeforeAfterUpdateRequest.from(request);

        int count = mapper.update(beforeAfter);
        if (count != 1) {
            throw new ServiceException("전후사진 수정 중 오류가 발생했습니다.");
        }
        
        // 카테고리 수정
        beforeAfterCategoriesService.delete(beforeAfter.getId());
        if(beforeAfter.getBeforeAfterCategoriesList() != null && beforeAfter.getBeforeAfterCategoriesList().size() > 0) {
            for(BeforeAfterCategories beforeAfterCategories : beforeAfter.getBeforeAfterCategoriesList()) {
                beforeAfterCategoriesService.create(beforeAfterCategories);
            }
        }
        
        // 태그 수정
        beforeAfterTagsService.delete(beforeAfter.getId());
        if (beforeAfter.getBeforeAfterTagsList() != null && beforeAfter.getBeforeAfterTagsList().size() > 0) {
            for (BeforeAfterTags beforeAfterTags : beforeAfter.getBeforeAfterTagsList()) {
                beforeAfterTagsService.create(beforeAfterTags);
            }
        }
    }

    @Auditable
    @Transactional
    public void updateDeleteBeforeAfter(BeforeAfterUpdateStatusRequest request) {

        BeforeAfter beforeAfter = BeforeAfterUpdateStatusRequest.from(request);

        int count = mapper.updateDelete(beforeAfter);
        if (count != 1) {
            throw new ServiceException("전후사진 삭제 중 오류가 발생하였습니다.");
        }

        // before_after_categories는 삭제하지 않음
        // before_after_tags는 삭제하지 않음
    }

}
