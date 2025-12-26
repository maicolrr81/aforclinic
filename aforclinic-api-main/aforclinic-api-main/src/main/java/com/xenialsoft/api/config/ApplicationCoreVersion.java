package com.xenialsoft.api.config;

import lombok.experimental.UtilityClass;

/**
 * 애플리케이션의 핵심 버전 정보를 제공하는 유틸리티 클래스입니다.
 *
 * <p>
 * 이 클래스는 상속 및 인스턴스 생성을 방지하며, 애플리케이션의 `serialVersionUID` 값을 정의합니다.
 * </p>
 *
 * @author xenialsoft
 * @since  2025.02.07
 */
@UtilityClass
public class ApplicationCoreVersion {

    public final long SERIAL_VERSION_UID = 20241216L;

}
