package com.xenialsoft.api.core.auth.util;

import java.time.LocalDate;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Kakao OAuth2 제공자로부터 사용자 정보를 추출하는 클래스.
 * 
 * 이 클래스는 {@link AttributeExtractor} 인터페이스를 구현하며, Kakao의 OAuth2 응답 형식에 맞게 사용자
 * 속성들을 추출하는 기능을 제공합니다.
 */
@Slf4j
public class KakaoAttributeExtractor implements AttributeExtractor {

    @Override
    public String getId(Map<String, Object> attributes) {
        if (attributes == null) {
            return null;
        }
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickname(Map<String, Object> attributes) {
        Map<String, Object> account = getAccount(attributes);
        if (account == null) {
            return null;
        }
        return (String) account.get("name");
    }

    @Override
    public String getEmail(Map<String, Object> attributes) {
        Map<String, Object> account = getAccount(attributes);
        if (account == null) {
            return null;
        }
        return (String) account.get("email");
    }

    @Override
    public String getPhoneNumber(Map<String, Object> attributes) {
        Map<String, Object> account = getAccount(attributes);
        if (account == null) {
            return null;
        }
        String phoneNumber = (String) account.get("phone_number");
        if (StringUtils.isNotBlank(phoneNumber) && StringUtils.startsWith(phoneNumber, "+82")) {
            phoneNumber = "0" + phoneNumber.substring(4).replace("-", "");
        }

        return phoneNumber;
    }

    @Override
    public LocalDate getBirthDate(Map<String, Object> attributes) {
        Map<String, Object> account = getAccount(attributes);
        if (account == null) {
            return null;
        }

        String birthyear = StringUtils.defaultString((String) account.get("birthyear"));
        String birthday = StringUtils.defaultString((String) account.get("birthday"));
        if (StringUtils.isAnyBlank(birthyear, birthday)) {
            return null;
        }

//        return String.join("-", birthyear, birthday.substring(0, 2), birthday.substring(2, 4));
        return null;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getAccount(Map<String, Object> attributes) {
        if (attributes == null) {
            return null;
        }
        return (Map<String, Object>) attributes.get("kakao_account");
    }

//    @Override
//    public KakaoUserInfo extract(Map<String, Object> attributes) {
//        return KakaoUserInfo.from(attributes);
//    }

}
