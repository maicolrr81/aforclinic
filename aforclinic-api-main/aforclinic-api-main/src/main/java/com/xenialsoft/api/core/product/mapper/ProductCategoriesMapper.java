package com.xenialsoft.api.core.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.product.model.ProductCategories;

@Mapper
public interface ProductCategoriesMapper {

    /**
     * id로 조회
     * 
     * @param id
     * @return
     */
    public List<ProductCategories> selectListById(@Param("id") String id);
    
    /**
     * 상품 id로 카테고리 조회
     * @param idList
     * @return
     */
    public List<ProductCategories> selectByProductId(@Param("idList") List<String> idList);

    /**
     * 추가
     * 
     * @param productCategories
     * @return
     */
    public int insert(ProductCategories productCategories);

    /**
     * 순서 변경
     * @param productCategories
     * @return
     */
    public int reorder(ProductCategories productCategories);

    
    /**
     * 키로 삭제
     * @param productId
     * @param categoryId
     * @return
     */
    public int deleteProductCategories(@Param("productId") String productId, @Param("categoryId") String categoryId);
    
    /**
     * 삭제
     * 
     * @param id
     * @return
     */
    public int delete(@Param("id") String id);

}
