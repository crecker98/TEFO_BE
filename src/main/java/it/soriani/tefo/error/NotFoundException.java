package it.soriani.tefo.error;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author christiansoriani on 30/01/24
 * @project TEFO_BE
 */

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@JsonIncludeProperties({"messageKey", "message"})
public class NotFoundException extends RuntimeException {

    protected final String messageKey;

    public NotFoundException() {
        this.messageKey = null;
    }

    public NotFoundException(String message) {
        this(message, null);
    }

    public NotFoundException(String message, String messageKey) {
        this(message, messageKey, null);
    }

    public NotFoundException(String message, String messageKey, Throwable cause) {
        super(message, cause);
        this.messageKey = messageKey;
    }

    public static NotFoundException of(String message) {
        return new NotFoundException(message);
    }

    public static NotFoundException of(Exception exception) {
        return new NotFoundException(exception.getMessage(), null, exception);
    }

    public static NotFoundException of(String message, String messageKey) {
        return new NotFoundException(message, messageKey);
    }

    public static NotFoundException of(String message, String messageKey, Throwable cause) {
        return new NotFoundException(message, messageKey, cause);
    }

}
