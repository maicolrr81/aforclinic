package com.xenialsoft.api.core.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.xenialsoft.api.core.user.model.UserAgreement;

@Mapper
public interface UserAgreementMapper {

    public int insert(UserAgreement agreement);
    
}
