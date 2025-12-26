package com.xenialsoft.api.custom.beforeafter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xenialsoft.api.core.beforeafter.model.BeforeAfterPagedResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomBeforeAfterPagedResponse {

    /**
     * 전후사진 식별자
     */
    private String id;

    /**
     * 전후사진 제목
     */
    private String title;

    /**
     * 전후사진 설명
     */
    private String description;

    /**
     * 전후 사진
     */
    private String image;
    
    /**
     * 전 사진
     */
    private String beforeImage;

    /**
     * 후 사진
     */
    private String afterImage;

    /**
     * 데이터 변환
     * 
     * @param beforeafter
     * @param blur
     * @return
     */
    public static CustomBeforeAfterPagedResponse from(BeforeAfterPagedResponse beforeafter, boolean blur) {
        CustomBeforeAfterPagedResponse data = new CustomBeforeAfterPagedResponse();
        data.id = beforeafter.getId();
        data.title = beforeafter.getTitle();
        data.description = beforeafter.getDescription();
        data.image = beforeafter.getImage();
        data.beforeImage = beforeafter.getBeforeImage();
        data.afterImage = blur ? beforeafter.getAfterBlurImage() : beforeafter.getAfterImage();
        return data;
    }

}
