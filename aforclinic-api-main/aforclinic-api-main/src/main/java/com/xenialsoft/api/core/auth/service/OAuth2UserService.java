package com.xenialsoft.api.core.auth.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.auth.util.AttributeExtractor;
import com.xenialsoft.api.core.auth.util.KakaoAttributeExtractor;
import com.xenialsoft.api.core.user.model.UserCreateRequest;
import com.xenialsoft.api.core.user.model.UserResponse;
import com.xenialsoft.api.core.user.model.UserRole;
import com.xenialsoft.api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * OAuth2 인증을 처리하는 사용자 서비스.
 * <p>
 * Spring Security의 {@link DefaultOAuth2UserService}를 확장하여
 * OAuth2 로그인 시 사용자 정보를 조회하고, 필요한 경우 저장 및 업데이트를 수행합니다.
 * </p>
 *
 * <h2>주요 기능</h2>
 * <ul>
 *     <li>OAuth2 제공자로부터 사용자 정보 조회</li>
 *     <li>사용자가 존재하지 않으면 신규 생성, 존재하면 정보 업데이트</li>
 *     <li>Spring Security의 {@link OAuth2User} 객체로 변환하여 반환</li>
 * </ul>
 *
 * <h2>사용 예시</h2>
 * <pre>
 * &#64;Bean
 * public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService oauth2UserService) throws Exception {
 *     http
 *         .oauth2Login(config -> config
 *             .userInfoEndpoint(userInfo -> userInfo.userService(oauth2UserService))
 *         );
 *     return http.build();
 * }
 * </pre>
 *
 * @author xenialsoft
 * @since  2025.02.07
 */
// @formatter:on
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserService service;

    /**
     * OAuth2 로그인 요청을 처리하고 사용자 정보를 반환합니다.
     *
     * @param userRequest OAuth2 로그인 요청 정보
     * @return OAuth2 인증된 사용자 정보
     */
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        // 제공자
        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 추출
        AttributeExtractor extractor = getAttributeExtractor(provider);

        Map<String, Object> attributes = oauth2User.getAttributes();
        String providerId = extractor.getId(attributes);
        String username = extractor.getEmail(attributes);
        String nickname = extractor.getNickname(attributes);
        String phoneNumber = extractor.getPhoneNumber(attributes);

        Optional<UserResponse> foundUser = service.getUserByUsername(username);

        // 사용자가 없으면 새로 생성
        UserResponse user = foundUser.orElseGet(() -> {
            // @formatter:off
            return service.createUser(UserCreateRequest.of(
                    username,
                    nickname,
                    null,
                    phoneNumber,
                    provider,
                    providerId));
            // @formatter:on
        });

        // @formatter:off
        return CustomUserDetails.builder()
                  .id(user.getId())
                  .username(user.getUsername())
                  .password(user.getPassword())
                  .authorities(UserRole.USER)
                  .attributes(attributes)
                  .build();
        // @formatter:on
    }

    private AttributeExtractor getAttributeExtractor(String provider) {
        return switch (provider) {
        case "kakao" -> new KakaoAttributeExtractor();
        default -> throw new IllegalArgumentException("Unsupported provider: " + provider);
        };
    }

}
