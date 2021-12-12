package com.zinkworks.atm.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zinkworks.atm.model.UserAccount;

@Repository
public interface BankAccountRespository extends JpaRepository<UserAccount, Long>{


	public UserAccount findByAccountNumberAndPin(long accountNumber,int pin);

	public UserAccount findByAccountNumber(long account);
	
	@Modifying
	@Query(value="update USER_ACCOUNT set balance_amount= ?1 where account_number= ?2 ",nativeQuery = true)
	public void updateAccountBalance(long remainingBal,long account);

	

}
