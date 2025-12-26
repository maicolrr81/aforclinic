package com.xenialsoft.api.custom.user.web;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.user.model.UserCreateRequest;
import com.xenialsoft.api.core.user.model.UserUpdateRequest;
import com.xenialsoft.api.core.user.service.UserService;
import com.xenialsoft.api.custom.user.model.CustomUserCreateRequest;
import com.xenialsoft.api.custom.user.model.CustomUserDeleteRequest;
import com.xenialsoft.api.custom.user.model.CustomUserUpdateRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/users")
public class CustomUserController {

    private final UserService service;

    @GetMapping("/check-id")
    public ResponseEntity<?> check(@RequestParam(required = false) String username) {

        // 1. 필수값 체크
        if (StringUtils.isBlank(username)) {
            return ApiResponse.ok(false, "아이디는 필수입니다.");
        }

        // 2. 이메일 형식 체크
        if (!username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ApiResponse.ok(false, "아이디는 이메일 형식이어야 합니다.");
        }

        // 3. 중복 체크
        if (service.getUserByUsername(username).isPresent()) {
            return ApiResponse.ok(false, "이미 사용 중인 아이디입니다.");
        }

        // 4. 사용 가능
        return ApiResponse.ok(true, "사용 가능한 아이디입니다.");
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid CustomUserCreateRequest request) {

        // 사용자 생성
        service.createUser(UserCreateRequest.from(request));

        return ApiResponse.ok();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal CustomUserDetails principal)
            throws UserPrincipalNotFoundException {
        return ApiResponse.ok(service.getUserById(principal.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found")));
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody @Valid CustomUserUpdateRequest request) {

        request.setId(principal.getId());

        // 사용자 정보 변경
        service.updateUser(UserUpdateRequest.from(request));

        return ApiResponse.ok();
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal CustomUserDetails principal) {

        CustomUserDeleteRequest request = new CustomUserDeleteRequest(principal.getId());

        // 사용자 탈퇴 처리
        service.updateUser(UserUpdateRequest.from(request));

        return ApiResponse.ok();
    }

}
