package com.xenialsoft.api.core.beforeafter.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.beforeafter.mapper.BeforeAfterTagsMapper;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterTags;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeforeAfterTagsService {

    private final BeforeAfterTagsMapper mapper;

    @Transactional(readOnly = true)
    public List<String> getBeforeAfterTagsById(String id) {
        return mapper.selectListById(id);
    }

    @Transactional(readOnly = true)
    public long getTotalCount(String id) {
        return mapper.selectTotalCount(id);
    }

    /**
     * 활성화된 태그 목록
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<String> getBeforeAfterTagsList() {
        return mapper.selectTagList();
    }

    @Transactional
    public void create(BeforeAfterTags request) {

        int count = mapper.insert(request);
        if (count != 1) {
            throw new ServiceException("태그 추가 중 오류가 발생하였습니다.");
        }
    }

    @Transactional
    public void delete(String id) {
        mapper.delete(id);  // 삭제가 0일 수 있음
    }
}
