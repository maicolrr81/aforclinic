package com.xenialsoft.api.custom.event.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xenialsoft.api.core.event.model.EventProductResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomEventResponse {

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
     * 상품 목록
     */
    @Setter
    private List<EventProductResponse> products;

    /**
     * 데이터 변환
     * 
     * @param event
     * @return
     */
    public static CustomEventResponse from(CustomEvent event) {
        if (event == null) {
            return null;
        }
        CustomEventResponse data = new CustomEventResponse();
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
