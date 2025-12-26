package com.xenialsoft.api.core.user.web;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.user.model.UserCreateRequest;
import com.xenialsoft.api.core.user.model.UserPageRequest;
import com.xenialsoft.api.core.user.model.UserPagedResponse;
import com.xenialsoft.api.core.user.model.UserResponse;
import com.xenialsoft.api.core.user.model.UserUpdateRequest;
import com.xenialsoft.api.core.user.service.UserService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal CustomUserDetails principal)
            throws UserPrincipalNotFoundException {
        return ApiResponse.ok(service.getUserById(principal.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found")));
    }

    @GetMapping
    public ResponseEntity<?> getUserList(UserPageRequest request, ApiPageRequest paging) {
        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<UserPagedResponse> content = service.getUserList(request, paging);
        long start = paging.getStartSequence(totalCount);
        SequenceUtils.assign(content, start, true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return ApiResponse.ok(service.getUserById(id).orElseThrow(() -> new NotFoundException()));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateRequest request) {

        // 사용자 생성
        service.createUser(request);

        return ApiResponse.ok();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateRequest user) {

        // 식별자 설정
        user.setId(id);

        // 사용자 수정
        service.updateUser(user);

        return ApiResponse.ok();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateUser(@PathVariable String id, @RequestBody UserUpdateRequest user) {

        // 식별자 설정
        user.setId(id);

        // 사용자 수정
        service.updateUser(user);

        return ApiResponse.ok();
    }

    /**
     * 아이디 체크
     * 
     * @param id
     * @return
     */
    @GetMapping("/check-id/{username}")
    public ResponseEntity<?> duplicationUser(@PathVariable String username) {

        Optional<UserResponse> user = service.getUserByUsername(username);
        if (user.isPresent()) {
            return ApiResponse.ok(true);
        }
        return ApiResponse.ok(false);
    }

}
