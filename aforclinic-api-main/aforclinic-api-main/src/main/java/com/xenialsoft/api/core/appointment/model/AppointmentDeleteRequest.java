package com.xenialsoft.api.core.appointment.model;

import org.springframework.data.annotation.LastModifiedBy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentDeleteRequest {

    /**
     * 식별자
     */
    private String id;

    /**
     * 상태
     */
    @Setter
    private AppointmentStatus status;

    @LastModifiedBy
    private String modifiedBy;

    public static AppointmentDeleteRequest of(String id) {
        AppointmentDeleteRequest request = new AppointmentDeleteRequest();
        request.id = id;
        return request;
    }

}
