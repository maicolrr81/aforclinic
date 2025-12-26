package com.xenialsoft.api.core.auth.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.auth.service.JwtProvider;
import com.xenialsoft.api.core.user.model.UserResponse;
import com.xenialsoft.api.core.user.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider provider;
    private final UserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 액세스 토큰을 헤더에서 얻기
            Optional<String> optionalAccessToken = parseBearerToken(request);

            // 액세스 토큰이 없다면 다음 필터로 요청을 전달
            if (optionalAccessToken.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = optionalAccessToken.get();

            // 액세스 토큰 유효성 검증 실패시 401 오류 전달
            if (!provider.validateToken(accessToken)) {
                log.debug("#### 토큰 검증 실패");
                throw new IllegalArgumentException();
            }

            // 사용자 고유 식별자
            String userId = provider.extractUserId(accessToken);
            // 사용자 조회
            UserResponse user = service.getUserById(userId).orElseThrow(() -> {
                log.error("#### 사용자를 찾을 수 없습니다. => userId: {}", userId);
                throw new UsernameNotFoundException("User Not Found");
            });

            // 인증 객체
            // @formatter:off
            CustomUserDetails principal = CustomUserDetails.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .authorities(user.getRole())
                    .build();
            // @formatter:on

            // 빈 컨텍스트 생성
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

            // 인증 토큰 생성
            AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(principal, null,
                    principal.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 컨텍스트에 인증 토큰 설정
            securityContext.setAuthentication(authToken);

            // 컨텍스트 등록
            SecurityContextHolder.setContext(securityContext);

        } catch (Exception e) {
            log.warn("#### 토큰 인증에 실패했습니다. {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }

    private Optional<String> parseBearerToken(HttpServletRequest request) {
        // @formatter:off
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(auth -> auth.startsWith("Bearer "))
                .map(auth -> auth.substring(7));
        // @formatter:on
    }

}
