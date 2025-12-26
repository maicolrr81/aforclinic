package com.xenialsoft.api.core.product.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.product.model.ProductBundle;

@Mapper
public interface ProductBundleMapper {

	/**
	 * id로 조회
	 * @param id
	 * @return
	 */
	public List<ProductBundle> selectListById(@Param("id") String id);
	
	/**
	 * 특정 productId가 있는 bundleId의 product Category
	 * @param id
	 * @return
	 */
	public List<ProductBundle> selectBundleCategoryListByProductId(@Param("id") String id);
	
	/**
	 * 추가
	 * @param productBundle
	 * @return
	 */
	public int insert(ProductBundle productBundle);
		
	/**
	 * 삭제
	 * @param id
	 * @return
	 */
	public int delete(@Param("id") String id);

}
