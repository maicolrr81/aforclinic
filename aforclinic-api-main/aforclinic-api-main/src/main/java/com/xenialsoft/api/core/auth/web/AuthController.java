package com.xenialsoft.api.core.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.auth.model.AuthRefreshRequest;
import com.xenialsoft.api.core.auth.model.AuthRenewRequest;
import com.xenialsoft.api.core.auth.model.AuthRevokeRequest;
import com.xenialsoft.api.core.auth.model.AuthSuccessResponse;
import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    /**
     * 사용자의 credentials로 액세스 토큰과 리프레시 토큰 발급
     * 
     * @param principal
     * @return
     */
    @PostMapping("/issue")
    public ResponseEntity<?> issue(@AuthenticationPrincipal CustomUserDetails principal) {

        // 액세스 토큰, 리프레시 토큰 생성
        AuthSuccessResponse response = service.issue(principal.getId());

        // 액세스 토큰, 리프레시 토큰 반환
        return ApiResponse.ok(response);
    }

    /**
     * 리프레시 토큰으로 액세스 토큰 발급
     * 
     * @param request
     * @return
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AuthRefreshRequest request) {

        // 액세스 토큰 생성
        AuthSuccessResponse response = service.refresh(request.getRefreshTokenId());

        // 액세스 토큰 반환
        return ApiResponse.ok(response);
    }

    /**
     * 리프레시 토큰으로 액세스 토큰과 리프레시 토큰 발급
     * 
     * @param request
     * @return
     */
    @PostMapping("/renew")
    public ResponseEntity<?> renew(@RequestBody AuthRenewRequest request) {

        // 액세스 토큰, 리프레시 토큰 생성
        AuthSuccessResponse response = service.renew(request.getRefreshTokenId());

        // 액세스 토큰, 리프레시 토큰 반환
        return ApiResponse.ok(response);
    }

    /**
     * 리프레시 토큰을 무효화하여 로그아웃 처리
     * 
     * @param request
     * @return
     */
    @PostMapping("/revoke")
    public ResponseEntity<?> revoke(@RequestBody AuthRevokeRequest request) {

        // 리프레시 토큰 삭제
        service.revoke(request.getRefreshTokenId());

        return ApiResponse.ok("Logged out successfully.");
    }

}
