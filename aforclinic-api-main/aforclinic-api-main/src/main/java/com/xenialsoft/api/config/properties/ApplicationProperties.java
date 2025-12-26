package com.xenialsoft.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

// @formatter:off
/**
 * 애플리케이션 전반적인 설정 값을 관리하는 프로퍼티 클래스입니다.
 * <p>
 * 이 클래스는 {@code application.yml} 또는 {@code application.properties}의
 * {@code app} 접두어를 가진 설정 값을 자동으로 바인딩합니다.
 * <p>
 * 주요 설정 값:
 * <ul>
 *   <li>{@code app.name} - 애플리케이션 이름</li>
 *   <li>{@code app.version} - 애플리케이션 버전</li>
 *   <li>{@code app.storage} - 스토리지 관련 설정 (하위 {@link StorageProperties})</li>
 * </ul>
 *
 * 예제 설정 파일 (`application.yml`):
 * <pre>
 * app:
 *   name: MyApplication
 *   version: 1.0.0
 *   storage:
 *     rootPath: /data/storage
 * </pre>
 *
 * @author xenialsoft
 * @since 2025.02.07
 */
// @formatter:on
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

}
