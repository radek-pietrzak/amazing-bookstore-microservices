package com.productautofillservice.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IsbnDBListRequest {
    List<String> isbn;
}
