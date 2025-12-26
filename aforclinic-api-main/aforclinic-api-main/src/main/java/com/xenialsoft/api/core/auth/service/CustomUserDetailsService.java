package com.xenialsoft.api.core.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.core.user.model.UserResponse;
import com.xenialsoft.api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @formatter:off
/**
 * Spring Security의 {@link UserDetailsService} 구현체로, 사용자 인증 정보를 로드하는 서비스입니다.
 * <p>
 * - 주어진 사용자명을 기반으로 {@link UserService}를 통해 사용자 정보를 조회합니다. <br>
 * - 사용자가 존재하지 않으면 {@link UsernameNotFoundException}을 발생시킵니다. <br>
 * - 조회된 정보를 {@link CustomUserDetails} 객체로 변환하여 반환합니다. <br>
 * </p>
 *
 * @author xenialsoft
 * @since  2025.02.07
 */
// @formatter:on
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    /**
     * 주어진 사용자명을 기반으로 사용자 정보를 조회하여 {@link UserDetails} 객체로 변환합니다.
     *
     * @param  username                  조회할 사용자명
     * @return                           {@link CustomUserDetails} 객체
     * @throws UsernameNotFoundException 사용자가 존재하지 않을 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserResponse> foundUser = service.getUserByUsername(username);
        UserResponse user = foundUser.orElseThrow(() -> {
            log.warn("사용자를 찾을 수 없습니다. => username: {}", username);
            throw new UsernameNotFoundException("User Not Found");
        });

        // @formatter:off
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole())
                .build();
        // @formatter:on
    }

}
