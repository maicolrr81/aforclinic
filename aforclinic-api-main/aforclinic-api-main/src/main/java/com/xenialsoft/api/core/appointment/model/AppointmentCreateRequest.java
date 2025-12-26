package com.xenialsoft.api.core.appointment.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentCreateRequest {

    /**
     * 예약 타입
     */
    @NotNull
    private AppointmentType type;

    /**
     * 사용자 식별자
     */
    @Setter
    @JsonIgnore
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
    @NotBlank
    private String content;

    /**
     * 예약일
     */
    @Nullable
    @FutureOrPresent
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
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Appointment from(AppointmentCreateRequest data) {
        Appointment appointment = new Appointment();
        appointment.setId(IdUtils.generate());
        appointment.setType(data.getType());
        appointment.setUserId(data.getUserId());
        appointment.setNickname(data.getNickname());
        appointment.setContact(data.getContact());
        appointment.setContent(data.getContent());
        appointment.setDate(data.getDate());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPrivacyAgreed(data.isPrivacyAgreed());
        appointment.setAgeConfirmed(data.isAgeConfirmed());
        appointment.setMarketingAgreed(data.isMarketingAgreed());
        return appointment;
    }

}
