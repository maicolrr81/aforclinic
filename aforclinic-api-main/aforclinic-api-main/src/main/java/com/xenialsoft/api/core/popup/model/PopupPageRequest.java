package com.xenialsoft.api.core.popup.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PopupPageRequest {

    private String type;
    
    private String title;

    private PopupProgressStatus progressType;

    private PopupStatus status;

}
