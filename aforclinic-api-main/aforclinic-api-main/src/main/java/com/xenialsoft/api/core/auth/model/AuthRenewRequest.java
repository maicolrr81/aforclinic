package com.xenialsoft.api.core.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRenewRequest {

    /**
     * 리프레시 토큰 고유 식별자
     */
    private String refreshTokenId;

}
