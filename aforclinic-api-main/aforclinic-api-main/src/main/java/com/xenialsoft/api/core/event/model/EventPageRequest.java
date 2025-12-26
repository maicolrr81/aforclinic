package com.xenialsoft.api.core.event.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventPageRequest {

    /**
     * 진행 상태
     */
    private EventProgressStatus progressType;

    /**
     * 이벤트 상태
     */
    private EventStatus status;
    
    /**
     * 이벤트 식별자 목록
     */
    private List<String> eventIds;

    /**
     * 검색어
     */
    private String text;

}