package com.xenialsoft.api.custom.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.custom.product.model.CustomProduct;
import com.xenialsoft.api.custom.product.model.CustomProductPageRequest;

@Mapper
public interface CustomProductMapper {

    public List<CustomProduct> selectList(@Param("search") CustomProductPageRequest request);

}
