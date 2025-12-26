package com.xenialsoft.api.core.popup.web;

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
import com.xenialsoft.api.core.popup.model.PopupCreateRequest;
import com.xenialsoft.api.core.popup.model.PopupPageRequest;
import com.xenialsoft.api.core.popup.model.PopupPagedResponse;
import com.xenialsoft.api.core.popup.model.PopupResponse;
import com.xenialsoft.api.core.popup.model.PopupUpdateRequest;
import com.xenialsoft.api.core.popup.service.PopupService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/popups")
@PreAuthorize("hasRole('ADMIN')")
public class PopupController {

    private final PopupService service;

    /**
     * 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getPageList(PopupPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<PopupPagedResponse> content = service.getPopupList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    /**
     * 상세 찾기
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPopup(@PathVariable String id) {

        PopupResponse data = service.getPopupById(id).orElseThrow(() -> {
            throw new NotFoundException("아이디를 검색했으나, 찾지 못함: {}", id);
        });

        return ApiResponse.ok(data);
    }

    /**
     * 추가
     * 
     * @param principal
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createPopup(@RequestBody @Valid PopupCreateRequest request) {

        service.createPopup(request);

        return ApiResponse.ok();
    }

    /**
     * 수정
     * 
     * @param id
     * @param popup
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePopup(@PathVariable String id, @RequestBody @Valid PopupUpdateRequest request) {
        request.setId(id);
        service.updatePopup(request);
        return ApiResponse.ok();
    }

    /**
     * 수정
     * 
     * @param id
     * @param request
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdatePopup(@PathVariable String id, @RequestBody PopupUpdateRequest request) {
        request.setId(id);
        service.updatePopup(request);
        return ApiResponse.ok();
    }

    /**
     * 순서 재정렬
     * 
     * @param request
     * @return
     */
    @PutMapping
    public ResponseEntity<?> updatePopups(@RequestBody List<@Valid PopupUpdateRequest> request) {
        service.reorderPopups(request);
        return ApiResponse.ok();
    }

}
