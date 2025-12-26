package com.xenialsoft.api.core.mainbanner.web;

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
import com.xenialsoft.api.core.mainbanner.model.MainBannerCreateRequest;
import com.xenialsoft.api.core.mainbanner.model.MainBannerPageRequest;
import com.xenialsoft.api.core.mainbanner.model.MainBannerPagedResponse;
import com.xenialsoft.api.core.mainbanner.model.MainBannerResponse;
import com.xenialsoft.api.core.mainbanner.model.MainBannerStatus;
import com.xenialsoft.api.core.mainbanner.model.MainBannerUpdateRequest;
import com.xenialsoft.api.core.mainbanner.service.MainBannerService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mainbanners")
@PreAuthorize("hasRole('ADMIN')")
public class MainBannerController {

    private final MainBannerService service;
    
    /**
     * 메인화면 배너 목록 조회
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getMainBannerList(MainBannerPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }
        List<MainBannerPagedResponse> content = service.getMainBannerList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }
    
    /**
     * 메인화면 배너 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getMainBanner(@PathVariable String id) {

    	MainBannerResponse data = service.getMainBannerById(id).orElseThrow(() -> {
            throw new NotFoundException("메인화면 배너가 없습니다.");
        });
        return ApiResponse.ok(data);
    }
    
    /**
     * 생성
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createMainBanner(@RequestBody @Valid MainBannerCreateRequest request) {
        service.createMainBanner(request);
        return ApiResponse.ok();
    }
    
    /**
     * 순서 재정렬
     * @param request
     * @return
     */
    @PutMapping
    public ResponseEntity<?> updateMainBanners(@RequestBody List<@Valid MainBannerUpdateRequest> request) {
        
        service.reorderMainBanners(request);
        return ApiResponse.ok();
    }
    
    /**
     * 수정
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMainBanner(@PathVariable String id, @RequestBody @Valid MainBannerUpdateRequest request) {
        
        request.setId(id);
        service.updateMainBanner(request);
        return ApiResponse.ok();
    }
    
    /**
     * 삭제로 상태 변경
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDeleteMainBanner(@PathVariable String id) {
        
    	MainBannerUpdateRequest request = new MainBannerUpdateRequest();
    	
        request.setId(id);
        request.setStatus(MainBannerStatus.DELETED);
        
        service.updateDeleteMainBanner(request);
        return ApiResponse.ok();
    }

}
