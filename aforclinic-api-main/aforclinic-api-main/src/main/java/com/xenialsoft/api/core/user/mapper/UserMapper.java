package com.xenialsoft.api.core.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.user.model.User;
import com.xenialsoft.api.core.user.model.UserPageRequest;

@Mapper
public interface UserMapper {

    /**
     * 사용자 목록 조회
     * 
     * @param search
     * @param paging
     * @return
     */
    public List<User> selectList(@Param("search") UserPageRequest search, @Param("paging") ApiPageRequest paging);

    /**
     * 사용자 수 조회
     * 
     * @param search
     * @return
     */
    public long selectTotalCount(@Param("search") UserPageRequest search);

    /**
     * id로 사용자 정보 조회
     * 
     * @param id
     * @return
     */
    public User selectById(@Param("id") String id);

    /**
     * username으로 사용자 정보 조회
     * 
     * @param username
     * @return
     */
    public User selectByUsername(@Param("username") String username);

    /**
     * 사용자 추가
     * 
     * @param user
     * @return
     */
    public int insert(User user);

    /**
     * 사용자 수정
     * 
     * @param user
     * @return
     */
    public int update(User user);
    
    /**
     * 사용자 삭제
     * @param user
     * @return
     */
    public int deletedUser(User user);
}
