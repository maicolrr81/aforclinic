package com.xenialsoft.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;

/**
 * Spring Data Web 관련 설정을 담당하는 클래스입니다.
 * 
 * 이 클래스는 Spring Data의 웹 관련 기능을 설정하고, 페이지 직렬화 방식으로
 * {@link PageSerializationMode#VIA_DTO} 모드를 사용하도록 설정합니다. 이를 통해 `PageImpl` 객체가
 * DTO 형식으로 직렬화되어, 클라이언트 응답에서 더 명확하고 일관된 형태로 데이터를 제공할 수 있습니다.
 * 
 * {@link EnableSpringDataWebSupport} 어노테이션을 통해 Spring Data의 웹 관련 기능이 활성화됩니다.
 * 
 * @author xenialsoft
 * @since 2025.02.10
 * @see EnableSpringDataWebSupport
 * @see PageSerializationMode#VIA_DTO
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class DataWebConfig {

}
