package pl.radek.productservice.api.handler;

import pl.radek.productservice.api.response.ErrorResponse;
import pl.radek.productservice.api.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Response response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatusCode().value())
                .error(ex.getStatusCode())
                .path(request.getRequestURI())
                .validationMessages(ex.getBindingResult()
                        .getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()))
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest servletRequest) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Response response = getErrorResponse(httpStatus, ex, servletRequest);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest servletRequest) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Response response = getErrorResponse(httpStatus, ex, servletRequest);
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static ErrorResponse getErrorResponse(HttpStatus httpStatus, Exception ex, HttpServletRequest servletRequest) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus)
                .path(servletRequest.getRequestURI())
                .exception(ex.toString())
                .build();
    }

}

