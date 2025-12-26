package com.xenialsoft.api.core.mainbanner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.mainbanner.mapper.MainBannerMapper;
import com.xenialsoft.api.core.mainbanner.model.MainBanner;
import com.xenialsoft.api.core.mainbanner.model.MainBannerCreateRequest;
import com.xenialsoft.api.core.mainbanner.model.MainBannerPageRequest;
import com.xenialsoft.api.core.mainbanner.model.MainBannerPagedResponse;
import com.xenialsoft.api.core.mainbanner.model.MainBannerResponse;
import com.xenialsoft.api.core.mainbanner.model.MainBannerUpdateRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainBannerService {

	private final MainBannerMapper mapper;

	@Transactional(readOnly = true)
	public List<MainBannerPagedResponse> getMainBannerList(MainBannerPageRequest request, ApiPageRequest paging) {
		return mapper.selectList(request, paging).stream().map(MainBannerPagedResponse::from).toList();
	}

	@Transactional(readOnly = true)
	public long getTotalCount(MainBannerPageRequest request) {
		return mapper.selectTotalCount(request);
	}

	@Transactional(readOnly = true)
	public Optional<MainBannerResponse> getMainBannerById(String id) {

		MainBanner mainBanner = mapper.selectById(id);
		MainBannerResponse res = MainBannerResponse.from(mainBanner);

		return Optional.ofNullable(res);
	}

	@Auditable
	@Transactional
	public void createMainBanner(MainBannerCreateRequest request) {

		MainBanner mainBanner = MainBannerCreateRequest.from(request);

		int count = mapper.insert(mainBanner);
		if (count != 1) {
			throw new ServiceException("메인화면 배너 등록 중 오류가 발생했습니다.");
		}
	}

	@Auditable
	@Transactional
	public void updateMainBanner(MainBannerUpdateRequest request) {

		MainBanner mainBanner = MainBannerUpdateRequest.from(request);

		int count = mapper.update(mainBanner);
		if (count != 1) {
			throw new ServiceException("메인화면 배너 수정 중 오류가 발생했습니다.");
		}
	}

	@Auditable
	@Transactional
	public void reorderMainBanners(List<MainBannerUpdateRequest> request) {
		request.stream().forEach(this::reorderMainBanner);
	}

	@Auditable
	@Transactional
	public void reorderMainBanner(MainBannerUpdateRequest request) {
		
		MainBanner mainBanner = MainBannerUpdateRequest.from(request);

		int count = mapper.reorder(mainBanner);
		if (count != 1) {
			throw new ServiceException("메인화면 배너 순서 변경 중 오류가 발생했습니다.");
		}
	}

	@Auditable
	@Transactional
	public void updateDeleteMainBanner(MainBannerUpdateRequest request) {

		MainBanner mainBanner = MainBannerUpdateRequest.from(request);

		int count = mapper.updateDelete(mainBanner);
		if (count != 1) {
			throw new ServiceException("메인화면 배너 삭제 중 오류가 발생하였습니다.");
		}
	}

}
