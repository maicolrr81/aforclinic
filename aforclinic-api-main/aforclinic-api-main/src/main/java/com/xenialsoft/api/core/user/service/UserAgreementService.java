package com.xenialsoft.api.core.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.user.mapper.UserAgreementMapper;
import com.xenialsoft.api.core.user.model.UserAgreement;
import com.xenialsoft.api.core.user.model.UserAgreementCreateRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAgreementService {

    private final UserAgreementMapper mapper;

    @Transactional
    public void createUserAgreement(UserAgreementCreateRequest data) {

        UserAgreement agreement = UserAgreementCreateRequest.from(data);

        int count = mapper.insert(agreement);
        if (count != 1) {
            throw new ServiceException("동의 내역 저장 중 오류가 발생했습니다.");
        }
    }

}
