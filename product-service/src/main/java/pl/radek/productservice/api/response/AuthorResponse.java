package pl.radek.productservice.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AuthorResponse {
    @Schema(description = "Author name", example = "J.D.")
    private String authorName;

    @Schema(description = "Author description", example = "Salinger")
    private String authorDescription;
}
