package com.xenialsoft.api.core.user.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAgreement extends Auditable {

    /**
     * 동의 내역 식별자
     */
    private Long id;

    /**
     * 사용자 식별자
     */
    private String userId;

    /**
     * 동의 내역
     */
    private UserAgreementType type;

    /**
     * 동의 여부
     */
    private boolean agreed;

}
