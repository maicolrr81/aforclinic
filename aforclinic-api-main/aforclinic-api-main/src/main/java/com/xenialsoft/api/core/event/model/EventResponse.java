package com.xenialsoft.api.core.event.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.core.post.model.PostResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventResponse {

    /**
     * 이벤트 식별자
     */
    private String id;

    /**
     * 이벤트명
     */
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
     * 상태
     */
    private EventStatus status;

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
     * 상품 목록
     */
    @Setter
    private List<EventProductResponse> products;

    /**
     * 게시글
     */
    private PostResponse post;

    /**
     * 데이터 변환
     * 
     * @param event
     * @return
     */
    public static EventResponse from(Event event) {
        if (event == null) {
            return null;
        }
        EventResponse data = new EventResponse();
        data.id = event.getId();
        data.title = event.getTitle();
        data.description = event.getDescription();
        data.imageId = event.getImageId();
        data.startDate = event.getStartDate();
        data.endDate = event.getEndDate();
        data.status = event.getStatus();
        data.post = PostResponse.from(event.getPost());
        data.createdAt = event.getCreatedAt();
        data.createdBy = event.getCreatedBy();
        data.modifiedAt = event.getModifiedAt();
        data.modifiedBy = event.getModifiedBy();
        return data;
    }

}
