package com.xenialsoft.api.core.beforeafter.model;

import java.util.List;

import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xenialsoft.api.common.util.IdUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterCreateRequest {
    
    private String title;
    
    private String description;
    
    private String image;
    
    private String beforeImage;
    
    private String afterImage;
    
    private String afterBlurImage;
    
    private BeforeAfterStatus status;
    
    @JsonIgnore
    @CreatedBy
    private String createdBy;
    
    private List<BeforeAfterCategories> beforeAfterCategoriesList;
    
    private List<BeforeAfterTags> beforeAfterTagsList; 
    
    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static BeforeAfter from(BeforeAfterCreateRequest request) {
        BeforeAfter beforeAfter = new BeforeAfter();
        beforeAfter.setId(IdUtils.generate());
        beforeAfter.setTitle(request.getTitle());
        beforeAfter.setDescription(request.getDescription());
        beforeAfter.setImage(request.getImage());
        beforeAfter.setBeforeImage(request.getBeforeImage());
        beforeAfter.setAfterImage(request.getAfterImage());
        beforeAfter.setAfterBlurImage(request.getAfterBlurImage());
        beforeAfter.setStatus(BeforeAfterStatus.ACTIVE);
        beforeAfter.setCreatedBy(request.getCreatedBy());

        if(request.getBeforeAfterCategoriesList() != null && request.getBeforeAfterCategoriesList().size() > 0) {
        	for(BeforeAfterCategories beforeAfterCategories : request.getBeforeAfterCategoriesList()) {
        		beforeAfterCategories.setBeforeAfterId(beforeAfter.getId());
        	}
        }
        beforeAfter.setBeforeAfterCategoriesList(request.getBeforeAfterCategoriesList());

        if(request.getBeforeAfterTagsList() != null && request.getBeforeAfterTagsList().size() > 0) {
        	for(BeforeAfterTags beforeAfterTags : request.getBeforeAfterTagsList()) {
        		beforeAfterTags.setBeforeAfterId(beforeAfter.getId());
        	}
        }
        beforeAfter.setBeforeAfterTagsList(request.getBeforeAfterTagsList());
        return beforeAfter;
    }

}
