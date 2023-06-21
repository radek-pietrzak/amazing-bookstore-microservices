package com.productservice.api.response;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchSpecCriteria {
    private Set<String> keys;
    private SpecificationType operation;
    private String content;

}
