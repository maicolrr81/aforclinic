package com.xenialsoft.api.core.post.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Post extends Auditable {

    /**
     * 게시글 식별자
     */
    private Long id;

    /**
     * 게시글 타입
     */
    private PostType type;

    /**
     * 제목
     */
    private String title;

    /**
     * 내용
     */
    private String content;

    /**
     * 내용(검색용)
     */
    private String contentText;

    /**
     * 상태
     */
    private PostStatus status;

}
