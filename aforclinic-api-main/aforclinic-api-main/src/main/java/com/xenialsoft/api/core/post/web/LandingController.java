package com.xenialsoft.api.core.post.web;

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
import com.xenialsoft.api.core.post.model.LandingCreateRequest;
import com.xenialsoft.api.core.post.model.LandingPageRequest;
import com.xenialsoft.api.core.post.model.LandingPagedResponse;
import com.xenialsoft.api.core.post.model.LandingUpdateRequest;
import com.xenialsoft.api.core.post.service.LandingService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/landings")
@PreAuthorize("hasRole('ADMIN')")
public class LandingController {

    private final LandingService service;

    @GetMapping
    public ResponseEntity<?> getLandingList(LandingPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<LandingPagedResponse> content = service.getLandingList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content, paging.getStartSequence(totalCount), true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getLandingByPostId(@PathVariable Long postId) {
        return ApiResponse
                .ok(service.getLandingByPostId(postId).orElseThrow(() -> new NotFoundException("랜딩페이지를 찾을 수 없습니다.")));
    }

    @PostMapping
    public ResponseEntity<?> createLanding(@RequestBody @Valid LandingCreateRequest request) {
        service.createLanding(request);
        return ApiResponse.ok();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updateLanding(@PathVariable Long postId,
            @RequestBody @Valid LandingUpdateRequest request) {
        request.setPostId(postId);
        service.updateLanding(request);
        return ApiResponse.ok();
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> partialUpdateLanding(@PathVariable Long postId,
            @RequestBody LandingUpdateRequest request) {
        request.setPostId(postId);
        service.updateLanding(request);
        return ApiResponse.ok();
    }

}
