package com.xenialsoft.api.custom.beforeafter.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.beforeafter.service.BeforeAfterTagsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/beforeafters/tags")
public class CustomBeforeAfterTagsController {

    private final BeforeAfterTagsService service;

    /**
     * 전후사진 태그 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBeforeAfterTagsList() {

        List<String> tagList = service.getBeforeAfterTagsList();

        return ApiResponse.ok(tagList);
    }

}
