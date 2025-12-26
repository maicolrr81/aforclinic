package com.xenialsoft.api.core.settings.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.settings.model.SiteSetting;

@Mapper
public interface SiteSettingMapper {

    public List<SiteSetting> selectList();

    public SiteSetting selectByKey(@Param("key") String key);

    public int insert(SiteSetting setting);

    public int update(SiteSetting setting);

}
