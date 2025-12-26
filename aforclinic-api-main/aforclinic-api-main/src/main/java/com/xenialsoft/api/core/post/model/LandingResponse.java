package com.xenialsoft.api.core.post.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LandingResponse {

    /**
     * 게시글 식별자
     */
    private Long postId;

    /**
     * 제목
     */
    private String title;

    /**
     * 설명
     */
    private String description;

    /**
     * 내용
     */
    private String content;

    /**
     * 이미지 식별자
     */
    private String imageId;

    /**
     * 시작일
     */
    private LocalDate startDate;

    /**
     * 종료일
     */
    private LocalDate endDate;

    /**
     * 상태
     */
    private PostStatus status;

    /**
     * 등록일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    /**
     * 등록자
     */
    private String createdBy;

    /**
     * 수정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedAt;

    /**
     * 수정자
     */
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param landing
     * @return
     */
    public static LandingResponse from(Landing landing) {
        if (landing == null) {
            return null;
        }
        LandingResponse data = new LandingResponse();
        data.postId = landing.getPostId();
        data.title = landing.getPost().getTitle();
        data.description = landing.getDescription();
        data.content = landing.getPost().getContent();
        data.imageId = landing.getImageId();
        data.startDate = landing.getStartDate();
        data.endDate = landing.getEndDate();
        data.status = landing.getPost().getStatus();
        data.createdAt = landing.getPost().getCreatedAt();
        data.createdBy = landing.getPost().getCreatedBy();
        data.modifiedAt = landing.getPost().getModifiedAt();
        data.modifiedBy = landing.getPost().getModifiedBy();
        return data;
    }

}
