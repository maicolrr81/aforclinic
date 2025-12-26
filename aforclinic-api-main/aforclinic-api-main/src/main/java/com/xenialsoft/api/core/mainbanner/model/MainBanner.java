package com.xenialsoft.api.core.mainbanner.model;

import com.xenialsoft.api.common.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MainBanner extends Auditable {

	private String id;

	private String title;
	
	private String imageId;

	private String linkUri;

	private int displayOrder;

	private MainBannerStatus status;

}
