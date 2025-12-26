package com.xenialsoft.api.core.appointment.model;

import java.time.LocalDateTime;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Appointment extends Auditable {

    /**
     * 예약 식별자
     */
    private String id;

    /**
     * 예약 유형
     */
    private AppointmentType type;

    /**
     * 사용자 식별자
     */
    private String userId;

    /**
     * 예약자명
     */
    private String nickname;

    /**
     * 예약자 연락처
     */
    private String contact;

    /**
     * 예약 내용
     */
    private String content;

    /**
     * 예약 일자
     */
    private LocalDateTime date;

    /**
     * 메모
     */
    private String memo;

    /**
     * 예약 상태
     */
    private AppointmentStatus status;

    /**
     * 개인정보 수집 이용 동의 체크
     */
    private boolean privacyAgreed;

    /**
     * 만 14세 이상 여부
     */
    private boolean ageConfirmed;

    /**
     * 카카오톡 또는 문자 발송 동의
     */
    private boolean marketingAgreed;

}
