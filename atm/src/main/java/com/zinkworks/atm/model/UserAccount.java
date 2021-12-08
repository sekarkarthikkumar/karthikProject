package com.zinkworks.atm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Getter
@Setter
@Entity
public class UserAccount {

	@Id
	private long accountNumber;
	private int pin;	
	private long balanceAmount;
	private long overdraftAmount;
}
