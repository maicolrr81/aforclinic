package com.xenialsoft.api.core.settings.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SiteSettingResponse {

    /**
     * 사이트 설정
     */
    private Map<String, String> settings;

    /**
     * 데이터 변환
     * 
     * @param settings
     * @return
     */
    public static SiteSettingResponse from(List<SiteSetting> settings) {
        SiteSettingResponse data = new SiteSettingResponse();
        data.settings = settings.stream().collect(Collectors.toMap(SiteSetting::getKey, SiteSetting::getValue));
        return data;
    }

}
