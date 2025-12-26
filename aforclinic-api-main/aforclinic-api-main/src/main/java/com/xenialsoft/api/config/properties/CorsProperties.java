package com.xenialsoft.api.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

// @formatter:off
/**
 * CORS 설정을 관리하는 프로퍼티 클래스입니다.
 * <p>
 * {@code application.yml}의 {@code app.cors} 속성을 기반으로
 * CORS 관련 설정을 로드합니다.
 * <p>
 * 주요 설정 값:
 * <ul>
 *   <li>{@code app.cors.allowed-origins} - 허용할 Origin 목록</li>
 *   <li>{@code app.cors.allowed-methods} - 허용할 HTTP 메서드</li>
 *   <li>{@code app.cors.allowed-headers} - 허용할 헤더</li>
 *   <li>{@code app.cors.allow-credentials} - 인증 정보 포함 여부</li>
 *   <li>{@code app.cors.max-age} - 사전 요청 캐시 시간</li>
 * </ul>
 *
 * 예제 설정 파일 (`application.yml`):
 * <pre>
 * app:
 *   cors:
 *     allowed-origins:
 *       - "https://example.com"
 *       - "https://another.com"
 *     allowed-methods: "*"
 *     allowed-headers: "*"
 *     allow-credentials: true
 * </pre>
 *
 * @author xenialsoft
 * @since 2025.02.07
 */
// @formatter:on
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    /**
     * 허용할 Origin 목록입니다.
     */
    private List<String> allowedOrigins;

    /**
     * 허용할 HTTP 메서드 목록 (기본값: GET, POST)
     */
    private List<String> allowedMethods = List.of("GET", "POST");

    /**
     * 허용할 HTTP 헤더 목록 (기본값: 모든 헤더 허용)
     */
    private List<String> allowedHeaders = List.of("*");

    /**
     * 인증 정보를 포함할지 여부 (기본값: true)
     */
    private boolean allowCredentials = true;

}
