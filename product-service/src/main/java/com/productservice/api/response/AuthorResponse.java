package com.productservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AuthorResponse {
    private String name;
    private String description;
}
