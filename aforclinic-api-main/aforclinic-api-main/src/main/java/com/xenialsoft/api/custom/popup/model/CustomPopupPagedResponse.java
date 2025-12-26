package com.xenialsoft.api.custom.popup.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xenialsoft.api.core.popup.model.PopupPagedResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomPopupPagedResponse {

    /**
     * 팝업 식별자
     */
    private String id;

    /**
     * 팝업 타입
     */
    private String type;

    /**
     * 팝업 제목
     */
    private String title;

    /**
     * 파일 식별자
     */
    private String imageId;

    /**
     * 링크
     */
    private String linkUri;

    /**
     * 포지션 top
     */
    private int positionTop;

    /**
     * 포지션 left
     */
    private int positionLeft;

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static CustomPopupPagedResponse from(PopupPagedResponse popup) {
        CustomPopupPagedResponse data = new CustomPopupPagedResponse();
        data.id = popup.getId();
        data.type = popup.getType();
        data.title = popup.getTitle();
        data.imageId = popup.getImageId();
        data.linkUri = popup.getLinkUri();
        data.positionLeft = popup.getPositionLeft();
        data.positionTop = popup.getPositionTop();
        return data;
    }

}
