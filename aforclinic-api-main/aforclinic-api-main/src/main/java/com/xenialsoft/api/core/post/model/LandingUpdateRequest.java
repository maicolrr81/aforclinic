package com.xenialsoft.api.core.post.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LandingUpdateRequest {

    /**
     * 게시글 식별자
     */
    @Setter
    @JsonIgnore
    private Long postId;

    /**
     * 제목
     */
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    /**
     * 내용
     */
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    /**
     * 설명
     */
    private String description;

    /**
     * 이미지 식별자
     */
    private String imageId;

    /**
     * 시작일
     */
    @NotNull(message = "시작일을 입력해주세요.")
    private LocalDate startDate;

    /**
     * 종료일
     */
    @NotNull(message = "종료일을 입력해주세요.")
    private LocalDate endDate;

    /**
     * 상태
     */
    @NotNull
    private PostStatus status;

    /**
     * 유효성 검사
     * 
     * @return
     */
    @AssertTrue(message = "종료일은 시작일 이후여야 합니다.")
    public boolean isEndDateAfterOrEqualStartDate() {
        return startDate != null && endDate != null && !endDate.isBefore(startDate);
    }

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Landing from(LandingUpdateRequest data) {
        Landing landing = new Landing();
        landing.setPostId(data.getPostId());
        landing.setDescription(data.getDescription());
        landing.setImageId(data.getImageId());
        landing.setStartDate(data.getStartDate());
        landing.setEndDate(data.getEndDate());
        return landing;
    }

}
