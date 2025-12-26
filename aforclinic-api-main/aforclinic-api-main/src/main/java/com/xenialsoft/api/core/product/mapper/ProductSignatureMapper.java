package com.xenialsoft.api.core.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xenialsoft.api.core.product.model.ProductSignature;

@Mapper
public interface ProductSignatureMapper {

    /**
     * 조회
     * 
     * @return
     */
    public List<ProductSignature> selectList();

    /**
     * 추가
     * 
     * @param id
     * @return
     */
    public int insert(String id);

    /**
     * 삭제
     * 
     * @param id
     * @return
     */
    public int delete();

}
