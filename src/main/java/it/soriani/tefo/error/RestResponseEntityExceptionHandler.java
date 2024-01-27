package it.soriani.tefo.error;

import it.soriani.tefo.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ApiError> buildApiErrorResponse(int status, String message, String description) {
        return ResponseEntity.status(status).body(ApiError.builder()
                .messageCode(message)
                .description(description)
                .build());
    }

    @ExceptionHandler({ApplicationException.class, Exception.class})
    protected ResponseEntity<ApiError> handleConflict(ApplicationException ex) {
        return buildApiErrorResponse(500, ex.getMessageKey(), ex.getMessage());
    }

    @ExceptionHandler({CheckException.class})
    protected ResponseEntity<ApiError> handleConflict(CheckException ex) {
        return buildApiErrorResponse(400, ex.getMessageKey(), ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final List<ApiError> apiErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ApiError.builder()
                        .messageCode(error.getCode())
                        .description(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        return new ResponseEntity<>(apiErrors, new HttpHeaders(), Objects.requireNonNull(HttpStatus.BAD_REQUEST));
    }

}
