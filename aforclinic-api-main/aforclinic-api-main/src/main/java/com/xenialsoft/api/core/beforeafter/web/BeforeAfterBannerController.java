package com.xenialsoft.api.core.beforeafter.web;

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
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerCreateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerPagedResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerStatus;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerUpdateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerUpdateStatusRequest;
import com.xenialsoft.api.core.beforeafter.service.BeforeAfterBannerService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/beforeafters/banners")
@PreAuthorize("hasRole('ADMIN')")
public class BeforeAfterBannerController {
    
    private final BeforeAfterBannerService service;

    /**
     * 배너 목록 조회
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getBeforeAfterBannerList(BeforeAfterBannerPageRequest request, ApiPageRequest paging) {
    	
        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }
        List<BeforeAfterBannerPagedResponse> content = service.getBeforeAfterBannerList(request, paging);
        
        // 순번 설정
        SequenceUtils.assign(content, paging.getStartSequence(totalCount), true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    /**
     * 배너 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBeforeAfterBanner(@PathVariable String id) {
        BeforeAfterBannerResponse data = service.getBeforeAfterBannerById(id).orElseThrow(() -> {
            throw new NotFoundException("전후사진 배너가 없습니다. id : " + id);
        });

        return ApiResponse.ok(data);
    }

    /**
     * 생성
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createBeforeAfterBanner(@RequestBody @Valid BeforeAfterBannerCreateRequest request) {
        service.createBeforeAfterBanner(request);
        return ApiResponse.ok();
    }
    
    /**
     * 수정
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeforeAfterBanner(@PathVariable String id, @RequestBody @Valid BeforeAfterBannerUpdateRequest request) {
        
        request.setId(id);
        service.updateBeforeAfterBanner(request);
        return ApiResponse.ok();
    }
    
    /**
     * 삭제로 상태 변경
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDeleteBeforeAfterBanner(@PathVariable String id) {
        
        BeforeAfterBannerUpdateStatusRequest request = new BeforeAfterBannerUpdateStatusRequest();
        request.setId(id);
        request.setStatus(BeforeAfterBannerStatus.DELETED);
        
        service.updateDeleteBeforeAfterBanner(request);
        return ApiResponse.ok();
    }

}
