package com.xenialsoft.api.core.post.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostPageRequest {

    /**
     * 타입
     */
    private PostType type;

    /**
     * 검색 시작 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 검색 종료 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 상태
     */
    private PostStatus status;

    /**
     * 검색어
     */
    private String text;

}
