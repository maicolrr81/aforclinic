package com.xenialsoft.api.core.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.post.model.Post;
import com.xenialsoft.api.core.post.model.PostPageRequest;

@Mapper
public interface PostMapper {

    /**
     * 게시글 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    public List<Post> selectList(@Param("search") PostPageRequest request, @Param("paging") ApiPageRequest paging);

    /**
     * 게시글 개수 조회
     * 
     * @param request
     * @return
     */
    public long selectTotalCount(@Param("search") PostPageRequest request);

    /**
     * id로 게시글 조회
     * 
     * @param id
     * @return
     */
    public Post selectById(@Param("id") Long id);

    /**
     * 게시글 등록
     * 
     * @param post
     * @return
     */
    public int insert(Post post);

    /**
     * 게시글 수정
     * 
     * @param post
     * @return
     */
    public int update(Post post);
    
    /**
     * 게시글 상태 삭제로 변경
     * 
     * @param post
     * @return
     */
    public int deleteUpdate(Post post);

    /**
     * 게시글 삭제
     * 
     * @param id
     * @return
     */
    public int delete(@Param("id") Long id);

}
