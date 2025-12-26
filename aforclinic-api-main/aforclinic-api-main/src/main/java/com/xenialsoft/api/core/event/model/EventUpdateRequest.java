package com.xenialsoft.api.core.event.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.core.post.model.PostUpdateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class EventUpdateRequest {

    /**
     * 이벤트 식별자
     */
    @Setter
    @JsonIgnore
    private String id;

    /**
     * 이벤트명
     */
    @NotBlank
    private String title;

    /**
     * 이벤트 설명
     */
    private String description;

    /**
     * 이미지 식별자
     */
    private String imageId;

    /**
     * 게시글 식별자
     */
    @Setter
    @JsonIgnore
    private Long postId;

    /**
     * 시작일
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 종료일
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 상태
     */
    @NotNull
    @Setter
    private EventStatus status;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

    /**
     * 이벤트 상품
     */
    private List<@Valid EventProductRequest> products;

    /**
     * 게시글
     */
    private @Valid PostUpdateRequest post;

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
    public static Event from(EventUpdateRequest data) {
        Event event = new Event();
        event.setId(data.getId());
        event.setTitle(data.getTitle());
        event.setDescription(data.getDescription());
        event.setImageId(data.getImageId());
        event.setPostId(data.getPostId());
        event.setStartDate(data.getStartDate());
        event.setEndDate(data.getEndDate());
        event.setStatus(data.getStatus());
        event.setModifiedBy(data.getModifiedBy());
        return event;
    }

}
