package com.xenialsoft.api.core.beforeafter.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterResponse implements Sequenceable {

    /**
     * 순번
     */
    @Setter
    private Long sequence;

    private String id;
    
    private String title;
    
    private String description;
    
    private String image;
    
    private String beforeImage;
    
    private String afterImage;
    
    private String afterBlurImage;
    
    private BeforeAfterStatus status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    private String modifiedBy;
    
    private List<BeforeAfterCategories> beforeAfterCategoriesList;
    
    private List<BeforeAfterTags> beforeAfterTagsList; 

    /**
     * 데이터 변환
     * 
     * @param beforeAfterBanner
     * @return
     */
    public static BeforeAfterResponse from(BeforeAfter beforeAfter) {
        BeforeAfterResponse res = new BeforeAfterResponse();
        res.id = beforeAfter.getId();
        res.title = beforeAfter.getTitle();
        res.description = beforeAfter.getDescription();
        res.image = beforeAfter.getImage();
        res.beforeImage = beforeAfter.getBeforeImage();
        res.afterImage = beforeAfter.getAfterImage();
        res.afterBlurImage = beforeAfter.getAfterBlurImage();
        res.status = beforeAfter.getStatus();
        res.createdAt = beforeAfter.getCreatedAt();
        res.createdBy = beforeAfter.getCreatedBy();
        res.modifiedAt = beforeAfter.getModifiedAt();
        res.modifiedBy = beforeAfter.getModifiedBy();
        res.beforeAfterCategoriesList = beforeAfter.getBeforeAfterCategoriesList();
        res.beforeAfterTagsList = beforeAfter.getBeforeAfterTagsList();
        return res;
    }
}
