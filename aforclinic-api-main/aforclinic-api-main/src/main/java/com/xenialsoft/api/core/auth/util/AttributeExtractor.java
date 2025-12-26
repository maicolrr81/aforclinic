package com.xenialsoft.api.core.auth.util;

import java.time.LocalDate;
import java.util.Map;

/**
 * OAuth2 제공자로부터 사용자 정보를 추출하는 클래스.
 */
public interface AttributeExtractor {

    String getId(Map<String, Object> attributes);

    String getNickname(Map<String, Object> attributes);

    String getEmail(Map<String, Object> attributes);

    String getPhoneNumber(Map<String, Object> attributes);

    LocalDate getBirthDate(Map<String, Object> attributes);

}
