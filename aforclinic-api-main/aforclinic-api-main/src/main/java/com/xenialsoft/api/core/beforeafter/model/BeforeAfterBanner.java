package com.xenialsoft.api.core.beforeafter.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeforeAfterBanner extends Auditable {
	
	private String id;
	
	private String title;
	
	private String description;
	
	private BeforeAfterBannerMediaType mediaType;
	
	private String mediaContent;
	
	private BeforeAfterBannerStatus status;
	
}
