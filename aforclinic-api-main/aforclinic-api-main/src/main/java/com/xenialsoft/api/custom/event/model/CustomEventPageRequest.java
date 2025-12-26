package com.xenialsoft.api.custom.event.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomEventPageRequest {

    private List<String> eventIds;

}
