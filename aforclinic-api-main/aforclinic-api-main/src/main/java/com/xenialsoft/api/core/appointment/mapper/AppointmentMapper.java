package com.xenialsoft.api.core.appointment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.core.appointment.model.Appointment;
import com.xenialsoft.api.core.appointment.model.AppointmentPageRequest;

@Mapper
public interface AppointmentMapper {

    public List<Appointment> selectList(@Param("search") AppointmentPageRequest search,
            @Param("paging") ApiPageRequest paging);

    public long selectTotalCount(@Param("search") AppointmentPageRequest search);

    public Appointment selectById(@Param("id") String id);

    public int insert(Appointment appointment);

    public int update(Appointment appointment);

}