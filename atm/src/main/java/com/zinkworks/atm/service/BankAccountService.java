package com.zinkworks.atm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zinkworks.atm.exception.AccountNumberNotFoundException;
import com.zinkworks.atm.exception.InsufficientBalanceException;
import com.zinkworks.atm.exception.InvalidPinNumberException;
import com.zinkworks.atm.exception.OutOfCashException;
import com.zinkworks.atm.model.AtmCashDetails;
import com.zinkworks.atm.model.TransactionDetail;
import com.zinkworks.atm.model.UserAccount;

@Service
public interface BankAccountService {

public UserAccount validateAccountdDetails(long Account, int pin) throws AccountNumberNotFoundException, InvalidPinNumberException;

public TransactionDetail getAccountBalance(UserAccount account);

public TransactionDetail withDrawAmount(UserAccount account, Integer amount) throws InsufficientBalanceException, OutOfCashException;

public List<AtmCashDetails> loadCash(List<AtmCashDetails>  atmDetails); 

public List<AtmCashDetails> getAtmCashDetails();

public UserAccount loadAccountDetail(UserAccount  userAcc); 

public List<UserAccount> findAllAccount();

public UserAccount findByAccountNumberAndPin(long accountNumber,int pin);
}
