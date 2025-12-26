package com.xenialsoft.api.core.attachment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.core.attachment.mapper.AttachmentMapper;
import com.xenialsoft.api.core.attachment.model.Attachment;
import com.xenialsoft.api.core.attachment.model.AttachmentRequest;
import com.xenialsoft.api.core.attachment.model.AttachmentResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentMapper mapper;

    @Transactional(readOnly = true)
    public List<AttachmentResponse> getAttachmentList(String targetId, String targetType) {
        return mapper.selectList(targetId, targetType).stream().map(AttachmentResponse::from).toList();
    }

    @Transactional
    public void createAttachment(AttachmentRequest data) {
        mapper.insert(Attachment.from(data));
    }

    @Transactional
    public void deleteAttachments(String targetId, String targetType) {
        mapper.delete(Attachment.from(AttachmentRequest.of(targetId, targetType)));
    }

    @Transactional
    public void saveAttachments(List<AttachmentRequest> attachments, String targetId, String targetType,
            boolean clean) {
        if (clean) {
            deleteAttachments(targetId, targetType);
        }
        if (attachments == null || attachments.isEmpty()) {
            return;
        }
        for (AttachmentRequest attachment : attachments) {
            attachment.setTargetId(targetId);
            attachment.setTargetType(targetType);
            createAttachment(attachment);
        }
    }

}
