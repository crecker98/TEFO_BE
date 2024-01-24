package it.soriani.tefo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author christiansoriani on 24/01/24
 * @project TEFO_BE
 */

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@AllArgsConstructor(staticName = "of")
public class ApplicationException extends RuntimeException {

    private String messageCode;

    private String description;


}
