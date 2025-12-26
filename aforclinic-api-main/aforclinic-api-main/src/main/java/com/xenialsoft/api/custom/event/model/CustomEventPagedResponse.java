package com.xenialsoft.api.custom.event.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomEventPagedResponse {

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
     * 이벤트 이미지 식별자
     */
    private String imageId;

    /**
     * 이벤트 시작일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 이벤트 종료일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 이벤트 상품 중 최소 가격
     */
    private Long minDiscountedPrice;

    /**
     * 이벤트 상품 중 최대 가격
     */
    private Long maxDiscountedPrice;

    /**
     * 데이터 변환
     * 
     * @param event
     * @return
     */
    public static CustomEventPagedResponse from(CustomEvent event) {
        CustomEventPagedResponse data = new CustomEventPagedResponse();
        data.id = event.getId();
        data.title = event.getTitle();
        data.description = event.getDescription();
        data.imageId = event.getImageId();
        data.startDate = event.getStartDate();
        data.endDate = event.getEndDate();
        data.minDiscountedPrice = event.getMinDiscountedPrice();
        data.maxDiscountedPrice = event.getMaxDiscountedPrice();
        return data;
    }

}
