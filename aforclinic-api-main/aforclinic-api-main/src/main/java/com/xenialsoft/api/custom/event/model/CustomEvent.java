package com.xenialsoft.api.custom.event.model;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomEvent {

    /**
     * 이벤트 식별자
     */
    private String id;

    /**
     * 이벤트 제목
     */
    private String title;

    /**
     * 이벤트 설명
     */
    private String description;

    /**
     * 이벤트 이미지
     */
    private String imageId;

    /**
     * 이벤트 시작일자
     */
    private LocalDate startDate;

    /**
     * 이벤트 종료일자
     */
    private LocalDate endDate;

    /**
     * 이벤트 상품 중 최소 가격
     */
    private Long minDiscountedPrice;

    /**
     * 이벤트 상품 중 최대 가격
     */
    private Long maxDiscountedPrice;

}
