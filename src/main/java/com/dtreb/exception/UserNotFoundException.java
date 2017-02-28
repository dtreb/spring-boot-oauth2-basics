package com.dtreb.exception;

import com.dtreb.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represents the case when correspondent {@link User} can not be found in the storage.
 *
 * @author dtreb
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
