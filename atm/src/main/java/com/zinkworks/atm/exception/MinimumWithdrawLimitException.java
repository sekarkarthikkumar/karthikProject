package com.zinkworks.atm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class MinimumWithdrawLimitException extends Exception {
	
	public MinimumWithdrawLimitException (String message) {
        super(message);
    }
}