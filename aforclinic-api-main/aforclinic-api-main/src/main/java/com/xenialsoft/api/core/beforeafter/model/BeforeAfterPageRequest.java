package com.xenialsoft.api.core.beforeafter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BeforeAfterPageRequest {

    private String title;

    private String categoryId;

    private String tag;

}
