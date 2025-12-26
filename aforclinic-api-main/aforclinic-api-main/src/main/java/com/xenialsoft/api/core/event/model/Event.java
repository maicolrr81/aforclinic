package com.xenialsoft.api.core.event.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.common.Auditable;
import com.xenialsoft.api.core.post.model.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Event extends Auditable {

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
     * 포스트 식별자
     */
    private Long postId;

    /**
     * 상태
     */
    private EventStatus status;

    /**
     * 포스트
     */
    private Post post;

}
