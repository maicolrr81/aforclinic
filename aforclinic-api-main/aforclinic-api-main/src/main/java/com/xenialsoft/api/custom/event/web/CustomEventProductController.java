package com.xenialsoft.api.custom.event.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.event.service.EventProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/events/products")
public class CustomEventProductController {

    private final EventProductService service;

    @GetMapping
    public ResponseEntity<?> getEventProducts(@RequestParam(required = false) String eventId) {
        return ApiResponse.ok(service.getEventProductsListByEventId(eventId));
    }

}
