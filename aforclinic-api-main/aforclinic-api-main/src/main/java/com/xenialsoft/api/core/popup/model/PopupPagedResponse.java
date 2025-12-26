package com.xenialsoft.api.core.popup.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopupPagedResponse implements Sequenceable {

    @Setter
    private Long sequence;

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
     * 팝업 식별자
     */
    private String id;

    /**
     * 제목
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
     * 팝업 시작일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 팝업 종료일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 상태
     */
    private PopupStatus status;

    /**
     * 등록 일시(YYYY-MM-DD HH:MM:SS)
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
    public static PopupPagedResponse from(Popup popup) {
        PopupPagedResponse data = new PopupPagedResponse();
        data.id = popup.getId();
        data.type = popup.getType();
        data.positionTop = popup.getPositionTop();
        data.positionLeft = popup.getPositionLeft();
        data.title = popup.getTitle();
        data.imageId = popup.getImageId();
        data.linkUri = popup.getLinkUri();
        data.startDate = popup.getStartDate();
        data.endDate = popup.getEndDate();
        data.status = popup.getStatus();
        data.createdAt = popup.getCreatedAt();
        data.createdBy = popup.getCreatedBy();
        data.modifiedAt = popup.getModifiedAt();
        data.modifiedBy = popup.getModifiedBy();
        return data;
    }

}
