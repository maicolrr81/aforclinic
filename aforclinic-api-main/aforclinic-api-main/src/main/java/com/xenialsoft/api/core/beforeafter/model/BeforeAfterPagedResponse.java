package com.xenialsoft.api.core.beforeafter.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xenialsoft.api.common.Sequenceable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterPagedResponse implements Sequenceable {

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

    private String categoriesName;

    private String tagsName;

    /**
     * 데이터 변환
     * 
     * @param beforeAfterBanner
     * @return
     */
    public static BeforeAfterPagedResponse from(BeforeAfter beforeAfter) {
        BeforeAfterPagedResponse res = new BeforeAfterPagedResponse();
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

        String categoriesName = "";
        if (beforeAfter.getBeforeAfterCategoriesList() != null && beforeAfter.getBeforeAfterCategoriesList().size() > 0) {            
            if(beforeAfter.getBeforeAfterCategoriesList().size() != 1 || beforeAfter.getBeforeAfterCategoriesList().get(0) != null ) {
                for (BeforeAfterCategories beforeAfterCategories : beforeAfter.getBeforeAfterCategoriesList()) {
                    if (!"".equals(categoriesName))
                        categoriesName += ",";
                    if(beforeAfterCategories.getCategory() != null) {
                        categoriesName += beforeAfterCategories.getCategory().getName();
                    }
                }
            }            
        }
        if (!"".equals(categoriesName))
            res.categoriesName = categoriesName;

        String tagsName = "";
        if (beforeAfter.getBeforeAfterTagsList() != null && beforeAfter.getBeforeAfterTagsList().size() > 0) {
            if(beforeAfter.getBeforeAfterTagsList().size() != 1 || beforeAfter.getBeforeAfterTagsList().get(0) != null ) {
                for (BeforeAfterTags beforeAfterTags : beforeAfter.getBeforeAfterTagsList()) {
                    if (!"".equals(tagsName))
                        tagsName += ",";
                    tagsName += beforeAfterTags.getTag();
                }
            }
        }
        if (!"".equals(tagsName))
            res.tagsName = tagsName;

        return res;
    }
}
