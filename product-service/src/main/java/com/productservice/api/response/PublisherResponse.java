package com.productservice.api.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
@JsonSerialize
public class PublisherResponse {
    private String publisherName;
    private String description;
}
