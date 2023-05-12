package com.productservice.api.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class PublisherResponse {
    private String publisherName;
    private String description;
}
