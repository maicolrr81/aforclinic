package com.xenialsoft.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.xenialsoft.api.config.properties.CorsProperties;
import com.xenialsoft.api.core.auth.filter.CustomUsernamePasswordAuthenticationFilter;
import com.xenialsoft.api.core.auth.filter.JwtAuthenticationFilter;
import com.xenialsoft.api.core.auth.service.JwtProvider;
import com.xenialsoft.api.core.auth.service.OAuth2UserService;
import com.xenialsoft.api.core.auth.service.OAuth2UserSuccessHandler;
import com.xenialsoft.api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * 애플리케이션의 보안 설정을 구성하는 클래스입니다.
 *
 * <p>
 * Spring Security를 활용하여 인증 및 권한 관리를 수행하며, CORS 설정, 쿠키 설정, OAuth2 로그인 설정, 인증 필터
 * 등의 보안 관련 기능을 정의합니다.
 * </p>
 *
 * @author xenialsoft
 * @since 2025.02.07
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    /**
     * {@link AuthenticationManager}를 빈으로 등록합니다.
     *
     * @param configuration Spring Security의 {@link AuthenticationConfiguration}
     * @return 인증 매니저 {@link AuthenticationManager}
     * @throws Exception 인증 매니저 생성 중 발생할 수 있는 예외
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * {@link PasswordEncoder}를 빈으로 등록합니다.
     *
     * <p>
     * 비밀번호 암호화를 위해 BCrypt 알고리즘을 사용합니다.
     * </p>
     *
     * @return BCrypt 기반의 {@link PasswordEncoder}
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS 설정을 위한 {@link CorsFilter}를 빈으로 등록합니다.
     *
     * <p>
     * 애플리케이션의 CORS 정책을 {@link CorsProperties}의 설정을 기반으로 구성합니다.
     * </p>
     *
     * @param cors {@link CorsProperties} 객체
     * @return 설정이 적용된 {@link CorsFilter}
     */
    @Bean
    CorsFilter corsFilter(CorsProperties cors) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(cors.getAllowedOrigins());
        config.setAllowedMethods(cors.getAllowedMethods());
        config.setAllowedHeaders(cors.getAllowedHeaders());
        config.setAllowCredentials(cors.isAllowCredentials());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    /**
     * Spring Security 필터 체인을 정의합니다.
     *
     * <p>
     * CSRF 및 폼 로그인 비활성화, CORS 설정 적용, 인증 및 권한 설정을 수행합니다.
     * </p>
     *
     * @param http                  {@link HttpSecurity} 객체
     * @param authenticationManager 인증 매니저
     * @param oauth2UserService     OAuth2 사용자 서비스
     * @param oauth2SuccessHandler  OAuth2 로그인 성공 핸들러
     * @return 설정이 적용된 {@link SecurityFilterChain}
     * @throws Exception 필터 체인 설정 중 발생할 수 있는 예외
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager,
            OAuth2UserService oauth2UserService, OAuth2UserSuccessHandler oauth2SuccessHandler, JwtProvider jwtProvider,
            UserService userService) throws Exception {

        // @formatter:off
        http
            // CSRF 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            // CORS 설정 적용
            .cors(Customizer.withDefaults())
            // 폼 로그인 비활성화 (클라이언트에서 폼 기반 로그인을 사용하지 않음)
            .formLogin(AbstractHttpConfigurer::disable)
            // HTTP 기본 인증 비활성화
            .httpBasic(AbstractHttpConfigurer::disable)
            // 익명 사용자 비활성화
            .anonymous(AbstractHttpConfigurer::disable)
            // 세션 설정
            .sessionManagement(config -> config
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // 로그아웃
            .logout(AbstractHttpConfigurer::disable)
            // 모든 요청은 인증 필요
            .authorizeHttpRequests(config -> config
                    .requestMatchers("/auth/**", "/files/**", "/public/**", "/login/**").permitAll()
                    .requestMatchers("/robots.txt").permitAll()
                    .anyRequest().authenticated())
            // OAuth2
            .oauth2Login(config -> config
                    .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService))
                    .successHandler(oauth2SuccessHandler))
            // 
            .exceptionHandling(config -> config
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
            ;
        // @formatter:on

        // 필터 등록
        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider, userService),
                UsernamePasswordAuthenticationFilter.class)
                .addFilter(new CustomUsernamePasswordAuthenticationFilter(authenticationManager));

        return http.build();
    }

}
