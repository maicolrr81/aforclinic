package com.xenialsoft.api.core.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 요약 정보
 */
@Getter
@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategorySummaryInfo {

    /**
     * 식별자
     */
    private String id;

    /**
     * 이름
     */
    private String name;

    /**
     * 설명
     */
    private String description;

    /**
     * 엔티티 변환
     * 
     * @param data
     * @return
     */
    public static CategorySummaryInfo from(CategoryPagedResponse data) {
        CategorySummaryInfo categorySummaryInfo = new CategorySummaryInfo();
        categorySummaryInfo.id = data.getId();
        categorySummaryInfo.name = data.getName();
        categorySummaryInfo.description = data.getDescription();
        return categorySummaryInfo;
    }

}
