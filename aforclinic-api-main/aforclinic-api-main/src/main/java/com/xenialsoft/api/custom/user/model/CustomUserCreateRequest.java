package com.xenialsoft.api.custom.user.model;

import java.util.List;

import com.xenialsoft.api.core.user.model.UserAgreementCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserCreateRequest {

    /**
     * 계정 식별자
     */
    @NotBlank
    private String username;

    /**
     * 비밀번호
     */
    @NotBlank
    private String password;

    /**
     * 사용자명
     */
    @NotBlank
    private String nickname;

    /**
     * 연락처
     */
    @NotBlank
    private String phoneNumber;

    /**
     * 동의내역
     */
    @NotEmpty
    private List<@Valid UserAgreementCreateRequest> agreements;

}
