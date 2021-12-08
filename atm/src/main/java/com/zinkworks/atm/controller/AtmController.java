package com.zinkworks.atm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zinkworks.atm.exception.AccountNumberNotFoundException;
import com.zinkworks.atm.exception.InsufficientBalanceException;
import com.zinkworks.atm.exception.InvalidPinNumberException;
import com.zinkworks.atm.exception.MinimumWithdrawLimitException;
import com.zinkworks.atm.exception.OutOfCashException;
import com.zinkworks.atm.model.AtmCashDetails;
import com.zinkworks.atm.model.TransactionDetail;
import com.zinkworks.atm.model.UserAccount;
import com.zinkworks.atm.service.BankAccountService;

@RestController
@RequestMapping("/atm")
public class AtmController {

	
	@Autowired
	private BankAccountService bankAccountService;
	
	/**
	 * Get user account details
	 * @param accountNumber
	 * @param pin
	 * @return
	 */
	@GetMapping(value = "/{accountNumber}/{pin}")
	public UserAccount findByAccountNumber(@PathVariable("accountNumber")  long accountNumber,@PathVariable("pin")  int pin){
		
		return bankAccountService.findByAccountNumberAndPin(accountNumber, pin);
	    }
	
		
	/** This service is used to fetch the current balance from user account
	 * 
	 * @param accountNumber
	 * @param pin
	 * @return TransactionDetail
	 * @throws AccountNumberNotFoundException
	 * @throws InvalidPinNumberException
	 */
	@GetMapping(value = "/balance/{accountNumber}/{pin}")
	public ResponseEntity<TransactionDetail> getAccountBalance(@PathVariable("accountNumber")  long accountNumber,@PathVariable("pin")  int pin) throws AccountNumberNotFoundException, InvalidPinNumberException{
		TransactionDetail transactionDetail = null;
		UserAccount userAccount =bankAccountService.validateAccountdDetails(accountNumber, pin);
		if(userAccount!=null) {
		transactionDetail =bankAccountService.getAccountBalance(userAccount);
		}
		
		return new ResponseEntity<TransactionDetail>(transactionDetail,HttpStatus.OK);
	    
	
	}
	
	/**
	 * WithDraw service call 
	 * 
	 * @param accountNumber
	 * @param pin
	 * @param withDrawamount
	 * @return
	 * @throws AccountNumberNotFoundException
	 * @throws InvalidPinNumberException
	 * @throws InsufficientBalanceException
	 * @throws OutOfCashException
	 * @throws MinimumWithdrawLimitException 
	 */
	@GetMapping(value = "/withDraw/{accountNumber}/{pin}/{amount}")
	public ResponseEntity<TransactionDetail> withDrawAmount(@PathVariable("accountNumber")  long accountNumber,@PathVariable("pin")  int pin, @PathVariable("amount")  int withDrawamount) throws AccountNumberNotFoundException, InvalidPinNumberException, InsufficientBalanceException, OutOfCashException, MinimumWithdrawLimitException{
		
		TransactionDetail transactionDetail = null;
		UserAccount userAccount =bankAccountService.validateAccountdDetails(accountNumber, pin);
		if(userAccount!=null) {		
		 transactionDetail =bankAccountService.withDrawAmount(userAccount,withDrawamount);
		}
		return new ResponseEntity<TransactionDetail>(transactionDetail,HttpStatus.OK);
	    
	
	}
	
	/** Loading user account
	 * 
	 */ 	
	@PostMapping(value = "/loadAccount")
    public UserAccount loadAccount(@RequestBody  UserAccount account) {
		
	        return bankAccountService.loadAccountDetail(account); 
	    }
	
	@GetMapping(value = "/allAccountDetails")
	public List<UserAccount> findByAll(){
	        return bankAccountService.findAllAccount();
	    }
	
	/** Loading cash notes details in atm
	 * 
	 */ 
	@PostMapping(value = "/loadCash")
    public List<AtmCashDetails> loadCash(@RequestBody  List<AtmCashDetails> bankDetails) {	
	        return bankAccountService.loadCash(bankDetails);
	    }

	@GetMapping(value = "/atmCashDetails")
	public List<AtmCashDetails> findAll(){
	        return bankAccountService.getAtmCashDetails();
	    }
}
