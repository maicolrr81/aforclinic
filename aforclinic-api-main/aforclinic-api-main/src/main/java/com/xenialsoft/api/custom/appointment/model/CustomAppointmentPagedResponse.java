package com.xenialsoft.api.custom.appointment.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.core.appointment.model.AppointmentPagedResponse;
import com.xenialsoft.api.core.appointment.model.AppointmentStatus;
import com.xenialsoft.api.core.appointment.model.AppointmentType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomAppointmentPagedResponse {

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
     * 날짜 변경 또는 취소 가능 여부
     */
    private boolean editable;

    /**
     * 취소 여부
     */
    private boolean cancel;

    /**
     * 과거 예약 여부
     */
    private boolean past;

    /**
     * 데이터 변환
     * 
     * @param appointment
     * @return
     */
    public static CustomAppointmentPagedResponse from(AppointmentPagedResponse appointment) {
        CustomAppointmentPagedResponse data = new CustomAppointmentPagedResponse();
        data.id = appointment.getId();
        data.type = appointment.getType();
        data.content = appointment.getContent();
        data.date = appointment.getDate();

        // 상태에 따른 변경
        if(appointment.getStatus() == null) {
        	data.editable = true;
        } else {
        	AppointmentStatus status = appointment.getStatus();
            switch (status) {
            case PENDING: // 접수 단계에서는 날짜 변경이나 취소가 가능
                data.editable = true;
                break;
            case CANCEL: // 취소 단계에서는 날짜 변경이나 취소 불가능
                data.cancel = true;
            default: // 그 외 단계에서는 날짜 변경이나 취소 불가능
                data.editable = false;
            }
        }        

        // 날짜가 지났는지
        if (data.date != null && data.date.isBefore(LocalDateTime.now())) {
            data.past = true;
        }

        return data;
    }

}
