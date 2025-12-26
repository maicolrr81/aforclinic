package com.xenialsoft.api.core.post.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPagedResponse implements Sequenceable {

    /**
     * 순번
     */
    @Setter
    private Long sequence;

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
     * 데이터 변환
     * 
     * @param post
     * @return
     */
    public static PostPagedResponse from(Post post) {
        PostPagedResponse data = new PostPagedResponse();
        data.id = post.getId();
        data.type = post.getType();
        data.title = post.getTitle();
        data.status = post.getStatus();
        data.createdAt = post.getCreatedAt();
        data.createdBy = post.getCreatedBy();
        data.modifiedAt = post.getModifiedAt();
        data.modifiedBy = post.getModifiedBy();
        return data;
    }

}
