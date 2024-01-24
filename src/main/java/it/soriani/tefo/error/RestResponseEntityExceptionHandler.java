package it.soriani.tefo.error;

import it.soriani.tefo.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author christiansoriani on 20/01/24
 * @project TEFO_BE
 */

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<ApiError> handleConflict(ApplicationException ex, WebRequest request) {
        log.error("Error: ", ex);
        return ResponseEntity.status(500).body(ApiError.builder()
                .messageCode(ex.getMessageCode())
                .description(ex.getDescription())
                .build());
    }

}
