package com.zinkworks.atm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse
{
	  private String message;
	  private String errorDescription;
	
	
    public ErrorResponse(String message, String errorDescription) {
        super();
        this.message = message;
        this.errorDescription = errorDescription;
    }
  
  
}