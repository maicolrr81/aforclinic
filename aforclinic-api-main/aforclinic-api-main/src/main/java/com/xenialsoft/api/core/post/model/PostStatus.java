package com.xenialsoft.api.core.post.model;

import com.xenialsoft.api.common.Status;

public enum PostStatus implements Status {
    /**
     * 임시 저장
     */
    DRAFT,
    /**
     * 검토 대기
     */
    PENDING,
    /**
     * 게시
     */
    PUBLISHED,
    /**
     * 숨김
     */
    HIDDEN,
    /**
     * 삭제
     */
    DELETED;
}
