package com.zinkworks.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class InsufficientBalanceException extends Exception {
	

	public InsufficientBalanceException (String message) {
        super(message);
    }
}
