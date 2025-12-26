package com.xenialsoft.api.core.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.util.IdUtils;
import com.xenialsoft.api.config.properties.JwtProperties;
import com.xenialsoft.api.core.auth.mapper.RefreshTokenMapper;
import com.xenialsoft.api.core.auth.model.AuthSuccessResponse;
import com.xenialsoft.api.core.auth.model.RefreshToken;
import com.xenialsoft.api.core.user.model.UserResponse;
import com.xenialsoft.api.core.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider provider;
    private final JwtProperties properties;
    private final RefreshTokenMapper mapper;

    @Transactional
    public AuthSuccessResponse issue(String userId) {

        log.info("#### 다음 사용자에게 토큰이 발급됩니다. 사용자 식별자: {}", userId);

        // 사용자 정보 조회
        UserResponse user = userService.getUserById(userId).orElseThrow(() -> {
            log.error("#### 사용자를 찾을 수 없습니다. 사용자 식별자: {}", userId);
            throw new UsernameNotFoundException("User Not Found");
        });

        // 액세스 토큰 생성
        String newAccessToken = provider.generateAccessToken(user.getId());

        // 리프레시 토큰 생성 및 저장
        // @formatter:off
        RefreshToken newRefreshToken = RefreshToken.builder()
                .id(IdUtils.generate())
                .userId(userId)
                .expireAt(LocalDateTime.now().plusSeconds(properties.getRefreshTokenExpiration()))
                .build();
        
        mapper.insert(newRefreshToken);
        // @formatter:on

        log.info("다음 사용자에게 토큰이 발급됩니다. 사용자 식별자: {}, 액세스 토큰: {}, 리프레시 토큰: {}", userId, newAccessToken,
                newRefreshToken.getId());

        // 액세스 토큰, 리프레시 토큰 반환
        return AuthSuccessResponse.of(newAccessToken, newRefreshToken.getId());
    }

    @Transactional
    public AuthSuccessResponse refresh(String id) {

        RefreshToken refreshToken = verifyAndGetRefreshToken(id);

        // 사용자 정보 조회
        UserResponse user = userService.getUserById(refreshToken.getUserId()).orElseThrow(() -> {
            throw new UsernameNotFoundException("User Not Found");
        });

        // 새로운 액세스 토큰 생성
        String newAccessToken = provider.generateAccessToken(user.getId());

        // 리프레시 토큰 사용 시간 갱신
        refreshToken.setLastUsedAt(LocalDateTime.now());
        mapper.update(refreshToken);

        // 새로운 액세스 토큰, 기존 리프레시 토큰 반환
        return AuthSuccessResponse.of(newAccessToken);
    }

    @Transactional
    public AuthSuccessResponse renew(String id) {

        RefreshToken refreshToken = verifyAndGetRefreshToken(id);

        // 이전 리프레시 토큰은 삭제
        mapper.delete(refreshToken.getId());

        // 사용자 정보 조회
        UserResponse user = userService.getUserById(refreshToken.getUserId()).orElseThrow(() -> {
            throw new UsernameNotFoundException("User Not Found");
        });

        String subject = user.getId();

        // 새로운 액세스 토큰 생성
        String newAccessToken = provider.generateAccessToken(subject);

        // 새로운 리프레시 토큰 생성 및 저장
        // @formatter:off
        RefreshToken newRefreshToken = RefreshToken.builder()
                .id(IdUtils.generate())
                .userId(user.getId())
                .expireAt(LocalDateTime.now().plusSeconds(properties.getRefreshTokenExpiration()))
                .build();
        
        mapper.insert(newRefreshToken);
        // @formatter:on

        // 토큰 생성 후 반환
        return AuthSuccessResponse.of(newAccessToken, newRefreshToken.getId());
    }

    @Transactional
    public void revoke(String id) {
        mapper.delete(id);
    }

    /**
     * 리프레시 토큰 조회
     * 
     * @param id
     * @return
     */
    private RefreshToken verifyAndGetRefreshToken(String id) {
        // 리프레시 토큰 고유 식별자로 리프레시 토큰 조회
        RefreshToken refreshToken = mapper.findById(id);
        if (refreshToken == null) {
            log.error("#### 리프레시 토큰을 찾을 수 없습니다. 리프레시 토큰 식별자: {}", id);
            throw new IllegalArgumentException("리프레시 토큰을 찾을 수 없습니다.");
        }
        // 토큰 만료 여부 확인
        if (refreshToken.getExpireAt().isBefore(LocalDateTime.now())) {
            log.error("#### 리프레시 토큰이 만료되었습니다. 리프레시 토큰 식별자: {}", refreshToken.getId());
            mapper.delete(id);
            throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
        }
        return refreshToken;
    }

}
