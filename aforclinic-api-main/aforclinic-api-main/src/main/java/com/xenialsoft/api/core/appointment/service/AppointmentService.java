package com.xenialsoft.api.core.appointment.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.appointment.mapper.AppointmentMapper;
import com.xenialsoft.api.core.appointment.model.Appointment;
import com.xenialsoft.api.core.appointment.model.AppointmentCreateRequest;
import com.xenialsoft.api.core.appointment.model.AppointmentPageRequest;
import com.xenialsoft.api.core.appointment.model.AppointmentPagedResponse;
import com.xenialsoft.api.core.appointment.model.AppointmentResponse;
import com.xenialsoft.api.core.appointment.model.AppointmentUpdateRequest;
import com.xenialsoft.api.core.user.model.UserResponse;
import com.xenialsoft.api.core.user.service.UserService;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final UserService userService;

    private final AppointmentMapper mapper;

    /**
     * 목록 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @Transactional(readOnly = true)
    public List<AppointmentPagedResponse> getAppointmentList(AppointmentPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(AppointmentPagedResponse::from).toList();
    }

    /**
     * 목록 개수 조회
     * 
     * @param request
     * @param paging
     * @return
     */
    @Transactional(readOnly = true)
    public long getTotalCount(AppointmentPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    /**
     * 예약 조회
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<AppointmentResponse> getAppointmentById(String id) {
        return Optional.ofNullable(mapper.selectById(id)).map(AppointmentResponse::from);
    }

    /**
     * 사용자의 예약 정보 유효성 체크
     * 
     * @param id
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isAppointmentOwner(String id, String userId) {
        Appointment appointment = mapper.selectById(id);
        if (appointment == null) {
            return false;
        }
        return StringUtils.equals(appointment.getUserId(), userId);
    }

    /**
     * 예약 생성
     * 
     * @param data
     */
    @Auditable
    @Transactional
    public Appointment createAppointment(AppointmentCreateRequest data) {

        Appointment appointment = AppointmentCreateRequest.from(data);

        // 사용자 식별자가 있지만 예약자명이나 연락처가 없는 경우
        if (StringUtils.isNotBlank(appointment.getUserId())
                && StringUtils.isAnyBlank(appointment.getNickname(), appointment.getContact())) {
            Optional<UserResponse> client = userService.getUserById(appointment.getUserId());
            if (client.isPresent()) {
                // 예약자명이 없는 경우
                if (StringUtils.isBlank(appointment.getNickname())) {
                    appointment.setNickname(client.get().getNickname());
                }
                // 연락처가 없는 경우
                if (StringUtils.isBlank(appointment.getContact())) {
                    appointment.setContact(client.get().getPhoneNumber());
                }
            }
        }

        int count = mapper.insert(appointment);
        if (count != 1) {
            throw new ServiceException("예약 등록 중 오류가 발생했습니다.");
        }
        return appointment;
    }

    /**
     * 예약 수정
     * 
     * @param data
     */
    @Auditable
    @Transactional
    public void updateAppointment(AppointmentUpdateRequest data) {

        Appointment appointment = AppointmentUpdateRequest.from(data);

        int count = mapper.update(appointment);
        if (count != 1) {
            throw new ServiceException("예약 변경 중 오류가 발생했습니다.");
        }
    }

}
