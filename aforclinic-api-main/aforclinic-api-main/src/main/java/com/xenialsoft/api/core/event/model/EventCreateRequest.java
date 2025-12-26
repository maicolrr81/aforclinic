package com.xenialsoft.api.core.event.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;
import com.xenialsoft.api.core.post.model.PostCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventCreateRequest {

    /**
     * 이벤트명
     */
    @NotBlank
    private String title;

    /**
     * 이벤트설명
     */
    private String description;

    /**
     * 이미지 식별자
     */
    private String imageId;

    /**
     * 시작일
     */
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 종료일
     */
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 등록일시
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * 이벤트 상품
     */
    private List<@Valid EventProductRequest> products;

    /**
     * 게시글
     */
    private @Valid PostCreateRequest post;

    /**
     * 시작일과 종료일에 대한 유효성 검사
     * 
     * @return
     */
    @AssertTrue(message = "종료일은 시작일 이후 또는 같아야 합니다.")
    public boolean isEndDateAfterOrEqualStartDate() {
        return startDate != null && endDate != null && !endDate.isBefore(startDate);
    }

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Event from(EventCreateRequest data) {
        Event event = new Event();
        event.setId(IdUtils.generate());
        event.setTitle(data.getTitle());
        event.setDescription(data.getDescription());
        event.setImageId(data.getImageId());
        event.setStartDate(data.getStartDate());
        event.setEndDate(data.getEndDate());
        event.setStatus(EventStatus.ACTIVE);
        event.setCreatedBy(data.getCreatedBy());
        return event;
    }

}
