package com.xenialsoft.api.core.mainbanner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.mainbanner.model.MainBanner;
import com.xenialsoft.api.core.mainbanner.model.MainBannerPageRequest;

public interface MainBannerMapper {

	public List<MainBanner> selectList(@Param("search") MainBannerPageRequest search,
			@Param("paging") ApiPageRequest paging);

	public long selectTotalCount(@Param("search") MainBannerPageRequest search);

	public MainBanner selectById(@Param("id") String id);

	public int insert(MainBanner mainBanner);

	public int update(MainBanner mainBanner);
	
	public int reorder(MainBanner mainBanner);

	public int updateDelete(MainBanner mainBanner);

}