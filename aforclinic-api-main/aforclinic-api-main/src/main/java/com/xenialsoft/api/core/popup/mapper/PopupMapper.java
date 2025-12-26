package com.xenialsoft.api.core.popup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.popup.model.Popup;
import com.xenialsoft.api.core.popup.model.PopupPageRequest;

@Mapper
public interface PopupMapper {

    /**
     * 개수 조회
     * 
     * @param search
     * @return
     */
    public long selectTotalCount(@Param("search") PopupPageRequest search);

    /**
     * 조회
     * 
     * @param search
     * @param paging
     * @return
     */
    public List<Popup> selectList(@Param("search") PopupPageRequest search, @Param("paging") ApiPageRequest paging);

    /**
     * id로 조회
     * 
     * @param id
     * @return
     */
    public Popup selectById(@Param("id") String id);

    /**
     * 추가
     * 
     * @param
     * @return
     */
    public int insert(Popup popup);

    /**
     * 수정
     * 
     * @param popup
     * @return
     */
    public int update(Popup popup);
    
    /**
     * 화면표시순서 변경
     * @param popup
     * @return
     */
    public int reorder(Popup popup);

}
