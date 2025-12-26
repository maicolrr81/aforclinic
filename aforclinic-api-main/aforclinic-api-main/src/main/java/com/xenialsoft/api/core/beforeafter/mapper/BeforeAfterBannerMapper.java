package com.xenialsoft.api.core.beforeafter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBanner;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterBannerPageRequest;

public interface BeforeAfterBannerMapper {

    public List<BeforeAfterBanner> selectList(@Param("search") BeforeAfterBannerPageRequest search,
            @Param("paging") ApiPageRequest paging);

    public long selectTotalCount(@Param("search") BeforeAfterBannerPageRequest search);

    public BeforeAfterBanner selectById(@Param("id") String id);

    public int insert(BeforeAfterBanner beforeAfterBanner);

    public int update(BeforeAfterBanner beforeAfterBanner);

    public int updateDelete(BeforeAfterBanner beforeAfterBanner);
}

