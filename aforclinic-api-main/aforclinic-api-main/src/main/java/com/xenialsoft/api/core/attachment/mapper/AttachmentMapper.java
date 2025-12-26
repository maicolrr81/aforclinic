package com.xenialsoft.api.core.attachment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xenialsoft.api.core.attachment.model.Attachment;

@Mapper
public interface AttachmentMapper {

    public List<Attachment> selectList(
            // 대상 식별자
            @Param("targetId") String targetId,
            // 대상 타입
            @Param("targetType") String targetType);

    public int insert(Attachment attachment);

    public int delete(Attachment attachment);

}
