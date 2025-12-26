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
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterCreateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterPagedResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterStatus;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterUpdateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterUpdateStatusRequest;
import com.xenialsoft.api.core.beforeafter.service.BeforeAfterService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/beforeafters")
@PreAuthorize("hasRole('ADMIN')")
public class BeforeAfterController {

    private final BeforeAfterService service;
    
    /**
     * 전후사진 목록 조회
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getBeforeAfterList(BeforeAfterPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }
        List<BeforeAfterPagedResponse> content = service.getBeforeAfterList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content, paging.getStartSequence(totalCount), true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }
    
    /**
     * 전후사진 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBeforeAfter(@PathVariable String id) {
        BeforeAfterResponse data = service.getBeforeAfterById(id).orElseThrow(() -> {
            throw new NotFoundException("전후사진이 없습니다. id : " + id);
        });

        return ApiResponse.ok(data);
    }
    
    /**
     * 생성
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createBeforeAfter(@RequestBody @Valid BeforeAfterCreateRequest request) {
    	
        service.createBeforeAfter(request);
        return ApiResponse.ok();
    }
    
    /**
     * 수정
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeforeAfter(@PathVariable String id, @RequestBody @Valid BeforeAfterUpdateRequest request) {
        
        request.setId(id);
        service.updateBeforeAfter(request);
        return ApiResponse.ok();
    }
    
    /**
     * 삭제로 상태 변경
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDeleteBeforeAfter(@PathVariable String id) {
        
        BeforeAfterUpdateStatusRequest request = new BeforeAfterUpdateStatusRequest();
        request.setId(id);
        request.setStatus(BeforeAfterStatus.DELETED);
        
        service.updateDeleteBeforeAfter(request);
        return ApiResponse.ok();
    }

}
