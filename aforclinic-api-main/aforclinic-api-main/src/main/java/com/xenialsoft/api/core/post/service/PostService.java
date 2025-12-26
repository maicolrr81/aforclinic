package com.xenialsoft.api.core.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.config.aspect.Auditable;
import com.xenialsoft.api.core.attachment.model.AttachmentResponse;
import com.xenialsoft.api.core.attachment.service.AttachmentService;
import com.xenialsoft.api.core.post.mapper.PostMapper;
import com.xenialsoft.api.core.post.model.Post;
import com.xenialsoft.api.core.post.model.PostCreateRequest;
import com.xenialsoft.api.core.post.model.PostPageRequest;
import com.xenialsoft.api.core.post.model.PostPagedResponse;
import com.xenialsoft.api.core.post.model.PostResponse;
import com.xenialsoft.api.core.post.model.PostUpdateRequest;
import com.xenialsoft.api.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final String ATTACHMENT_TARGET_TYPE = "post";

    private final AttachmentService attachment;

    private final PostMapper mapper;

    @Transactional(readOnly = true)
    public List<PostPagedResponse> getPostList(PostPageRequest request, ApiPageRequest paging) {
        return mapper.selectList(request, paging).stream().map(PostPagedResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public long getTotalCount(PostPageRequest request) {
        return mapper.selectTotalCount(request);
    }

    @Transactional(readOnly = true)
    public Optional<PostResponse> getPostById(Long id) {
        return Optional.ofNullable(mapper.selectById(id)).map(PostResponse::from).flatMap(post -> {
            List<AttachmentResponse> attachments = attachment.getAttachmentList(String.valueOf(post.getId()),
                    ATTACHMENT_TARGET_TYPE);
            if (!attachments.isEmpty()) {
                post.setAttachments(attachments);
            }
            return Optional.of(post);
        });
    }

    @Auditable
    @Transactional
    public PostResponse createPost(PostCreateRequest data) {

        Post post = PostCreateRequest.from(data);

        int count = mapper.insert(post);
        if (count != 1) {
            throw new ServiceException("게시글 등록 중 오류가 발생했습니다.");
        }

        attachment.saveAttachments(data.getAttachments(), String.valueOf(post.getId()), ATTACHMENT_TARGET_TYPE, false);
        
        return PostResponse.from(post);
    }

    @Auditable
    @Transactional
    public void updatePost(PostUpdateRequest data) {

        Post post = PostUpdateRequest.from(data);

        int count = mapper.update(post);
        if (count != 1) {
            throw new ServiceException("게시글 수정 중 오류가 발생했습니다.");
        }

        attachment.saveAttachments(data.getAttachments(), String.valueOf(post.getId()), ATTACHMENT_TARGET_TYPE, true);
    }
    
    @Transactional
    public void deleteUpdatePost(Post data) {
        int count = mapper.deleteUpdate(data);
        if(count == 0) {
            throw new ServiceException("게시글 삭제 중 오류가 발생했습니다.");
        }
    }

}
