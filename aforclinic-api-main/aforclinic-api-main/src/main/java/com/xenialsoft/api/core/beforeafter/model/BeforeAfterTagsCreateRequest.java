package com.xenialsoft.api.core.beforeafter.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterTagsCreateRequest {
    
    private String beforeAfterId;

    private String tag;
    
    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static BeforeAfterTags from(BeforeAfterTagsCreateRequest request) {
        BeforeAfterTags beforeAfterTags = new BeforeAfterTags();
        beforeAfterTags.setBeforeAfterId(request.getBeforeAfterId());
        beforeAfterTags.setTag(request.getTag());
        return beforeAfterTags;
    }

}
