package com.productautofillservice.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private String general;
    private String title;
    private String author;
}
