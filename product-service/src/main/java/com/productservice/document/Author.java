package com.productservice.document;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Author {
    private String authorName;
    private String authorDescription;
}
