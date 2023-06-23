package com.productservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String search;
    private Set<SearchKey> searchKeys;
}
