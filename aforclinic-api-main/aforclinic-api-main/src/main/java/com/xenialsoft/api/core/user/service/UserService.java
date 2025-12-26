package com.xenialsoft.api.core.user.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.util.IdUtils;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.config.properties.OAuth2Properties;
import com.xenialsoft.api.core.user.mapper.UserMapper;
import com.xenialsoft.api.core.user.model.User;
import com.xenialsoft.api.core.user.model.UserAgreementCreateRequest;
import com.xenialsoft.api.core.user.model.UserCreateRequest;
import com.xenialsoft.api.core.user.model.UserPageRequest;
import com.xenialsoft.api.core.user.model.UserPagedResponse;
import com.xenialsoft.api.core.user.model.UserResponse;
import com.xenialsoft.api.core.user.model.UserStatus;
import com.xenialsoft.api.core.user.model.UserUpdateRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;

    private final UserAgreementService agreementService;

    private final UserMapper mapper;

    private final OAuth2Properties oauth2Properties;

    @Transactional(readOnly = true)
    public List<UserPagedResponse> getUserList(UserPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(UserPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public long getTotalCount(UserPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserById(String id) {
        return Optional.ofNullable(mapper.selectById(id)).map(UserResponse::from);
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserByUsername(String username) {
        return Optional.ofNullable(mapper.selectByUsername(username)).map(UserResponse::from);
    }

    @Auditable
    @Transactional
    public UserResponse createUser(UserCreateRequest data) {
        if (StringUtils.isNotBlank(data.getPassword())) {
            data.setPassword(encoder.encode(data.getPassword()));
        }

        User user = UserCreateRequest.from(data);

        int count = mapper.insert(user);
        if (count != 1) {
            throw new ServiceException("사용자 등록 중 오류가 발생했습니다.");
        }

        // 동의내역 저장
        List<UserAgreementCreateRequest> agreements = data.getAgreements();
        if (agreements != null && !agreements.isEmpty()) {
            for (UserAgreementCreateRequest agreement : agreements) {
                agreement.setUserId(user.getId());
                agreementService.createUserAgreement(agreement);
            }
        }

        return UserResponse.from(mapper.selectById(user.getId()));
    }

    @Auditable
    @Transactional
    public void updateUser(UserUpdateRequest data) {
        if (StringUtils.isNotBlank(data.getPassword())) {
            data.setPassword(encoder.encode(data.getPassword()));
        }

        if (UserStatus.DELETED == data.getStatus()) {
            User user = mapper.selectById(data.getId());
            if (user != null && user.getProvider() != null) {

                WebClient client = WebClient.builder().build();

                switch (user.getProvider()) {
                case KAKAO:
                    // @formatter:off
                    client.post()
                    .uri("https://kapi.kakao.com/v1/user/unlink")
                    .header("Authorization", "KakaoAK " + oauth2Properties.getKakaoAdminKey())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue("target_id_type=user_id&target_id=" + user.getProviderId())
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnNext(response -> log.info("#### 카카오 연결 끊기 성공: {}", response))
                    .doOnError(error -> log.error("#### 카카오 연결 끊기 실패: {}", error))
                    .block();
                    // @formatter:on
                    break;
                }
            }
            if(user != null) {
                //사용자 정보 초기화로 업데이트
                User delUser = new User();
                delUser.setId(data.getId());
                delUser.setUsername(IdUtils.generate());
                delUser.setModifiedBy(data.getModifiedBy());
                if(mapper.deletedUser(delUser) != 1) {
                    throw new ServiceException("사용자 삭제 중 오류가 발생하였습니다.");
                }
            }
        } else {
            User user = UserUpdateRequest.from(data);
    
            int count = mapper.update(user);
            if (count != 1) {
                throw new ServiceException("사용자 수정 중 오류가 발생하였습니다.");
            }
        }
    }

}
