package com.xenialsoft.api.custom.beforeafter.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerPagedResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerResponse;
import com.xenialsoft.api.core.beforeafter.service.BeforeAfterBannerService;
import com.xenialsoft.api.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/beforeafters/banners")
public class CustomBeforeAfterBannerController {

    private final BeforeAfterBannerService service;

    /**
     * 배너 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBeforeAfterBannerList() {

        BeforeAfterBannerPageRequest request = new BeforeAfterBannerPageRequest();
        ApiPageRequest paging = ApiPageRequest.unpaged();

        List<BeforeAfterBannerPagedResponse> content = service.getBeforeAfterBannerList(request, paging);

        return ApiPagedResponse.ok(content);
    }

    /**
     * 배너 상세 조회
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getBeforeAfterBanner(@PathVariable String id) {
        BeforeAfterBannerResponse data = service.getBeforeAfterBannerById(id).orElseThrow(() -> {
            throw new NotFoundException("전후사진 배너가 없습니다. id : " + id);
        });

        return ApiResponse.ok(data);
    }

}
