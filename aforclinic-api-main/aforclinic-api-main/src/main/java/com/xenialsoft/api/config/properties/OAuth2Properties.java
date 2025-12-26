package com.xenialsoft.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

// @formatter:off
/**
 * OAuth2 관련 설정을 관리하는 설정 클래스입니다.
 * <p>
 * OAuth2 로그인 성공 후 클라이언트의 리디렉션 주소를 설정할 수 있습니다.
 * </p>
 *
 * @author xenialsoft
 * @since  2025.02.07
 */
// @formatter:on
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2Properties {

    /**
     * 기본 리디렉트 URI
     */
    private String defaultRedirectUri;

    /**
     * 성공 리디렉트 URI
     */
    private String successRedirectUri;
    
    /**
     * 카카오 어드민 키
     */
    private String kakaoAdminKey;

}
