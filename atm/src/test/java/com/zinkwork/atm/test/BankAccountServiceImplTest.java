package com.zinkwork.atm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.zinkworks.atm.exception.InsufficientBalanceException;
import com.zinkworks.atm.exception.MinimumWithdrawLimitException;
import com.zinkworks.atm.exception.OutOfCashException;
import com.zinkworks.atm.model.AtmCashDetails;
import com.zinkworks.atm.model.TransactionDetail;
import com.zinkworks.atm.model.UserAccount;
import com.zinkworks.atm.respository.AtmCashDetailRespository;
import com.zinkworks.atm.respository.BankAccountRespository;
import com.zinkworks.atm.service.BankAccountService;
import com.zinkworks.atm.serviceImpl.BankAccountServiceImpl;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
public class BankAccountServiceImplTest {
	
	@MockBean
	private BankAccountRespository bankAccountRespository;
	
	@MockBean
	private AtmCashDetailRespository atmCashDetailRespository;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	 @TestConfiguration
	    static class BankAccountServiceImplTestConfiguration {
	        @Bean
	        public BankAccountService employeeService() {
	            return new BankAccountServiceImpl();
	        }
	    }
	@Before
	public void setUp() {
		//mocking the repo mtd to get user account
		Mockito.when(bankAccountRespository.findByAccountNumberAndPin(123456789L,1234)).thenReturn(getUserAccount());
		Mockito.when(atmCashDetailRespository.findAll()).thenReturn(getAtmCashDetails());
	}
	

	private static UserAccount getUserAccount() {		
		UserAccount account = new UserAccount();
		account.setAccountNumber(123456789);
		account.setBalanceAmount(800);
		account.setOverdraftAmount(200);
		account.setPin(1234);
		return account;
	}
	
	private static List<AtmCashDetails> getAtmCashDetails() {
		
		List<AtmCashDetails> cashList = new ArrayList<AtmCashDetails>();
		
		cashList.add( new AtmCashDetails(50,10));
		cashList.add( new AtmCashDetails(20,30));
		cashList.add( new AtmCashDetails(10,30));
		cashList.add( new AtmCashDetails(5,20));
				
		return cashList;
	}
	
	
	@Test
	public void findAccountTest() {			
		long balanceAmount = 800L;
		long accNo = 123456789L;
		int pin =1234;
		//Get the user account details
		UserAccount account = bankAccountService.findByAccountNumberAndPin(accNo, pin);
		
		Assert.assertEquals(account.getBalanceAmount(),balanceAmount);
		Assert.assertEquals(account.getAccountNumber(),accNo);
	}

	//Test for checking balance amount mtd
	@Test
	public void checkBalanceTest() {			
	
		long accNo = 123456789L;
		int pin =1234;
		int actualBalAmt= 800;
		String transDescriptionForMaxLimit="Remaining balance is "+800 + " , but you can also withDraw upto "+ 1000;
		
		UserAccount account = bankAccountService.findByAccountNumberAndPin(accNo, pin);
		//Checking for balance amount with max withdraw amount details
		TransactionDetail  transactionDetail =bankAccountService.getAccountBalance(account); 
		
		Assert.assertEquals(actualBalAmt,transactionDetail.getAmount());
		Assert.assertEquals(transDescriptionForMaxLimit,transactionDetail.getDescription());
	}
	
	
	//Test for withdraw amount mtd
	@Test
	public void checkWithDrawTest() {			
		long accNo = 123456789L;
		int pin =1234;
		int withDrawAmt = 125;
		String description="Remaining balance in your account is "+ 875;
		UserAccount account = bankAccountService.findByAccountNumberAndPin(accNo, pin);
		TransactionDetail  transactionDetail=null;
		//getting withdraw amount and remaining balance details
		try {
			transactionDetail=bankAccountService.withDrawAmount(account,withDrawAmt);
		} catch (InsufficientBalanceException | OutOfCashException | MinimumWithdrawLimitException e) {
			e.printStackTrace();
		} 
		Assert.assertEquals(withDrawAmt,transactionDetail.getAmount());
		Assert.assertEquals(description,transactionDetail.getDescription());
	}

}
