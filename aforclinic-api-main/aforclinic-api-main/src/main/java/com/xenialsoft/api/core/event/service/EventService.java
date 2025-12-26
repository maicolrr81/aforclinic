package com.xenialsoft.api.core.event.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.util.LoggingUtils;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.event.mapper.EventMapper;
import com.xenialsoft.api.core.event.model.Event;
import com.xenialsoft.api.core.event.model.EventCreateRequest;
import com.xenialsoft.api.core.event.model.EventPageRequest;
import com.xenialsoft.api.core.event.model.EventPagedResponse;
import com.xenialsoft.api.core.event.model.EventResponse;
import com.xenialsoft.api.core.event.model.EventStatus;
import com.xenialsoft.api.core.event.model.EventUpdateRequest;
import com.xenialsoft.api.core.post.model.Post;
import com.xenialsoft.api.core.post.model.PostCreateRequest;
import com.xenialsoft.api.core.post.model.PostResponse;
import com.xenialsoft.api.core.post.model.PostStatus;
import com.xenialsoft.api.core.post.model.PostUpdateRequest;
import com.xenialsoft.api.core.post.service.PostService;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper mapper;

    private final PostService postService;
    private final EventProductService eventProductsService;

    @Transactional(readOnly = true)
    public List<EventPagedResponse> getEventList(EventPageRequest search, ApiPageRequest paging) {
        return mapper.selectList(search, paging).stream().map(EventPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public long getTotalCount(EventPageRequest search) {
        return mapper.selectTotalCount(search);
    }

    @Transactional(readOnly = true)
    public Optional<EventResponse> getEventById(String id) {

        Event event = mapper.selectById(id);
        if (event == null) {
            return Optional.empty();
        }

        EventResponse data = EventResponse.from(event);

        // 상품 목록 정보 조회
        data.setProducts(eventProductsService.getEventProductsListByEventId(id));

        return Optional.of(data);
    }

    @Auditable
    @Transactional
    public void createEvent(EventCreateRequest data) {

        Event event = EventCreateRequest.from(data);

        // 포스트 저장
        PostCreateRequest createPost = data.getPost();
        
        LoggingUtils.debug("#### createPost: {}", createPost);
        
        if (createPost != null) {
            PostResponse post = postService.createPost(createPost);
            
            LoggingUtils.debug("#### post.getId(): {}", post.getId());
            
            event.setPostId(post.getId());
        }

        // 이벤트 저장
        int count = mapper.insert(event);
        if (count != 1) {
            throw new ServiceException("이벤트 등록 중 오류가 발생했습니다.");
        }

        // 이벤트에 상품 저장
        eventProductsService.saveEventProducts(event.getId(), data.getProducts(), false);
    }

    @Auditable
    @Transactional
    public void updateEvent(EventUpdateRequest data) {

        Event event = EventUpdateRequest.from(data);

        // 포스트 수정
        if(data.getPostId() != null ) {
            PostUpdateRequest updatePost = data.getPost();
            if (updatePost != null) {
                postService.updatePost(updatePost);
            }   
        }

        // 이벤트 수정
        int count = mapper.update(event);
        if (count != 1) {
            throw new ServiceException("이벤트 수정 중 오류가 발생했습니다.");
        }

        // 이벤트에 상품 저장
        eventProductsService.saveEventProducts(event.getId(), data.getProducts(), true);
    }
    
    @Auditable
    @Transactional
    public void deleteUpdateEvent(EventUpdateRequest data) {
        
        Event event = mapper.selectById(data.getId());
        event.setId(data.getId());
        event.setModifiedBy(data.getModifiedBy());
        event.setStatus(EventStatus.DELETED);
        event.setTitle(null);
        event.setDescription(null);
        event.setImageId(null);
        event.setStartDate(null);
        event.setEndDate(null);
        
        // 포스트 상태를 삭제로 변경
        if(event.getPostId() != null) {
            Post post = new Post();
            post.setId(null);
            post.setStatus(PostStatus.DELETED);
            post.setModifiedBy(data.getModifiedBy());
            postService.deleteUpdatePost(null);
        }

        // 이벤트의 상태를 삭제로 변경
        int count = mapper.update(event);
        if (count != 1) {
            throw new ServiceException("이벤트 삭제 중 오류가 발생했습니다.");
        }

        // 이벤트에 상품은 삭제하지 않음
    }

}
