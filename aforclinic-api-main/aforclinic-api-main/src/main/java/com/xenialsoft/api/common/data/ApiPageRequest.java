package com.xenialsoft.api.common.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiPageRequest {

    /**
     * 페이지 번호를 지정합니다.<br>
     * 1 기반 인덱스이며, 1보다 작을 수 없습니다. 기본값은 1입니다.
     */
    private int page = 1;

    /**
     * 페이지 크기를 지정합니다.<br>
     * 한 페이지에 포함될 항목 수를 나타냅니다. 기본값은 10입니다.
     */
    private int size = 10;

    /**
     * 페이지를 설정하지 않습니다.
     */
    private boolean unpaged;

    /**
     * 페이지당 항목 수를 지정합니다.
     * 
     * @return
     */
    public int getLimit() {
        return Math.max(1, size);
    }

    /**
     * 페이지의 오프셋을 지정합니다.
     * 
     * @return
     */
    public int getOffset() {
        return (page - 1) * getLimit();
    }

    /**
     * 페이지의 순번 시작을 반환합니다.
     * 
     * @param totalCount
     * @return
     */
    public long getStartSequence(long totalCount) {
        return totalCount - getOffset();
    }

    /**
     * ApiPageRequest 객체를 Spring Data의 Pageable 객체로 변환합니다.
     * 
     * @return {@link Pageable} 객체
     */
    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }

    /**
     * 페이지를 설정하지 않습니다.
     * 
     * @return
     */
    public static ApiPageRequest unpaged() {
        ApiPageRequest paging = new ApiPageRequest();
        paging.setUnpaged(true);
        return paging;
    }

}
