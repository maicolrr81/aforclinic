package com.xenialsoft.api.core.settings.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.settings.mapper.SiteSettingMapper;
import com.xenialsoft.api.core.settings.model.SiteSetting;
import com.xenialsoft.api.core.settings.model.SiteSettingRequest;
import com.xenialsoft.api.core.settings.model.SiteSettingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteSettingService {

    private final SiteSettingMapper mapper;

    @Transactional(readOnly = true)
    public SiteSettingResponse getSettings() {
        return SiteSettingResponse.from(mapper.selectList());
    }

    @Auditable
    @Transactional
    public void saveSetting(SiteSettingRequest data) {
        Map<String, String> settings = data.getSettings();
        if (settings == null || settings.isEmpty()) {
            return;
        }

        Map<String, String> existingSettings = mapper.selectList().stream()
                .collect(Collectors.toMap(SiteSetting::getKey, SiteSetting::getValue));

        for (Map.Entry<String, String> entry : settings.entrySet()) {
            SiteSetting setting = new SiteSetting();
            setting.setKey(entry.getKey());
            setting.setValue(entry.getValue());
            setting.setCreatedBy(data.getCreatedBy());
            setting.setModifiedBy(data.getModifiedBy());
            if (existingSettings.containsKey(setting.getKey())) {
                mapper.update(setting);
            } else {
                mapper.insert(setting);
            }
        }
    }

}
