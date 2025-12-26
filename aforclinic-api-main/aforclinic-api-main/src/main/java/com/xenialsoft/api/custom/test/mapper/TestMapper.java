package com.xenialsoft.api.custom.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xenialsoft.api.custom.test.model.Test;

@Mapper
public interface TestMapper {

    public List<Test> selectListAll();

}
