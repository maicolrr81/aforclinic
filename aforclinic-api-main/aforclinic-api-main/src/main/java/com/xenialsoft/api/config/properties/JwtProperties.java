package com.xenialsoft.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private String secretKey = "ThisIsSecretSecretSecretSecretSecretSecretkeyJWT";

    /**
     * 액세스 토큰 만료 시간(초 단위)
     */
    private long accessTokenExpiration = 60 * 60; // 1시간

    /**
     * 리프레시 토큰 만료 시간(초 단위)
     */
    private long refreshTokenExpiration = 60 * 60 * 24 * 7; // 7일

}
