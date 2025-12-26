package com.xenialsoft.api.core.popup.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopupResponse {

    /**
     * 팝업 식별자
     */
    private String id;

    /**
     * 유형
     */
    private String type;
    
    /**
     * 화면 상단으로부터의 거리(px)
     */
    private int positionTop;
    
    /**
     * 화면 좌측으로부터의 거리(px)
     */
    private int positionLeft;
    
    /**
     * 제목
     */
    private String title;

    /**
     * 이미지 식별자
     */
    private String imageId;

    /**
     * 링크
     */
    private String linkUri;

    /**
     * 시작일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 종료일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    /**
     * 화면 표시 순서
     */
    private int displayOrder;

    /**
     * 상태
     */
    private PopupStatus status;

    /**
     * 등록일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 등록자
     */
    private String createdBy;

    /**
     * 수정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    /**
     * 수정자
     */
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param popup
     * @return
     */
    public static PopupResponse from(Popup popup) {
        PopupResponse data = new PopupResponse();
        data.id = popup.getId();
        data.type = popup.getType();
        data.positionTop = popup.getPositionTop();
        data.positionLeft = popup.getPositionLeft();
        data.title = popup.getTitle();
        data.imageId = popup.getImageId();
        data.linkUri = popup.getLinkUri();
        data.startDate = popup.getStartDate();
        data.endDate = popup.getEndDate();
        data.displayOrder = popup.getDisplayOrder();
        data.status = popup.getStatus();
        data.createdAt = popup.getCreatedAt();
        data.createdBy = popup.getCreatedBy();
        data.modifiedAt = popup.getModifiedAt();
        data.modifiedBy = popup.getModifiedBy();
        return data;
    }

}
