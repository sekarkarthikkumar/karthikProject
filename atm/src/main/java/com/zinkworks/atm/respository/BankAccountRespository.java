package com.zinkworks.atm.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zinkworks.atm.model.UserAccount;

@Repository
public interface BankAccountRespository extends JpaRepository<UserAccount, Long>{


	UserAccount findByAccountNumberAndPin(long accountNumber,int pin);

	UserAccount findByAccountNumber(long account);

	

}
