package com.xenialsoft.api.core.post.model;

import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.xenialsoft.api.core.attachment.model.AttachmentRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {

    /**
     * 게시글 식별자
     */
    @Setter
    @JsonIgnore
    private Long id;

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
     * 상태
     */
    @NotNull
    @Setter
    private PostStatus status;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

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
    public static Post from(PostUpdateRequest data) {
        Post post = new Post();
        post.setId(data.getId());
        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        if (StringUtils.isNotBlank(post.getContent())) {
            post.setContentText(Jsoup.parse(post.getContent()).text());
        }
        post.setStatus(data.getStatus());
        post.setModifiedBy(data.getModifiedBy());
        return post;
    }

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static PostUpdateRequest from(LandingUpdateRequest data) {
        PostUpdateRequest post = new PostUpdateRequest();
        post.id = data.getPostId();
        post.title = data.getTitle();
        post.content = data.getContent();
        post.status = data.getStatus();
        return post;
    }

}
