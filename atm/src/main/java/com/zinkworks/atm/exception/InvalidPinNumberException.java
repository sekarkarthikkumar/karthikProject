package com.zinkworks.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPinNumberException extends Exception {
	

	public InvalidPinNumberException (String message) {
        super(message);
    }
}
