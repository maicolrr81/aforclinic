package com.xenialsoft.api.core.post.model;

import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.core.attachment.model.AttachmentRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @Setter
    @JsonIgnore
    private Long id;
    
    private PostType type;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
    
    private PostStatus status;
    
    /**
     * 작성자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

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

//    /**
//     * 데이터 변환
//     * 
//     * @param data
//     * @return
//     */
//    public static Post from(PostRequest data) {
//        Post post = new Post();
//        post.setId(data.getId());
//        post.setSlug(SlugUtils.toSlug(data.getSlug()));
//        post.setTitle(data.getTitle());
//        post.setContent(data.getContent());
//        post.setContentText(Jsoup.parse(data.getContent()).text());
//        post.setStatus(data.getStatus());
//        post.setModifiedBy(data.getModifiedBy());
//        return post;
//    }
//    
//    /**
//     * 데이터 변환
//     * 
//     * @param data
//     * @return
//     */
//    public static Post from(PostCreateRequest data) {
//        Post post = new Post();
//        post.setType(data.getType());
//        post.setSlug(SlugUtils.toSlug(data.getSlug()));
//        post.setTitle(data.getTitle());
//        post.setContent(data.getContent());
//        post.setContentText(Jsoup.parse(data.getContent()).text());
//        post.setStatus(PostStatus.PUBLISHED);
//        post.setCreatedBy(data.getCreatedBy());
//        return post;
//    }

}
