package com.xenialsoft.api.core.event.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventPagedResponse implements Sequenceable {

    /**
     * 순번
     */
    @Setter
    private Long sequence;

    /**
     * 이벤트 식별자
     */
    private String id;

    /**
     * 제목
     */
    private String title;
    
    /**
     * 설명
     */
    private String description;
    
    /**
     * 이미지
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
     * post 식별자
     */
    private Long postId;
    
    /**
     * 상태
     */
    private EventStatus status;

    /**
     * 등록일자
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 등록자
     */
    private String createdBy;

    /**
     * 수정일자
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
     * @param event
     * @return
     */
    public static EventPagedResponse from(Event event) {
        EventPagedResponse data = new EventPagedResponse();
        data.id = event.getId();
        data.title = event.getTitle();
        data.description = event.getDescription();
        data.imageId = event.getImageId();
        data.startDate = event.getStartDate();
        data.endDate = event.getEndDate();
        data.postId = event.getPostId();
        data.status = event.getStatus();
        data.createdAt = event.getCreatedAt();
        data.createdBy = event.getCreatedBy();
        data.modifiedAt = event.getModifiedAt();
        data.modifiedBy = event.getModifiedBy();
        return data;
    }

}
