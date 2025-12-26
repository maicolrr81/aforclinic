package com.xenialsoft.api.core.appointment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentPageRequest {

    /**
     * 예약 타입
     */
    private AppointmentType type;

    /**
     * 예약 상태
     */
    private AppointmentStatus status;

    /**
     * 예약자 식별자
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
     * 정렬기준
     */
    private AppointmentSort sort;

}
