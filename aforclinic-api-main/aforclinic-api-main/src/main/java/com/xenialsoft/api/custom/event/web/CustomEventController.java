package com.xenialsoft.api.custom.event.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.custom.event.model.CustomEventPageRequest;
import com.xenialsoft.api.custom.event.service.CustomEventService;
import com.xenialsoft.api.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/events")
public class CustomEventController {

    private final CustomEventService service;

    /**
     * 메인 노출 이벤트의 상품 목록 조회
     * 
     * @return
     */
    @GetMapping("/main-events")
    public ResponseEntity<?> getMainEventProductList() {
        return ApiPagedResponse.ok(service.getMainEventProductList());
    }

    /**
     * 이벤트의 목록 조회
     * 
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getEventList(CustomEventPageRequest request) {
        return ApiPagedResponse.ok(service.getEventList(request, ApiPageRequest.unpaged()));
    }

    /**
     * 이벤트의 상세 목록 조회
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable String id) {
        return ApiResponse.ok(service.getEventById(id).orElseThrow(() -> new NotFoundException("이벤트를 찾을 수 없습니다.")));
    }

}
