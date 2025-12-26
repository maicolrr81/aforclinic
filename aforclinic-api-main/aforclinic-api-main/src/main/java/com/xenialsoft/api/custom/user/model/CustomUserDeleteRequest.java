package com.xenialsoft.api.custom.user.model;

import com.xenialsoft.api.core.user.model.UserStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDeleteRequest {

    /**
     * 사용자 식별자
     */
    private final String id;

    /**
     * 사용자 상태
     */
    private final UserStatus status = UserStatus.DELETED;

}
