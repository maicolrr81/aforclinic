package com.xenialsoft.api.core.user.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

public enum Gender {

    /**
     * 여성
     */
    FEMALE,

    /**
     * 남성
     */
    MALE;

    private static final Gender[] VALUES;
    
    static {
        VALUES = values();
    }
    
    @Nullable
    public static Gender resolve(String gendername) {
        for (Gender gender : VALUES) {
            if (StringUtils.equalsIgnoreCase(gender.name(), gendername)) {
                return gender;
            }
        }
        return null;
    }
    
}
