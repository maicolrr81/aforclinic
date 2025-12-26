package com.xenialsoft.api.core.event.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.event.mapper.EventProductMapper;
import com.xenialsoft.api.core.event.model.EventProductRequest;
import com.xenialsoft.api.core.event.model.EventProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventProductService {

    private final EventProductMapper mapper;

    @Transactional(readOnly = true)
    public List<EventProductResponse> getEventProductsListByEventId(String eventId) {
        return mapper.selectListById(eventId).stream().map(EventProductResponse::from).toList();
    }

    @Transactional
    public void createEventProduct(EventProductRequest data) {
        mapper.insert(EventProductRequest.from(data));
    }

    @Transactional
    public void deleteEventProductByEventId(String eventId) {
        mapper.delete(eventId);
    }

    @Transactional
    public void saveEventProducts(String eventId, List<EventProductRequest> products, boolean clean) {
        if (clean) {
            deleteEventProductByEventId(eventId);
        }
        if (products == null || products.isEmpty()) {
            return;
        }
        for (EventProductRequest product : products) {
            product.setEventId(eventId);
            createEventProduct(product);
        }
    }

}
