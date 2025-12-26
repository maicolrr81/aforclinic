package com.xenialsoft.api.common.data;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

/**
 * API 페이징 응답을 표준화하기 위한 DTO 클래스입니다.
 * <p>
 * 이 클래스는 {@link ApiResponse}를 확장하여, 페이징된 응답에 대해 전체 개수를 나타내는 {@code totalCount}
 * 필드를 추가로 포함합니다. 클라이언트에 페이징된 데이터를 응답할 때, 데이터와 함께 전체 결과의 개수를 전달할 수 있습니다.
 * </p>
 * <p>
 * 제공되는 정적 팩토리 메서드를 사용하여, 응답 데이터, 전체 개수 및 (선택적으로) 응답 메시지를 포함한
 * {@link ResponseEntity} 객체를 손쉽게 생성할 수 있습니다.
 * </p>
 * 
 * @author xenialsoft
 * @since 2025.02.07
 */
public class ApiPagedResponse {

    /**
     * 페이지네이션이 되지 않은 데이터를 반환하는 메서드
     * 
     * @param content 데이터 리스트
     * @return ResponseEntity
     */
    public static ResponseEntity<?> ok(List<?> content) {
        return ApiResponse.ok(new PageImpl<>(content));
    }

    /**
     * 페이지네이션된 빈 데이터를 반환하는 메서드
     * 
     * @param page 페이지네이션 정보
     * @return ResponseEntity
     */
    public static ResponseEntity<?> ok(ApiPageRequest page) {
        return ApiResponse.ok(new PageImpl<>(Collections.emptyList(), page.toPageable(), 0));
    }

    /**
     * 페이지네이션된 데이터를 반환하는 메서드 (totalElements와 Pageable을 포함)
     * 
     * @param content 데이터 리스트
     * @param total   전체 요소 수
     * @param page    페이지네이션 정보
     * @return ResponseEntity
     */
    public static ResponseEntity<?> ok(List<?> content, long total, ApiPageRequest page) {
        if (page.isUnpaged()) {
            page.setPage(1);
            page.setSize((int) total);
        }
        return ApiResponse.ok(new PageImpl<>(content, page.toPageable(), total));
    }

}
