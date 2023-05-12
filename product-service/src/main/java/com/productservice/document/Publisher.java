package com.productservice.document;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Publisher {
    private String publisherName;
    private String description;
}
