package com.xenialsoft.api.core.popup.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopupCreateRequest {

    /**
     * 팝업 유형
     */
    @NotBlank
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
    @NotBlank
    private String title;

    /**
     * 이미지 식별자
     */
    @NotBlank
    private String imageId;

    /**
     * 링크
     */
    private String linkUri;

    /**
     * 팝업 시작일
     */
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 팝업 종료일
     */
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    /**
     * 화면 표시 순서
     */
    private int displayOrder;

    /**
     * 등록자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Popup from(PopupCreateRequest data) {
        Popup popup = new Popup();
        popup.setId(IdUtils.generate());
        popup.setType(data.getType());
        popup.setPositionTop(data.getPositionTop());
        popup.setPositionLeft(data.getPositionLeft());
        popup.setTitle(data.getTitle());
        popup.setImageId(data.getImageId());
        popup.setLinkUri(data.getLinkUri());
        popup.setStartDate(data.getStartDate());
        popup.setEndDate(data.getEndDate());
        popup.setDisplayOrder(data.getDisplayOrder());
        popup.setStatus(PopupStatus.ACTIVE);
        popup.setCreatedBy(data.getCreatedBy());
        return popup;
    }

}
