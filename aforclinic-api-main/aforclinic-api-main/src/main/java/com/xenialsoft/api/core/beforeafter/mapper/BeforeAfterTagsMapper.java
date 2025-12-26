package com.xenialsoft.api.core.beforeafter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.beforeafter.model.BeforeAfterTags;

public interface BeforeAfterTagsMapper {

    public List<String> selectListById(@Param("id") String id);

    public long selectTotalCount(@Param("id") String id);

    public List<String> selectTagList();

    public int insert(BeforeAfterTags beforeAfterTags);

    public int delete(@Param("id") String id);

}