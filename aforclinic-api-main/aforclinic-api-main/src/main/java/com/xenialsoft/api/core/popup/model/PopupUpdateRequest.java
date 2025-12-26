
package com.xenialsoft.api.core.popup.model;

import java.time.LocalDate;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PopupUpdateRequest {

    /**
     * ID
     */
    @Setter
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
     * 화면 표시 순서
     */
    private int displayOrder;

    /**
     * 상태
     */
    @Setter
    private PopupStatus status;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Popup from(PopupUpdateRequest data) {
        Popup popup = new Popup();
        popup.setId(data.getId());
        popup.setType(data.getType());
        popup.setPositionTop(data.getPositionTop());
        popup.setPositionLeft(data.getPositionLeft());
        popup.setTitle(data.getTitle());
        popup.setImageId(data.getImageId());
        popup.setLinkUri(data.getLinkUri());
        popup.setStartDate(data.getStartDate());
        popup.setEndDate(data.getEndDate());
        popup.setDisplayOrder(data.getDisplayOrder());
        popup.setStatus(data.getStatus());
        popup.setModifiedBy(data.getModifiedBy());
        return popup;
    }

}
