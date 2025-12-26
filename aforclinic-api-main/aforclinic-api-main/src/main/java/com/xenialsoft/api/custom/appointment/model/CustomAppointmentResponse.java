package com.xenialsoft.api.custom.appointment.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.core.appointment.model.AppointmentResponse;
import com.xenialsoft.api.core.appointment.model.AppointmentStatus;
import com.xenialsoft.api.core.appointment.model.AppointmentType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomAppointmentResponse {

    /**
     * 예약 식별자
     */
    private String id;

    /**
     * 예약 타입
     */
    private AppointmentType type;

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
     * 예약 상태
     */
    private AppointmentStatus status;

    /**
     * 데이터 변환
     * 
     * @param appointment
     * @return
     */
    public static CustomAppointmentResponse from(AppointmentResponse appointment) {
        CustomAppointmentResponse data = new CustomAppointmentResponse();
        data.id = appointment.getId();
        data.type = appointment.getType();
        data.content = appointment.getContent();
        data.date = appointment.getDate();
        data.status = appointment.getStatus();
        return data;
    }

}
