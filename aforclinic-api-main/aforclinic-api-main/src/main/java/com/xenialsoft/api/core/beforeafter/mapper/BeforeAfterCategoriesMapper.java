package com.xenialsoft.api.core.beforeafter.mapper;

import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.beforeafter.model.BeforeAfterCategories;

public interface BeforeAfterCategoriesMapper {

    public int insert(BeforeAfterCategories beforeAfterCategories);

    public int delete(@Param("id") String id);

}