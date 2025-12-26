package com.xenialsoft.api.core.user.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public enum UserRole implements GrantedAuthority {

    /**
     * 관리자
     */
    ADMIN("ROLE_ADMIN"),

    /**
     * 매니저
     */
    MANAGER("ROLE_MANAGER"),

    /**
     * 사용자
     */
    USER("ROLE_USER");

    /**
     * 역할
     */
    private final String authority;

    private static final UserRole[] VALUES;

    static {
        VALUES = values();
    }

    UserRole(String authority) {
        this.authority = authority;
    }

    @Nullable
    public static UserRole resolve(String rolename) {
        for (UserRole role : VALUES) {
            if (StringUtils.equalsIgnoreCase(role.name(), rolename)) {
                return role;
            }
        }
        return null;
    }

}
