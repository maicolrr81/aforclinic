package com.xenialsoft.api.core.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.product.model.Category;
import com.xenialsoft.api.core.product.model.CategoryPageRequest;

@Mapper
public interface CategoryMapper {

    /**
     * 개수 조회
     * 
     * @param search
     * @param isadmin
     * @return
     */
    public long selectTotalCount(@Param("search") CategoryPageRequest search);

    /**
     * 조회
     * 
     * @param search
     * @param isadmin
     * @param paging
     * @return
     */
    public List<Category> selectList(@Param("search") CategoryPageRequest search,
            @Param("paging") ApiPageRequest paging);

    /**
     * id로 조회
     * 
     * @param id
     * @return
     */
    public Category selectById(@Param("id") String id);

    /**
     * 이름 중복 체크
     * 
     * @param name
     * @return
     */
    public int selectByName(@Param("name") String name);

    /**
     * 추가
     * 
     * @param category
     * @return
     */
    public int insert(Category category);

    /**
     * 수정
     * 
     * @param category
     * @return
     */
    public int update(Category category);

    /**
     * 수정
     * 
     * @param category
     * @return
     */
    public int reorder(Category category);

    /**
     * 유효한 상품 개수
     * 
     * @param id
     * @return
     */
    public int hasProduct(@Param("id") String id);

    /**
     * 유효한 전후사진 개수
     * 
     * @param id
     * @return
     */
    public int hasBeforeAfter(@Param("id") String id);

    /**
     * 상태 업데이트
     * 
     * @param category
     * @return
     */
    public int updateStatus(Category category);

}
