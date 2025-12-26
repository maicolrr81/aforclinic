package com.xenialsoft.api.common;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auditable {

    /**
     * 등록일시
     */
    protected LocalDateTime createdAt;

    /**
     * 등록자
     */
    protected String createdBy;

    /**
     * 수정일시
     */
    protected LocalDateTime modifiedAt;

    /**
     * 수정자
     */
    protected String modifiedBy;

}
