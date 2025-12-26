package com.xenialsoft.api.custom.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserUpdateRequest {

    /**
     * 사용자 식별자
     */
    @Setter
    @JsonIgnore
    private String id;

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

    public static CustomUserUpdateRequest of(String id) {
        CustomUserUpdateRequest request = new CustomUserUpdateRequest();
        request.id = id;
        return request;
    }

}
