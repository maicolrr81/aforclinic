package com.xenialsoft.api.core.popup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.popup.mapper.PopupMapper;
import com.xenialsoft.api.core.popup.model.Popup;
import com.xenialsoft.api.core.popup.model.PopupCreateRequest;
import com.xenialsoft.api.core.popup.model.PopupPageRequest;
import com.xenialsoft.api.core.popup.model.PopupPagedResponse;
import com.xenialsoft.api.core.popup.model.PopupResponse;
import com.xenialsoft.api.core.popup.model.PopupUpdateRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PopupService {

    private final PopupMapper mapper;

    /**
     * 팝업 목록 개수 조회
     * 
     * @param req
     * @return
     */
    @Transactional(readOnly = true)
    public long getTotalCount(PopupPageRequest req) {
        return mapper.selectTotalCount(req);
    }

    /**
     * 팝업 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @Transactional(readOnly = true)
    public List<PopupPagedResponse> getPopupList(PopupPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(PopupPagedResponse::from).toList();
    }

    /**
     * id로 조회
     * 
     * @param id
     * @return
     */
    @Auditable
    @Transactional(readOnly = true)
    public Optional<PopupResponse> getPopupById(String id) {
        return Optional.ofNullable(mapper.selectById(id)).map(PopupResponse::from);
    }

    /**
     * 추가
     * 
     * @param data
     */
    @Auditable
    @Transactional
    public void createPopup(PopupCreateRequest data) {

        Popup popup = PopupCreateRequest.from(data);

        int count = mapper.insert(popup);
        if (count != 1) {
            throw new ServiceException("팝업 생성 중 오류가 발생했습니다.");
        }
    }

    /**
     * 수정
     * 
     * @param popup
     */
    @Auditable
    @Transactional
    public void updatePopup(PopupUpdateRequest data) {

        Popup popup = PopupUpdateRequest.from(data);

        int count = mapper.update(popup);
        if (count != 1) {
            throw new ServiceException("팝업 수정 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 화면 표시 순서 변경
     * @param request
     */
    @Auditable
    @Transactional
    public void reorderPopups(List<PopupUpdateRequest> request) {
        request.stream().forEach(this::reorderPopup);
    }

    @Auditable
    @Transactional
    public void reorderPopup(PopupUpdateRequest request) {
        
        Popup popup = PopupUpdateRequest.from(request);

        int count = mapper.reorder(popup);
        if (count != 1) {
            throw new ServiceException("팝업 순서 변경 중 오류가 발생했습니다.");
        }
    }

}
