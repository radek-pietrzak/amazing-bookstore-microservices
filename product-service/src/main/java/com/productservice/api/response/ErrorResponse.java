package com.productservice.api.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class ErrorResponse implements Response {
    LocalDateTime timestamp;
    Integer status;
    HttpStatusCode error;
    String path;
    List<String> validationMessages;
}
