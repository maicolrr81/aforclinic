package com.xenialsoft.api.core.beforeafter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.beforeafter.mapper.BeforeAfterBannerMapper;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBanner;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerCreateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerPagedResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerResponse;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerUpdateRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerUpdateStatusRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeforeAfterBannerService {

    private final BeforeAfterBannerMapper mapper;

    @Transactional(readOnly = true)
    public List<BeforeAfterBannerPagedResponse> getBeforeAfterBannerList(BeforeAfterBannerPageRequest request,
            ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(BeforeAfterBannerPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public long getTotalCount(BeforeAfterBannerPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    @Transactional(readOnly = true)
    public Optional<BeforeAfterBannerResponse> getBeforeAfterBannerById(String id) {

        BeforeAfterBanner banner = mapper.selectById(id);
        BeforeAfterBannerResponse res = BeforeAfterBannerResponse.from(banner);

        return Optional.ofNullable(res);
    }

    @Auditable
    @Transactional
    public void createBeforeAfterBanner(BeforeAfterBannerCreateRequest request) {

        BeforeAfterBanner beforeAfterBanner = BeforeAfterBannerCreateRequest.from(request);

        int count = mapper.insert(beforeAfterBanner);
        if (count != 1) {
            throw new ServiceException("전후사진배너 등록 중 오류가 발생했습니다.");
        }
    }

    @Auditable
    @Transactional
    public void updateBeforeAfterBanner(BeforeAfterBannerUpdateRequest request) {

        BeforeAfterBanner beforeAfterBanner = BeforeAfterBannerUpdateRequest.from(request);

        int count = mapper.update(beforeAfterBanner);
        if (count != 1) {
            throw new ServiceException("전후사진 배너 변경 중 오류가 발생했습니다.");
        }
    }

    @Auditable
    @Transactional
    public void updateDeleteBeforeAfterBanner(BeforeAfterBannerUpdateStatusRequest request) {

        BeforeAfterBanner beforeAfterBanner = BeforeAfterBannerUpdateStatusRequest.from(request);

        int count = mapper.updateDelete(beforeAfterBanner);
        if (count != 1) {
            throw new ServiceException("전후사진 배너 삭제 중 오류가 발생했습니다.");
        }
    }
}
