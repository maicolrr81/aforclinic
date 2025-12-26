package com.xenialsoft.api.core.beforeafter.model;

import java.util.List;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BeforeAfterUpdateRequest {

    @JsonIgnore
    @Setter
    private String id;

    private String title;
    
    private String description;
    
    private String image;
    
    private String beforeImage;
    
    private String afterImage;
    
    private String afterBlurImage;
    
    @JsonIgnore
    @LastModifiedBy
    private String modifiedBy;
    
    private List<BeforeAfterCategories> beforeAfterCategoriesList;
    
    private List<BeforeAfterTags> beforeAfterTagsList; 
    
    /**
     * 데이터 변환
     * 
     * @param data
     * @return
     */
    public static BeforeAfter from(BeforeAfterUpdateRequest request) {
        BeforeAfter beforeAfter = new BeforeAfter();
        beforeAfter.setId(request.getId());
        beforeAfter.setTitle(request.getTitle());
        beforeAfter.setDescription(request.getDescription());
        beforeAfter.setImage(request.getImage());
        beforeAfter.setBeforeImage(request.getBeforeImage());
        beforeAfter.setAfterImage(request.getAfterImage());
        beforeAfter.setAfterBlurImage(request.getAfterBlurImage());
        beforeAfter.setModifiedBy(request.getModifiedBy());
        
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
