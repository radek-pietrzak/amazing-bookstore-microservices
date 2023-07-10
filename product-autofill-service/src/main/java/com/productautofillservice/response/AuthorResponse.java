package com.productautofillservice.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AuthorResponse {
    private String authorName;
    private String authorDescription;
}
