package com.xenialsoft.api.core.post.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xenialsoft.api.common.data.ApiPageRequest;
import com.xenialsoft.api.common.data.ApiPagedResponse;
import com.xenialsoft.api.common.data.ApiResponse;
import com.xenialsoft.api.common.util.SequenceUtils;
import com.xenialsoft.api.core.post.model.PostCreateRequest;
import com.xenialsoft.api.core.post.model.PostPageRequest;
import com.xenialsoft.api.core.post.model.PostPagedResponse;
import com.xenialsoft.api.core.post.model.PostType;
import com.xenialsoft.api.core.post.model.PostUpdateRequest;
import com.xenialsoft.api.core.post.service.PostService;
import com.xenialsoft.api.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@PreAuthorize("hasRole('ADMIN')")
public class PostController {

    private final PostService service;

    @GetMapping
    public ResponseEntity<?> getPostList(PostPageRequest request, ApiPageRequest paging) {

        // 게시글 타입 설정
        request.setType(PostType.DEFAULT);

        long totalCount = service.getTotalCount(request);
        if (totalCount == 0) {
            return ApiPagedResponse.ok(paging);
        }

        List<PostPagedResponse> content = service.getPostList(request, paging);

        // 순번 설정
        SequenceUtils.assign(content, paging.getStartSequence(totalCount), true);

        return ApiPagedResponse.ok(content, totalCount, paging);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return ApiResponse.ok(service.getPostById(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다.")));
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody @Valid PostCreateRequest request) {
        service.createPost(request);
        return ApiResponse.ok();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody @Valid PostUpdateRequest request) {
        request.setId(id);
        service.updatePost(request);
        return ApiResponse.ok();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        request.setId(id);
        service.updatePost(request);
        return ApiResponse.ok();
    }
    
}
