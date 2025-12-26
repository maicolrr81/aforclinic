package com.xenialsoft.api.core.cart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.cart.model.Cart;

@Mapper
public interface CartMapper {

    public List<Cart> selectListById(@Param("id") String id);

    public int insert(Cart cart);

    public int delete(@Param("userId") String userId, @Param("productId") String productId);

}
