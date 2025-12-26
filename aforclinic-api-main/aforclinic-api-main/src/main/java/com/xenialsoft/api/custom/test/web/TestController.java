package com.xenialsoft.api.custom.test.web;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.config.aspect.Auditable;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public ResponseEntity<?> test(HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok(authentication);
    }

    @GetMapping("/page")
    public ResponseEntity<?> page() {

        Pageable pageable = PageRequest.of(0, 10);

        List<Map<String, String>> content = List.of(Map.of("name", "mike1", "age", "10"),
                Map.of("name", "mike2", "age", "30"));

        int total = 2;

        return ApiResponse.ok(new PageImpl<>(content, pageable, total));

    }

    @Auditable
    @GetMapping("/audit")
    public ResponseEntity<?> test(TestDto dto) {
        log.debug("#### dto createdBy: {}", dto.getCreatedBy());
        log.debug("#### dto modifiedBy: {}", dto.getModifiedBy());
        return ResponseEntity.ok(String.join(", ", dto.getCreatedBy(), dto.getModifiedBy()));
    }

    @Getter
    public static class TestDto {

        @CreatedBy
        private String createdBy;

        @LastModifiedBy
        private String modifiedBy;

    }

}
