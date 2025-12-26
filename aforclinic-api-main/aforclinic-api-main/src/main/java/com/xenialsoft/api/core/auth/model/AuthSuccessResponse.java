package com.xenialsoft.api.core.auth.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthSuccessResponse {

    /**
     * 액세스 토큰
     */
    private final String accessToken;

    /**
     * 리프레시 토큰
     */
    private final String refreshToken;

    public static AuthSuccessResponse of(String accessToken) {
        return of(accessToken, null);
    }

    public static AuthSuccessResponse of(String accessToken, String refreshToken) {
        return new AuthSuccessResponse(accessToken, refreshToken);
    }

}
