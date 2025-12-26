package com.xenialsoft.api.custom.appointment.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.core.appointment.model.AppointmentCreateRequest;
import com.xenialsoft.api.core.appointment.model.AppointmentPageRequest;
import com.xenialsoft.api.core.appointment.model.AppointmentSort;
import com.xenialsoft.api.core.appointment.model.AppointmentType;
import com.xenialsoft.api.core.appointment.model.AppointmentUpdateRequest;
import com.xenialsoft.api.core.appointment.service.AppointmentService;
import com.xenialsoft.api.core.auth.model.CustomUserDetails;
import com.xenialsoft.api.custom.appointment.model.CustomAppointmentPagedResponse;
import com.xenialsoft.api.custom.appointment.model.CustomAppointmentResponse;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/appointments")
public class CustomAppointmentController {

    private final AppointmentService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMyAppointmentList(@AuthenticationPrincipal CustomUserDetails principal) {

        AppointmentPageRequest request = new AppointmentPageRequest();
        request.setUserId(principal.getId());
        request.setType(AppointmentType.PROCEDURE);
        request.setSort(AppointmentSort.APPOINTMENT_DATE_DESC);

        ApiPageRequest paging = ApiPageRequest.unpaged();

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<CustomAppointmentPagedResponse> content = service.getAppointmentList(request, paging).stream()
                .map(CustomAppointmentPagedResponse::from).toList();

        return ApiPagedResponse.ok(content);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @appointmentService.isAppointmentOwner(#id, principal.id)")
    public ResponseEntity<?> getMyAppointment(@PathVariable String id) {

        CustomAppointmentResponse data = service.getAppointmentById(id).map(CustomAppointmentResponse::from)
                .orElseThrow(() -> new NotFoundException("예약 정보를 찾을 수 없습니다."));

        return ApiResponse.ok(data);
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody @Valid AppointmentCreateRequest request) {

        // 로그인한 사용자는 사용자 식별자를 설정
        if (principal != null) {
            request.setUserId(principal.getId());
        }

        service.createAppointment(request);

        return ApiResponse.ok();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated() and @appointmentService.isAppointmentOwner(#id, principal.id)")
    public ResponseEntity<?> partialUpdateAppointment(@PathVariable String id,
            @RequestBody AppointmentUpdateRequest request) {

        // id 설정
        request.setId(id);

        service.updateAppointment(request);

        return ApiResponse.ok();
    }

}
