package com.productservice.api.request;

import com.productservice.validation.ValidationErrors;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublisherRequest {
    @NotNull(message = ValidationErrors.PUBLISHER_NAME_NULL)
    @Size(min = 1, max = 255, message = ValidationErrors.PUBLISHER_NAME_LENGTH)
    private String publisherName;
    @Size(max = 1000, message = ValidationErrors.PUBLISHER_DESCRIPTION_LENGTH)
    private String description;
}
