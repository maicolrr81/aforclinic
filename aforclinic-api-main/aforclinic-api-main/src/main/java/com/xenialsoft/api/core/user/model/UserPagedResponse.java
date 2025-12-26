package com.xenialsoft.api.core.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.xenialsoft.api.common.Sequenceable;
import com.xenialsoft.api.core.auth.model.OAuth2Provider;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPagedResponse implements Sequenceable {

    /**
     * 순번
     */
    @Setter
    private Long sequence;

    /**
     * 사용자 식별자
     */
    private String id;

    /**
     * 로그인 식별자
     */
    private String username;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * 마지막 로그인 시간
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginAt;

    /**
     * 등록일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 등록자
     */
    private String createdBy;

    /**
     * 수정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    /**
     * 수정자
     */
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param user
     * @return
     */
    public static UserPagedResponse from(User user) {
        UserPagedResponse data = new UserPagedResponse();
        data.id = user.getId();
        data.username = user.getUsername();
        data.role = user.getRole();
        data.status = user.getStatus();
        data.nickname = user.getNickname();
        data.birthDate = user.getBirthDate();
        data.phoneNumber = user.getPhoneNumber();
        data.lastLoginAt = user.getLastLoginAt();
        data.createdAt = user.getCreatedAt();
        data.createdBy = user.getCreatedBy();
        data.modifiedAt = user.getModifiedAt();
        data.modifiedBy = user.getModifiedBy();
        return data;
    }

}
