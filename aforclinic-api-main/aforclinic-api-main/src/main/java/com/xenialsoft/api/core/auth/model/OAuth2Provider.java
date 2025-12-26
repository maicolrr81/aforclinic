package com.xenialsoft.api.core.auth.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 제공자
 */
public enum OAuth2Provider {

    /**
     * 카카오를 통한 로그인
     */
    KAKAO;

    private static final OAuth2Provider[] VALUES;

    static {
        VALUES = values();
    }

    public static OAuth2Provider resolve(String providerName) {
        if (StringUtils.isBlank(providerName)) {
            return null;
        }
        for (OAuth2Provider provider : VALUES) {
            if (StringUtils.equalsIgnoreCase(provider.name(), providerName)) {
                return provider;
            }
        }
        return null;
    }

}
