package com.xenialsoft.api.core.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRevokeRequest {

    private String refreshTokenId;

}
