package com.xenialsoft.api.custom.post.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xenialsoft.api.core.post.model.LandingResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomLandingResponse {

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
     * 데이터 변환
     * 
     * @param landing
     * @return
     */
    public static CustomLandingResponse from(LandingResponse landing) {
        if (landing == null) {
            return null;
        }
        CustomLandingResponse data = new CustomLandingResponse();
        data.postId = landing.getPostId();
        data.title = landing.getTitle();
        data.description = landing.getDescription();
        data.content = landing.getContent();
        return data;
    }

}
