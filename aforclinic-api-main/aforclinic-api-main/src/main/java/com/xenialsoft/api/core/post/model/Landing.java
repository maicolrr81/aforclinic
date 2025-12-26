package com.xenialsoft.api.core.post.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Landing {

    /**
     * 포스트 식별자
     */
    private Long postId;

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
    private LocalDate startDate;

    /**
     * 종료일
     */
    private LocalDate endDate;

    /**
     * 게시글
     */
    private Post post;

}
