package com.xenialsoft.api.core.user.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.xenialsoft.api.core.auth.model.OAuth2Provider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPageRequest {

    /**
     * 역할
     */
    private UserRole role;

    /**
     * 상태
     */
    private UserStatus status;

    /**
     * 제공자
     */
    private OAuth2Provider provider;

    /**
     * 검색 시작 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /**
     * 검색 종료 일자
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 검색어
     */
    private String text;

}
