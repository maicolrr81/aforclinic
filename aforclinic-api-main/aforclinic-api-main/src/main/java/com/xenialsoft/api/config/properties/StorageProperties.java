package com.xenialsoft.api.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 * 애플리케이션의 파일 저장소 관련 설정을 관리하는 클래스입니다.
 * <p>
 * 이 클래스는 {@code application.yml} 또는 {@code application.properties}에서
 * {@code app.storage} 접두어를 가진 설정 값을 자동으로 바인딩합니다.
 * <p>
 * 주요 설정 값:
 * <ul>
 *   <li>{@code app.storage.root-path} - 파일이 저장될 루트 경로</li>
 * </ul>
 *
 * 예제 설정 파일 (`application.yml`):
 * <pre>
 * app:
 *   storage:
 *     root-path: /data/storage
 * </pre>
 *
 * @author xenialsoft
 * @since 2025.02.07
 */
//@formatter:on
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.storage")
public class StorageProperties {

    /**
     * 파일이 저장될 루트 경로를 지정하는 속성입니다.
     */
    private String rootPath;

}
