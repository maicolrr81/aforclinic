package com.xenialsoft.api.core.beforeafter.model;

import java.util.List;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeforeAfter extends Auditable {

	private String id;
	
	private String title;
	
	private String description;
	
	private String image;
	
	private String beforeImage;
	
	private String afterImage;
	
	private String afterBlurImage;
	
	private BeforeAfterStatus status;
	
	private List<BeforeAfterCategories> beforeAfterCategoriesList;
	
	private List<BeforeAfterTags> beforeAfterTagsList;	
}
