package com.xenialsoft.api.core.event.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.event.model.EventProduct;

@Mapper
public interface EventProductMapper {

    /**
     * 목록 조회
     * 
     * @param id
     * @return
     */
    public List<EventProduct> selectListById(@Param("id") String id);

    /**
     * 생성
     * 
     * @param product
     * @return
     */
    public int insert(EventProduct product);

    /**
     * 삭제
     * 
     * @param event
     * @return
     */
    public int delete(@Param("eventId") String eventId);

}
