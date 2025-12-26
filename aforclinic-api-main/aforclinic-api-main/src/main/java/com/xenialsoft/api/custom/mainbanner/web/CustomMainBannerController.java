package com.xenialsoft.api.custom.mainbanner.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.util.SequenceUtils;
import com.xenialsoft.api.core.mainbanner.model.MainBannerPageRequest;
import com.xenialsoft.api.core.mainbanner.model.MainBannerPagedResponse;
import com.xenialsoft.api.core.mainbanner.service.MainBannerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/public/mainbanners")
public class CustomMainBannerController {

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

}
