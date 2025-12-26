package com.xenialsoft.api.custom.post.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.post.service.LandingService;
import com.xenialsoft.api.custom.post.model.CustomLandingResponse;
import com.xenialsoft.api.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/landings")
public class CustomLandingController {

    private final LandingService service;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getLandingByPostId(@PathVariable Long postId) {
        return ApiResponse.ok(service.getLandingByPostId(postId).map(CustomLandingResponse::from)
                .orElseThrow(() -> new NotFoundException("랜딩페이지를 찾을 수 없습니다.")));
    }

}
