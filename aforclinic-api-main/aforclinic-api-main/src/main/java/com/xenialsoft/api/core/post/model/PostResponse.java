package com.xenialsoft.api.core.post.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xenialsoft.api.core.attachment.model.AttachmentResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

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
     * 첨부파일
     */
    @Setter
    private List<AttachmentResponse> attachments;

    /**
     * 데이터 변환
     * 
     * @param post
     * @return
     */
    public static PostResponse from(Post post) {
        if (post == null) {
            return null;
        }
        PostResponse data = new PostResponse();
        data.id = post.getId();
        data.type = post.getType();
        data.title = post.getTitle();
        data.content = post.getContent();
        data.status = post.getStatus();
        data.createdAt = post.getCreatedAt();
        data.createdBy = post.getCreatedBy();
        data.modifiedAt = post.getModifiedAt();
        data.modifiedBy = post.getModifiedBy();
        return data;
    }

}
