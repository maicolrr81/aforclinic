package com.xenialsoft.api.core.user.model;

import java.time.LocalDate;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.custom.user.model.CustomUserDeleteRequest;
import com.xenialsoft.api.custom.user.model.CustomUserUpdateRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {

    /**
     * 사용자 식별자
     */
    @Setter
    @JsonIgnore
    private String id;

    /**
     * 비밀번호
     */
    @Setter
    private String password;

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
     * 상태
     */
    @NotNull
    private UserStatus status;

    /**
     * 수정자
     */
    @LastModifiedBy
    private String modifiedBy;

    /**
     * 사용자 엔티티로 변환
     * 
     * @param data
     * @return
     */
    public static User from(UserUpdateRequest data) {
        User user = new User();
        user.setId(data.getId());
        user.setNickname(data.getNickname());
        user.setBirthDate(data.getBirthDate());
        user.setPhoneNumber(data.getPhoneNumber());
        user.setStatus(data.getStatus());
        user.setModifiedBy(data.getModifiedBy());
        return user;
    }

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static UserUpdateRequest from(CustomUserUpdateRequest data) {
        UserUpdateRequest user = new UserUpdateRequest();
        user.id = data.getId();
        user.nickname = data.getNickname();
        user.phoneNumber = data.getPhoneNumber();
        return user;
    }

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static UserUpdateRequest from(CustomUserDeleteRequest data) {
        UserUpdateRequest user = new UserUpdateRequest();
        user.id = data.getId();
        user.status = data.getStatus();
        return user;
    }

}
