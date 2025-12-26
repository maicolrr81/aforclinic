package com.xenialsoft.api.core.auth.model;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    /**
     * 리프레시 토큰 고유 식별자
     */
    private String id;

    /**
     * 사용자 고유 식별자
     */
    private String userId;

    /**
     * 생성일자
     */
    private LocalDateTime createdAt;

    /**
     * 만료일자
     */
    private LocalDateTime expireAt;

    /**
     * 마지막 사용일자
     */
    @Setter
    private LocalDateTime lastUsedAt;

}
