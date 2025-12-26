package com.xenialsoft.api.custom.event.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.custom.event.model.CustomEvent;
import com.xenialsoft.api.custom.event.model.CustomEventPageRequest;

@Mapper
public interface CustomEventMapper {

    public List<CustomEvent> mainEventProductList();
    
    public List<CustomEvent> selectList(@Param("search") CustomEventPageRequest request,
            @Param("paging") ApiPageRequest paging);

    public CustomEvent selectById(@Param("id") String id);

}
