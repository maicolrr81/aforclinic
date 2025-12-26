package com.xenialsoft.api.core.event.web;

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
import com.xenialsoft.api.core.event.model.EventCreateRequest;
import com.xenialsoft.api.core.event.model.EventPageRequest;
import com.xenialsoft.api.core.event.model.EventPagedResponse;
import com.xenialsoft.api.core.event.model.EventUpdateRequest;
import com.xenialsoft.api.core.event.service.EventService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@PreAuthorize("hasRole('ADMIN')")
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<?> getEventList(EventPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<EventPagedResponse> content = service.getEventList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content, paging.getStartSequence(totalCount), true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable String id) {
        return ApiResponse.ok(service.getEventById(id).orElseThrow(() -> new NotFoundException("이벤트를 찾을 수 없습니다.")));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody @Valid EventCreateRequest request) {
        service.createEvent(request);
        return ApiResponse.ok();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable String id, @RequestBody @Valid EventUpdateRequest request) {
        request.setId(id);
        service.updateEvent(request);
        return ApiResponse.ok();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> deleteUpdateEvent(@PathVariable String id, @RequestBody EventUpdateRequest request) {
        request.setId(id);
        service.deleteUpdateEvent(request);
        return ApiResponse.ok();

    }
    
//    @PatchMapping("/{id}")
//    public ResponseEntity<?> partialUpdateEvent(@PathVariable String id, @RequestBody EventUpdateRequest request) {
//        request.setId(id);
//        service.updateEvent(request);
//        return ApiResponse.ok();
//
//    }

}
