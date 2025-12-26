package com.xenialsoft.api.core.post.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LandingPageRequest {

    /**
     * 상태
     */
    private PostStatus status;

    /**
     * 검색어
     */
    private String text;

}
