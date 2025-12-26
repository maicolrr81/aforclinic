package com.xenialsoft.api.core.mainbanner.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MainBannerPageRequest {

    private String text;
    
    private MainBannerStatus status;

}
