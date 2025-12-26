package com.xenialsoft.api.core.auth.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.xenialsoft.api.config.properties.OAuth2Properties;
import com.xenialsoft.api.core.auth.model.AuthSuccessResponse;
import com.xenialsoft.api.core.auth.model.CustomUserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * OAuth2 로그인 성공 후, 세션을 유지하고 적절한 URL로 리디렉트하는 핸들러입니다.
 * <p>
 * 1. OAuth2 로그인 후 인증 객체를 SecurityContext에 저장하고 세션을 유지합니다.
 * 2. Referer가 존재하고, 화이트리스트 내의 도메인이라면 해당 Referer로 리디렉트합니다.
 * 3. 그렇지 않다면, 기본 리디렉트 URI로 이동합니다.
 * </p>
 *
 * @author xenialsoft
 * @since  2025.02.07
 */
// @formatter:on
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2UserSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2Properties oauth;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            response.sendRedirect(oauth.getDefaultRedirectUri());
            return;
        }

        // 인증 정보
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

        // 리프레시 토큰 생성
        AuthSuccessResponse res = authService.issue(principal.getId());

        // oauth 성공 리디렉션 처리
        response.sendRedirect(oauth.getSuccessRedirectUri() + "?refreshTokenId=" + res.getRefreshToken());
    }

}
