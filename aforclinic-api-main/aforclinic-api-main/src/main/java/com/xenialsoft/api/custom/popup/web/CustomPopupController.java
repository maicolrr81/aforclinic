package com.xenialsoft.api.custom.popup.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.core.popup.model.PopupPageRequest;
import com.xenialsoft.api.core.popup.model.PopupProgressStatus;
import com.xenialsoft.api.core.popup.service.PopupService;
import com.xenialsoft.api.custom.popup.model.CustomPopupPagedResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/popups")
public class CustomPopupController {

    private final PopupService service;

    @GetMapping
    public ResponseEntity<?> getPopupList(PopupPageRequest request) {

        ApiPageRequest paging = new ApiPageRequest();
        paging.setUnpaged(true);

        request.setProgressType(PopupProgressStatus.ONGOING);
        
        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<CustomPopupPagedResponse> content = service.getPopupList(request, paging).stream()
                .map(CustomPopupPagedResponse::from).toList();

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

}
