package com.xenialsoft.api.core.appointment.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.common.util.SequenceUtils;
import com.xenialsoft.api.core.appointment.model.AppointmentPageRequest;
import com.xenialsoft.api.core.appointment.model.AppointmentPagedResponse;
import com.xenialsoft.api.core.appointment.model.AppointmentUpdateRequest;
import com.xenialsoft.api.core.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@PreAuthorize("hasRole('ADMIN')")
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public ResponseEntity<?> getAppointmentList(AppointmentPageRequest request, ApiPageRequest paging) {

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<AppointmentPagedResponse> content = service.getAppointmentList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content, paging.getStartSequence(totalCount), true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateAppointment(@PathVariable String id,
            @RequestBody AppointmentUpdateRequest request) {

        request.setId(id);

        service.updateAppointment(request);

        return ApiResponse.ok();
    }

}
