package com.productservice.api.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageCriteria {
    private Integer pageNo;
    private Integer pageSize;

}
