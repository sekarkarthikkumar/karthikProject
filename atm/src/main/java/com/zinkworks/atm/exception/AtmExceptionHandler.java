package com.zinkworks.atm.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.zinkworks.atm.model.ErrorResponse;

@ControllerAdvice
public class AtmExceptionHandler extends ResponseEntityExceptionHandler
{
    private String INCORRECT_REQUEST = "INCORRECT_REQUEST";
    private String INSUFFICIENT_BALANCE = "INSUFFICIENT_BALANCE";
    private String OUT_OF_CASH = "OUT_OF_CASH";
    
     
    @ExceptionHandler(AccountNumberNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleAccountNotFoundException
                        (AccountNumberNotFoundException ex, WebRequest request) 
    {
        ErrorResponse errorResponse = new ErrorResponse(INCORRECT_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
     
    @ExceptionHandler(InvalidPinNumberException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidPinNumberException
                        (InvalidPinNumberException ex, WebRequest request) {
      
        ErrorResponse errorResponse = new ErrorResponse(INCORRECT_REQUEST,  ex.getLocalizedMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(InsufficientBalanceException.class)
    public final ResponseEntity<ErrorResponse> hanldeInsufficientBalanceException
                        (InsufficientBalanceException ex, WebRequest request) {
      
        ErrorResponse errorResponse = new ErrorResponse(INSUFFICIENT_BALANCE,  ex.getLocalizedMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
    
    @ExceptionHandler(OutOfCashException.class)
    public final ResponseEntity<ErrorResponse> hanldeOutofCashException
                        (OutOfCashException ex, WebRequest request) {
      
        ErrorResponse errorResponse = new ErrorResponse(OUT_OF_CASH,  ex.getLocalizedMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
}
