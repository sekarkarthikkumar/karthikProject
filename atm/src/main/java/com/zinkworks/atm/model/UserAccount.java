package com.zinkworks.atm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@Entity
@Table(name="USER_ACCOUNT")
public class UserAccount {

	@Id
	private long accountNumber;
	private int pin;	
	private long balanceAmount;
	private long overdraftAmount;
}
