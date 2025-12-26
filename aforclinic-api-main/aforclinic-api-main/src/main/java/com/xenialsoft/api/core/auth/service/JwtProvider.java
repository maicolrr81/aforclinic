package com.xenialsoft.api.core.auth.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.xenialsoft.api.config.properties.JwtProperties;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties properties;

    /**
     * 비밀키
     * 
     * @return
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(properties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 액세스 토큰 생성
     * 
     * @param subject
     * @return
     */
    public String generateAccessToken(String subject) {
        long amount = properties.getAccessTokenExpiration();
        Date expiration = Date.from(Instant.now().plusSeconds(amount));
        // @formatter:off
        return Jwts.builder()
                .subject(subject)
                .expiration(expiration)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
        // @formatter:on
    }

    /**
     * 리프레시 토큰 생성
     * 
     * @param subject
     * @return
     */
    public String generateRefreshToken(String subject) {
        long amount = properties.getRefreshTokenExpiration();
        Date expiration = Date.from(Instant.now().plusSeconds(amount));
        // @formatter:off
        return Jwts.builder()
                .subject(subject)
                .expiration(expiration)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
        // @formatter:on
    }

    /**
     * 토큰에서 사용자 고유 식별자 추출
     * 
     * @param jwt
     * @return
     */
    public String extractUserId(String jwt) {
        // @formatter:off
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
        // @formatter:on
    }

    /**
     * 토큰 검증
     * 
     * @param accessToken
     * @return
     */
    public boolean validateToken(String jwt) {
        try {
            // @formatter:off
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt);
            // @formatter:on
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
