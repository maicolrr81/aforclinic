package com.xenialsoft.api.core.appointment.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentUpdateRequest {

    /**
     * 예약 식별자
     */
    @Setter
    @JsonIgnore
    private String id;

    /**
     * 예약 상태
     */
    private AppointmentStatus status;

    /**
     * 예약 일자
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    /**
     * 메모
     */
    private String memo;

    /**
     * 수정자
     */
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;

    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static Appointment from(AppointmentUpdateRequest data) {
        Appointment appointment = new Appointment();
        appointment.setId(data.getId());
        appointment.setStatus(data.getStatus());
        appointment.setDate(data.getDate());
        appointment.setMemo(data.getMemo());
        appointment.setModifiedBy(data.getModifiedBy());
        return appointment;
    }

}
