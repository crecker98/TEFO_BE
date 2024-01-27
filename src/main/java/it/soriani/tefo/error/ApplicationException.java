package it.soriani.tefo.error;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author christiansoriani on 24/01/24
 * @project TEFO_BE
 */

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@JsonIncludeProperties({"messageKey", "message"})
public class ApplicationException extends RuntimeException {

    protected final String messageKey;

    public ApplicationException() {
        this.messageKey = null;
    }

    public ApplicationException(String message) {
        this(message, null);
    }

    public ApplicationException(String message, String messageKey) {
        this(message, messageKey, null);
    }

    public ApplicationException(String message, String messageKey, Throwable cause) {
        super(message, cause);
        this.messageKey = messageKey;
    }

    public static ApplicationException of(String message) {
        return new ApplicationException(message);
    }

    public static ApplicationException of(Exception exception) {
        return new ApplicationException(exception.getMessage(), null, exception);
    }

    public static ApplicationException of(String message, String messageKey) {
        return new ApplicationException(message, messageKey);
    }

    public static ApplicationException of(String message, String messageKey, Throwable cause) {
        return new ApplicationException(message, messageKey, cause);
    }

}
