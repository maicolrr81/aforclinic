package com.xenialsoft.api.core.event.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.event.model.Event;
import com.xenialsoft.api.core.event.model.EventPageRequest;

@Mapper
public interface EventMapper {

    /**
     * 이벤트 개수 조회
     * 
     * @param search
     * @return
     */
    public long selectTotalCount(@Param("search") EventPageRequest search);

    /**
     * 이벤트 목록 조회
     * 
     * @param search
     * @param paging
     * @return
     */
    public List<Event> selectList(@Param("search") EventPageRequest search, @Param("paging") ApiPageRequest paging);

    /**
     * 이벤트 조회
     * 
     * @param id
     * @return
     */
    public Event selectById(@Param("id") String id);

    /**
     * 이벤트 등록
     * 
     * @param event
     * @return
     */
    public int insert(Event event);

    /**
     * 이벤트 수정
     * 
     * @param event
     * @return
     */
    public int update(Event event);
    
}
