package com.xenialsoft.api.custom.event.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.event.model.EventProductResponse;
import com.xenialsoft.api.core.event.model.EventResponse;
import com.xenialsoft.api.core.event.service.EventProductService;
import com.xenialsoft.api.core.event.service.EventService;
import com.xenialsoft.api.custom.event.mapper.CustomEventMapper;
import com.xenialsoft.api.custom.event.model.CustomEvent;
import com.xenialsoft.api.custom.event.model.CustomEventPageRequest;
import com.xenialsoft.api.custom.event.model.CustomEventPagedResponse;
import com.xenialsoft.api.custom.event.model.CustomEventResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomEventService {

    private final EventService eventService;
    private final EventProductService eventProductService;

    private final CustomEventMapper mapper;
    
    @Transactional(readOnly = true)
    public List<CustomEventPagedResponse> getMainEventProductList() {
        return mapper.mainEventProductList().stream().map(CustomEventPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<CustomEventPagedResponse> getEventList(CustomEventPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(CustomEventPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CustomEventResponse> getEventById(String id) {
        CustomEvent event = mapper.selectById(id);
        if (event == null) {
            return Optional.empty();
        }

        CustomEventResponse data = CustomEventResponse.from(event);

        // 상품 목록 정보 조회
        List<EventProductResponse> products = eventProductService.getEventProductsListByEventId(id);
        data.setProducts(products);

        return Optional.of(data);
    }

    @Transactional(readOnly = true)
    public Optional<EventResponse> getEventPostById(String id) {
        return eventService.getEventById(id);
    }

}
