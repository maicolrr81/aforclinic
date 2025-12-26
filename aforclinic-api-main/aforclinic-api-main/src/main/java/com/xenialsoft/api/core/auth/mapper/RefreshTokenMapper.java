package com.xenialsoft.api.core.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.auth.model.RefreshToken;

@Mapper
public interface RefreshTokenMapper {

    /**
     * 리프레시 토큰 조회
     * 
     * @param id
     * @return
     */
    public RefreshToken findById(@Param("id") String id);

    /**
     * 리프레시 토큰 저장
     * 
     * @param refreshToken
     * @return
     */
    public int insert(RefreshToken refreshToken);

    /**
     * 리프레시 토큰 수정
     * 
     * @param refreshToken
     * @return
     */
    public int update(RefreshToken refreshToken);

    /**
     * 리프레시 토큰 삭제
     * 
     * @param id
     * @return
     */
    public int delete(@Param("id") String id);

}
