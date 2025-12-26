package com.xenialsoft.api.core.appointment.model;

import com.xenialsoft.api.common.Status;

public enum AppointmentStatus implements Status {

    /**
     * 접수
     */
    PENDING,

    /**
     * 예약 성공
     */
    CONFIRMED,

    /**
     * 재연락
     */
    FOLLOW_UP,

    /**
     * 부재1
     */
    NO_ANSWER_1,

    /**
     * 부재2
     */
    NO_ANSWER_2,

    /**
     * 부재3
     */
    NO_ANSWER_3,

    /**
     * 부재4
     */
    NO_ANSWER_4,

    /**
     * 부재>카톡1
     */
    NO_ANSWER_KAKAO_1,

    /**
     * 부재>카톡2
     */
    NO_ANSWER_KAKAO_2,

    /**
     * 거절
     */
    REJECTED,

    /**
     * 중복
     */
    DUPLICATED,

    /**
     * 결번
     */
    INVALID_NUMBER,

    /**
     * 마감
     */
    CLOSED,

    /**
     * 취소
     */
    CANCEL,

    /**
     * 삭제
     */
    DELETED;

}
