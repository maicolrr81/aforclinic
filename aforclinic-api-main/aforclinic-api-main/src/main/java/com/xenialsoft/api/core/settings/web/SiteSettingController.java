package com.xenialsoft.api.core.settings.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.settings.model.SiteSettingRequest;
import com.xenialsoft.api.core.settings.service.SiteSettingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/site-settings")
public class SiteSettingController {

    private final SiteSettingService service;

    @GetMapping
    public ResponseEntity<?> getSiteSettings() {
        return ApiResponse.ok(service.getSettings());
    }

    @PostMapping
    public ResponseEntity<?> saveSiteSettings(@RequestBody @Valid SiteSettingRequest request) {
        service.saveSetting(request);
        return ApiResponse.ok();
    }

}
