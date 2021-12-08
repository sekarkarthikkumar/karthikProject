package com.zinkworks.atm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zinkworks.atm.exception.AccountNumberNotFoundException;
import com.zinkworks.atm.exception.InsufficientBalanceException;
import com.zinkworks.atm.exception.InvalidPinNumberException;
import com.zinkworks.atm.exception.MinimumWithdrawLimitException;
import com.zinkworks.atm.exception.OutOfCashException;
import com.zinkworks.atm.model.AtmCashDetails;
import com.zinkworks.atm.model.TransactionDetail;
import com.zinkworks.atm.model.UserAccount;
import com.zinkworks.atm.respository.AtmCashDetailRespository;
import com.zinkworks.atm.respository.BankAccountRespository;
import com.zinkworks.atm.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);
	
	@Autowired
	private BankAccountRespository bankAccountRespository;
	
	@Autowired
	private AtmCashDetailRespository atmCashDetailRespository;
	
	@Override
	public UserAccount validateAccountdDetails(long accountNo, int pinNo) throws AccountNumberNotFoundException, InvalidPinNumberException {
		
	UserAccount userAcc = bankAccountRespository.findByAccountNumber(accountNo);
	
	logger.info("Inside validateAccountdDetails ");
	
	if(userAcc == null) {
		throw new AccountNumberNotFoundException("Account Number " + accountNo + " is not found.");
	} else if(userAcc.getPin() != pinNo ) {
		throw new InvalidPinNumberException("Pin number entered is incorrect");
	}
	
	return userAcc;
	}


	/** Method used to validate the withdrawal amount, calculate the notes detail and update the existing atm notes details 
	 * @throws MinimumWithdrawLimitException 
	 * 
	 */
	@Override
	public TransactionDetail withDrawAmount(UserAccount userAcc, Integer withDrawalAmt) throws InsufficientBalanceException, OutOfCashException, MinimumWithdrawLimitException {
	
		
		logger.info("Inside withDrawAmount ");
		
		if(withDrawalAmt < 20) {
			throw new MinimumWithdrawLimitException("Minimum withdrawal amount should be 20 Euro. Please try again");
		}
		
		long maxWithDrawAmount = userAcc.getBalanceAmount() +  userAcc.getOverdraftAmount();
		int withDrawAmount = withDrawalAmt;
		if( withDrawAmount > maxWithDrawAmount) {
			throw new InsufficientBalanceException("Requested withdraw amount " + withDrawAmount + " is more than maxiumum withdraw limit amount" + maxWithDrawAmount);
		}
		
		Long totalAtmAmountBal = atmCashDetailRespository.findAll().stream().mapToLong(p->p.getNotesValue() * p.getNotesVolume()).sum();
		logger.info("totalAtmAmountBal " + totalAtmAmountBal);
				
		if (withDrawAmount > totalAtmAmountBal ) {
			throw new OutOfCashException("Sorry! ATM Machine got OUT of CASH");
		}
				
		//Map holding note amount as key and volume as value
		Map<Integer, Integer> currentAtmCash=atmCashDetailRespository.findAll().stream().collect(Collectors.toMap(AtmCashDetails::getNotesValue, AtmCashDetails::getNotesVolume));
		
		//Map to store actual note details needed for the withdrawal amount 
		Map<Integer, Integer> actualCashNeeded = new HashMap<Integer,Integer>();

		//Iterating current atmCashdetails map to calculate notes required for withdrawal amount
		for(Map.Entry<Integer, Integer> map : currentAtmCash.entrySet() ) {
			
			if(withDrawAmount >= map.getKey() ) {
				//Number of notes need for withdrawal amount
				int noteVolume = withDrawAmount / map.getKey() ;
				if(map.getValue() < noteVolume) {					
					withDrawAmount = withDrawAmount - (map.getKey() * map.getValue());
					//Adding actual notes needed in Map
					actualCashNeeded.put(map.getKey(), map.getValue());
					//updating the existing balance note in atm machine
					currentAtmCash.replace(map.getKey(), map.getValue(), 0 );
				} else {
					actualCashNeeded.put(map.getKey(), noteVolume);
					currentAtmCash.replace(map.getKey(), map.getValue(), (map.getValue() - noteVolume) );
					withDrawAmount = withDrawAmount % map.getKey();
				}
			}
		}
		
		
		//Updating the notes details after withdrawal
		List<AtmCashDetails> atmCashDetails =currentAtmCash.entrySet().stream().map(p-> new AtmCashDetails(p.getKey(),p.getValue())).collect(Collectors.toList());
		atmCashDetailRespository.saveAll(atmCashDetails);
		
		Long remainingAccBal = maxWithDrawAmount - Long.parseLong(withDrawalAmt.toString());
		TransactionDetail transactionDetail = new TransactionDetail();
		transactionDetail.setAmount(withDrawalAmt);
		transactionDetail.setNotesDetails(actualCashNeeded);
		transactionDetail.setDescription("Remaining balance in your account is "+ remainingAccBal);
		transactionDetail.setTransactionStatus("SUCCESS");
		transactionDetail.setTransactionType("Cash Withdrawal Option");
		
		return transactionDetail;
	}

	/** This method to fetch current balance from user account
	 * 
	 * 
	 */
	@Override
	public TransactionDetail getAccountBalance(UserAccount userAcc) {
		
		logger.info("Inside getAccountBalance ");		
		long maxWithDrawAmount = userAcc.getOverdraftAmount() + userAcc.getBalanceAmount();
		
		TransactionDetail transactionDetail = new TransactionDetail();
		
		transactionDetail.setAmount(userAcc.getBalanceAmount());
		transactionDetail.setTransactionType("Check Balance Option");
		transactionDetail.setTransactionStatus("SUCCESS");
		transactionDetail.setDescription("Remaining balance is "+userAcc.getBalanceAmount() + " , but you can also withDraw upto "+ maxWithDrawAmount);
		return transactionDetail;
		
	}


	@Override
	public UserAccount findByAccountNumberAndPin(long accountNumber, int pin) {
		// TODO Auto-generated method stub
		return bankAccountRespository.findByAccountNumberAndPin(accountNumber,pin); 
		}


	@Override
	public List<AtmCashDetails> loadCash(List<AtmCashDetails> atmDetails) {
		// TODO Auto-generated method stub		
		atmCashDetailRespository.saveAll(atmDetails);
		return atmCashDetailRespository.findAll();
	}


	@Override
	public List<AtmCashDetails> getAtmCashDetails() {
		// TODO Auto-generated method stub
		return atmCashDetailRespository.findAll();
	}


	@Override
	public UserAccount loadAccountDetail(UserAccount userAcc) {
		  bankAccountRespository.save(userAcc);
		return bankAccountRespository.findByAccountNumberAndPin(userAcc.getAccountNumber(),userAcc.getPin());
	}


	@Override
	public List<UserAccount> findAllAccount() {
		// TODO Auto-generated method stub
		return bankAccountRespository.findAll();
	}

	
	
	
}
