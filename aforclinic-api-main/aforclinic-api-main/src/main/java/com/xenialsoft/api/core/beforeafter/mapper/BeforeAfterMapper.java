package com.xenialsoft.api.core.beforeafter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfter;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterPageRequest;

public interface BeforeAfterMapper {

    public List<BeforeAfter> selectList(@Param("search") BeforeAfterPageRequest search,
            @Param("paging") ApiPageRequest paging);

    public long selectTotalCount(@Param("search") BeforeAfterPageRequest search);

    public BeforeAfter selectById(@Param("id") String id);

    public int insert(BeforeAfter beforeafter);

    public int update(BeforeAfter beforeafter);
    
    public int updateDelete(BeforeAfter beforeafter);

}