package com.xenialsoft.api.core.settings.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SiteSetting extends Auditable {

    /**
     * 사이트 설정 식별자
     */
    private String key;

    /**
     * 사이트 설정 값
     */
    private String value;

}
