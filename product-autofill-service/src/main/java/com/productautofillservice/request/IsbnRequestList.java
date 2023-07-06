package com.productautofillservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class IsbnRequestList {
    List<String> isbn;
}
