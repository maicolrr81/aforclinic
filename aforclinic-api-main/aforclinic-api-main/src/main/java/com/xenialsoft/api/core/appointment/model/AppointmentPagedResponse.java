package com.xenialsoft.api.core.appointment.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentPagedResponse implements Sequenceable {

    /**
     * 순번
     */
    @Setter
    private Long sequence;

    /**
     * 예약 식별자
     */
    private String id;

    /**
     * 예약 타입
     */
    private AppointmentType type;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    /**
     * 개인정보 동의
     */
    private boolean privacyAgreed;

    /**
     * 나이제한 동의
     */
    private boolean ageConfirmed;

    /**
     * 마케팅 동의
     */
    private boolean marketingAgreed;

    /**
     * 메모
     */
    private String memo;

    /**
     * 예약 상태
     */
    private AppointmentStatus status;

    /**
     * 등록일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    /**
     * 등록자
     */
    private String createdBy;

    /**
     * 수정일시
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    /**
     * 수정자
     */
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param appointment
     * @return
     */
    public static AppointmentPagedResponse from(Appointment appointment) {
        AppointmentPagedResponse data = new AppointmentPagedResponse();
        data.id = appointment.getId();
        data.type = appointment.getType();
        data.content = appointment.getContent();
        data.nickname = appointment.getNickname();
        data.contact = appointment.getContact();
        data.date = appointment.getDate();
        data.privacyAgreed = appointment.isPrivacyAgreed();
        data.ageConfirmed = appointment.isAgeConfirmed();
        data.marketingAgreed = appointment.isMarketingAgreed();
        data.memo = appointment.getMemo();
        data.status = appointment.getStatus();
        data.createdAt = appointment.getCreatedAt();
        data.createdBy = appointment.getCreatedBy();
        data.modifiedAt = appointment.getModifiedAt();
        data.modifiedBy = appointment.getModifiedBy();
        return data;
    }
}
