package com.xenialsoft.api.core.post.model;

import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.xenialsoft.api.core.attachment.model.AttachmentRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    /**
     * 타입
     */
    @NotNull
    private PostType type;

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
     * 작성자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * 첨부파일
     */
    private List<@Valid AttachmentRequest> attachments;

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Post from(PostCreateRequest data) {
        Post post = new Post();
        post.setType(data.getType());
        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        if (StringUtils.isNotBlank(post.getContent())) {
            post.setContentText(Jsoup.parse(post.getContent()).text());
        }
        post.setStatus(PostStatus.PUBLISHED);
        post.setCreatedBy(data.getCreatedBy());
        return post;
    }

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static PostCreateRequest from(LandingCreateRequest data) {
        PostCreateRequest post = new PostCreateRequest();
        post.type = PostType.LANDING;
        post.title = data.getTitle();
        post.content = data.getContent();
        return post;
    }

}
