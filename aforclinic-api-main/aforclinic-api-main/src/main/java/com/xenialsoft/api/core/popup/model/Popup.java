package com.xenialsoft.api.core.popup.model;

import java.time.LocalDate;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 팝업 정보를 저장하는 엔티티 클래스.
 *
 * @author xenialsoft
 * @since 
 */
@Getter
@Setter
@NoArgsConstructor
public class Popup extends Auditable {
    
    /**
     * 팝업 고유 식별자
     */
    private String id;
    
    /**
     * 팝업 유형
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
     * 파일 고유 식별자
     */
    private String imageId;
    
    /**
     * 링크
     */
    private String linkUri;
    
    /**
     * 팝업 시작일
     */
    private LocalDate startDate;
    
    /**
     * 팝업 종료일
     */
    private LocalDate endDate;
    
    /**
     * 화면 표시 순서
     */
    private int displayOrder;
    
    /**
     * 상태
     */
    private PopupStatus status;
    
}
