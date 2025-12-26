package com.xenialsoft.api.core.user.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;
import com.xenialsoft.api.core.auth.model.OAuth2Provider;
import com.xenialsoft.api.custom.user.model.CustomUserCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {

    /**
     * 사용자 식별자
     */
    private String id;

    /**
     * 계정 식별자
     */
    @NotBlank
    private String username;

    /**
     * 비밀번호
     */
    @Setter
    @NotBlank
    private String password;

    /**
     * 역할
     */
    @NotBlank
    private UserRole role;

    /**
     * 사용자명
     */
    @NotBlank
    private String nickname;

    /**
     * 생년월일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * 전화번호
     */
    private String phoneNumber;

    /**
     * 제공자
     */
    private String provider;

    /**
     * 제공자의 사용자 식별자
     */
    private String providerId;

    /**
     * 등록자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * 동의내역
     */
    private List<@Valid UserAgreementCreateRequest> agreements;

    /**
     * 사용자 엔티티로 변환
     * 
     * @param data
     * @return
     */
    public static User from(UserCreateRequest data) {
        User user = new User();
        user.setId(IdUtils.generate());
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());
        user.setRole(data.getRole());
        user.setStatus(UserStatus.ACTIVE);
        user.setNickname(data.getNickname());
        user.setBirthDate(data.getBirthDate());
        user.setPhoneNumber(data.getPhoneNumber());
        user.setProvider(OAuth2Provider.resolve(data.getProvider()));
        user.setProviderId(data.getProviderId());
        user.setLastLoginAt(LocalDateTime.now());
        user.setCreatedBy(data.getCreatedBy());
        return user;
    }

    /**
     * 사용자 생성
     * 
     * @param custom
     * @return
     */
    public static UserCreateRequest from(CustomUserCreateRequest custom) {
        UserCreateRequest data = new UserCreateRequest();
        data.username = custom.getUsername();
        data.password = custom.getPassword();
        data.role = UserRole.USER;
        data.nickname = custom.getNickname();
        data.phoneNumber = custom.getPhoneNumber();
        data.agreements = custom.getAgreements();
        return data;
    }

    /**
     * 사용자 생성
     * 
     * @param username
     * @param nickname
     * @param birthDate
     * @param phoneNumber
     * @param provider
     * @param providerId
     * @return
     */
    public static UserCreateRequest of(String username, String nickname, LocalDate birthDate, String phoneNumber,
            String provider, String providerId) {
        UserCreateRequest data = new UserCreateRequest();
        data.username = username;
        data.role = UserRole.USER;
        data.nickname = nickname;
        data.birthDate = birthDate;
        data.phoneNumber = phoneNumber;
        data.provider = provider;
        data.providerId = providerId;
        return data;
    }

}
