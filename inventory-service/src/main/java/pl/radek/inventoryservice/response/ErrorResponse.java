package pl.radek.inventoryservice.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse implements Response {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
}
