package com.xenialsoft.api.core.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.post.mapper.LandingMapper;
import com.xenialsoft.api.core.post.model.Landing;
import com.xenialsoft.api.core.post.model.LandingCreateRequest;
import com.xenialsoft.api.core.post.model.LandingPageRequest;
import com.xenialsoft.api.core.post.model.LandingPagedResponse;
import com.xenialsoft.api.core.post.model.LandingResponse;
import com.xenialsoft.api.core.post.model.LandingUpdateRequest;
import com.xenialsoft.api.core.post.model.PostCreateRequest;
import com.xenialsoft.api.core.post.model.PostResponse;
import com.xenialsoft.api.core.post.model.PostStatus;
import com.xenialsoft.api.core.post.model.PostUpdateRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LandingService {

    private final PostService post;
    private final LandingMapper mapper;

    @Transactional(readOnly = true)
    public long getTotalCount(LandingPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    @Transactional(readOnly = true)
    public List<LandingPagedResponse> getLandingList(LandingPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(LandingPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Optional<LandingResponse> getLandingByPostId(Long postId) {
        return Optional.ofNullable(mapper.selectByPostId(postId)).map(LandingResponse::from);
    }

    @Auditable
    @Transactional
    public void createLanding(LandingCreateRequest data) {

        // 게시글 추가
        PostResponse newPost = post.createPost(PostCreateRequest.from(data));

        // 랜딩 페이지 추가
        Landing landing = LandingCreateRequest.from(data);
        landing.setPostId(newPost.getId());

        int count = mapper.insert(landing);
        if (count != 1) {
            throw new ServiceException("랜딩 페이지 등록 중 오류가 발생했습니다.");
        }
    }

    @Auditable
    @Transactional
    public void updateLanding(LandingUpdateRequest data) {

        // 게시글 수정
        post.updatePost(PostUpdateRequest.from(data));

        // 랜딩 페이지 수정
        if (data.getStatus() != PostStatus.DELETED) {
            Landing landing = LandingUpdateRequest.from(data);
            int count = mapper.update(landing);
            if (count != 1) {
                throw new ServiceException("랜딩 페이지 수정 중 오류가 발생했습니다.");
            }
        }
    }

}
