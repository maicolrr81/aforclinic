package com.xenialsoft.api.core.appointment.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.core.event.model.EventResponse;
import com.xenialsoft.api.core.user.model.UserResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentResponse {

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
     * 예약일시
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
     * 이벤트
     */
    private EventResponse event;

    /**
     * 예약자
     */
    private UserResponse user;

    /**
     * 데이터 변환
     * 
     * @param appointment
     * @return
     */
    public static AppointmentResponse from(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        AppointmentResponse data = new AppointmentResponse();
        data.id = appointment.getId();
        data.type = appointment.getType();
        data.nickname = appointment.getNickname();
        data.contact = appointment.getContact();
        data.content = appointment.getContent();
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
