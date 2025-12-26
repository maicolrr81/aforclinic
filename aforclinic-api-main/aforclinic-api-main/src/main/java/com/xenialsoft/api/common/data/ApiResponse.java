package com.xenialsoft.api.common.data;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @formatter:off
/**
 * API 응답을 표준화하기 위한 헬퍼 클래스입니다.
 * <p>
 * 이 클래스는 API 응답 구조를 통일하여, 응답 데이터와 메시지를 포함하는
 * {@link ResponseEntity} 객체를 쉽게 생성할 수 있도록 도와줍니다.
 * </p>
 * <p>
 * 주요 메서드:
 * <ul>
 *   <li>{@link #ok()} - 데이터 없이 성공 응답을 생성합니다.</li>
 *   <li>{@link #ok(Object)} - 데이터를 포함하는 성공 응답을 생성합니다.</li>
 *   <li>{@link #ok(Object, String)} - 데이터를 포함하고 메시지를 지정하는 성공 응답을 생성합니다.</li>
 * </ul>
 * </p>
 * 
 * @author xenialsoft
 * @since  2025.02.07
 */
// @formatter:on
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse {

    /**
     * 응답 데이터는 이 필드에 포함됩니다.
     */
    private Object data;

    /**
     * 응답 코드를 설정합니다.
     */
    private String code;

    /**
     * 응답 메시지를 설정합니다. 보통 오류에 대한 메시지를 전달합니다.
     */
    private String message;

    /**
     * 응답 시간을 설정합니다.
     */
    @Builder.Default
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss.SSS")
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * 데이터 없이 성공 응답 엔티티를 생성합니다.
     * 
     * @return 성공 응답 {@link ResponseEntity} 객체
     */
    public static ResponseEntity<?> ok() {
        return ok(null);
    }

    /**
     * 주어진 데이터를 포함하는 성공 응답 엔티티를 생성합니다.
     * 
     * @param data 응답에 포함할 데이터
     * @return 성공 응답 {@link ResponseEntity} 객체
     */
    public static ResponseEntity<?> ok(Object data) {
        return ok(data, HttpStatus.OK.getReasonPhrase(), "success");
    }

    /**
     * 주어진 데이터를 포함하는 성공 응답 엔티티를 생성합니다.
     * 
     * @param data    응답에 포함할 데이터
     * @param message 응답 메시지
     * @return 성공 응답 {@link ResponseEntity} 객체
     */
    public static ResponseEntity<?> ok(Object data, String message) {
        return ok(data, null, message);
    }

    /**
     * 주어진 데이터와 메시지를 포함하는 성공 응답 엔티티를 생성합니다.
     * 
     * @param data    응답에 포함할 데이터
     * @param code    응답 코드
     * @param message 응답 메시지
     * @return 성공 응답 {@link ResponseEntity} 객체
     */
    public static ResponseEntity<?> ok(Object data, String code, String message) {
        // @formatter:off
        return ResponseEntity.ok(ApiResponse.builder()
                .data(data)
                .code(code)
                .message(message)
                .build());
        // @formatter:on
    }

}
