package com.xenialsoft.api.core.popup.model;

import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PopupUpdateStatusRequest {
	
    @Setter
    @JsonIgnore
	private String id;
    
    @Setter
    @JsonIgnore
	private PopupStatus status;
    
    @LastModifiedBy
    @JsonIgnore
    private String modifiedBy;

}
