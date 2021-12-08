package com.zinkworks.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class OutOfCashException extends Exception {
	
	public OutOfCashException (String message) {
        super(message);
    }
}