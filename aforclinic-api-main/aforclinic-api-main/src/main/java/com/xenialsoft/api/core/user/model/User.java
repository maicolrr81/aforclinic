package com.xenialsoft.api.core.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.xenialsoft.api.common.Auditable;
import com.xenialsoft.api.core.auth.model.OAuth2Provider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User extends Auditable {

    /**
     * 사용자 식별자
     */
    private String id;

    /**
     * 로그인 식별자
     */
    private String username;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * 역할
     */
    private UserRole role;

    /**
     * 상태
     */
    private UserStatus status;

    /**
     * 사용자명
     */
    private String nickname;

    /**
     * 생년월일
     */
    private LocalDate birthDate;

    /**
     * 핸드폰 번호
     */
    private String phoneNumber;

    /**
     * 제공자
     */
    private OAuth2Provider provider;

    /**
     * 제공자의 사용자 식별자
     */
    private String providerId;

    /**
     * 마지막 로그인 일시
     */
    private LocalDateTime lastLoginAt;

}
