package com.xenialsoft.api.custom.beforeafter.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterResponse;
import com.xenialsoft.api.core.beforeafter.service.BeforeAfterService;
import com.xenialsoft.api.custom.beforeafter.model.CustomBeforeAfterPagedResponse;
import com.xenialsoft.api.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/beforeafters")
public class CustomBeforeAfterController {

    private final BeforeAfterService service;

    /**
     * 전후사진 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBeforeAfterList(@AuthenticationPrincipal CustomUserDetails principal,
            BeforeAfterPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<CustomBeforeAfterPagedResponse> content = service.getBeforeAfterList(request, paging).stream()
                .map(data -> CustomBeforeAfterPagedResponse.from(data, principal == null)).toList();

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    /**
     * 전후사진 상세 조회
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBeforeAfter(@PathVariable String id) {
        BeforeAfterResponse data = service.getBeforeAfterById(id).orElseThrow(() -> {
            throw new NotFoundException("전후사진이 없습니다. id : " + id);
        });
        return ApiResponse.ok(data);
    }

}
