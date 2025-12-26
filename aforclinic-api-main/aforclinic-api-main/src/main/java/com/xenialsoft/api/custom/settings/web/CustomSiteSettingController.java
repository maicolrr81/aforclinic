package com.xenialsoft.api.custom.settings.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.settings.service.SiteSettingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/site-settings")
public class CustomSiteSettingController {

    private final SiteSettingService service;

    @GetMapping
    public ResponseEntity<?> getSiteSettings() {
        return ApiResponse.ok(service.getSettings());
    }

}
