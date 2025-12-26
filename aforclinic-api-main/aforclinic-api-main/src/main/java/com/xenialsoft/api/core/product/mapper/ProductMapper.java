package com.xenialsoft.api.core.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.product.model.Product;
import com.xenialsoft.api.core.product.model.ProductPageRequest;

@Mapper
public interface ProductMapper {

    /**
     * 개수 조회
     * 
     * @param search
     * @return
     */
    public long selectTotalCount(@Param("search") ProductPageRequest search);

    /**
     * 조회
     * 
     * @param search
     * @param paging
     * @return
     */
    public List<Product> selectList(@Param("search") ProductPageRequest search, 
            @Param("paging") ApiPageRequest paging);

    /**
     * id로 조회
     * 
     * @param id
     * @return
     */
    public Product selectById(@Param("id") String id);

    /**
     * 이름 중복 체크
     * 
     * @param name
     * @return
     */
    public int selectByName(@Param("name") String name, @Param("id") String id);

    /**
     * 패키지에 등록할 상품 목록 조회
     * 
     * @param categoryId
     * @param searchText
     * @return
     */
    public List<Product> selectBundleProduct(@Param("categoryId") String categoryId,
            @Param("searchText") String searchText);

    
    /**
     * 상품을 idList로 조회
     * @param idList
     * @return
     */
    public List<Product> selectByIdList(@Param("idList") List<String> idList);


    /**
     * 이벤트에 등록할 상품 목록
     * @param categoryId
     * @param searchText
     * @return
     */
    public List<Product> selectEventProduct(@Param("categoryId") String categoryId, @Param("searchText") String searchText);

    
    /**
     * 추가
     * 
     * @param product
     * @return
     */
    public int insert(Product product);

    /**
     * 수정
     * 
     * @param product
     * @return
     */
    public int update(Product product);

    /**
     * 상품이 번들에 포함된 개수
     * 
     * @param id
     * @return
     */
    public int hasBundle(@Param("id") String id);

    /**
     * 상품이 이벤트에 포함된 개수
     * 
     * @param id
     * @return
     */
    public int hasEvent(@Param("id") String id);

    /**
     * 상품이 장바구니에 포함된 개수
     * 
     * @param id
     * @return
     */
    public int hasCart(@Param("id") String id);

    /**
     * 카트에 담긴 상품 삭제
     * 
     * @param id
     * @return
     */
    public int deleteCart(@Param("id") String id);

    /**
     * 상태 업데이트
     * 
     * @param product
     * @return
     */
    public int updateStatus(Product product);

}
