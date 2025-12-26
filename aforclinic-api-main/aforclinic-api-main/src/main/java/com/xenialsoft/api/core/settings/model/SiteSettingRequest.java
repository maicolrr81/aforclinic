package com.xenialsoft.api.core.settings.model;

import java.util.Map;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SiteSettingRequest {

    /**
     * 사이트 설정
     */
    @NotEmpty
    private Map<String, String> settings;

    /**
     * 작성자
     */
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

}
