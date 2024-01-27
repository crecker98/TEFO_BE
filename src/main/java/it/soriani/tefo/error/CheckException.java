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
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@JsonIncludeProperties({"messageKey", "message"})
public class CheckException extends RuntimeException {

    protected final String messageKey;

    public CheckException() {
        this.messageKey = null;
    }

    public CheckException(String message) {
        this(message, null);
    }

    public CheckException(String message, String messageKey) {
        this(message, messageKey, null);
    }

    public CheckException(String message, String messageKey, Throwable cause) {
        super(message, cause);
        this.messageKey = messageKey;
    }

    public static CheckException of(String message) {
        return new CheckException(message);
    }

    public static CheckException of(Exception exception) {
        return new CheckException(exception.getMessage(), null, exception);
    }

    public static CheckException of(String message, String messageKey) {
        return new CheckException(message, messageKey);
    }

    public static CheckException of(String message, String messageKey, Throwable cause) {
        return new CheckException(message, messageKey, cause);
    }

}
