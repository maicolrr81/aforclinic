package com.xenialsoft.api.core.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.post.model.Landing;
import com.xenialsoft.api.core.post.model.LandingPageRequest;

@Mapper
public interface LandingMapper {

    public long selectTotalCount(@Param("search") LandingPageRequest request);

    public List<Landing> selectList(@Param("search") LandingPageRequest request,
            @Param("paging") ApiPageRequest paging);

    public Landing selectByPostId(@Param("postId") Long postId);

    public int insert(Landing landing);

    public int update(Landing landing);

}
