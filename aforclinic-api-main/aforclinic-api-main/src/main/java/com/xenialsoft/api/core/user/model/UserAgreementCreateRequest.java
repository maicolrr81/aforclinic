package com.xenialsoft.api.core.user.model;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgreementCreateRequest {

    /**
     * 사용자 식별자
     */
    @Setter
    private String userId;

    /**
     * 동의 내역
     */
    @NotNull
    private UserAgreementType type;

    /**
     * 동의 여부
     */
    private boolean agreed;

    /**
     * 데이터 변환
     * 
     * @param userId
     * @param type
     * @return
     */
    public static UserAgreement from(UserAgreementCreateRequest data) {
        UserAgreement agreement = new UserAgreement();
        agreement.setUserId(data.getUserId());
        agreement.setType(data.getType());
        agreement.setAgreed(data.isAgreed());
        return agreement;
    }

}
