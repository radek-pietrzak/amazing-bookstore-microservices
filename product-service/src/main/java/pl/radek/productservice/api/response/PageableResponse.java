package pl.radek.productservice.api.response;

import pl.radek.productservice.api.criteria.PageInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class PageableResponse implements Response {
    private PageInfo pageInfo;
    private Boolean hasNextPage;
    private Integer totalPages;
    private Long totalSize;
}
