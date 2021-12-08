package com.zinkworks.atm.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetail {

	private String transactionType;
	private String description;
	private String transactionStatus;
	private Map<Integer,Integer> notesDetails ;
	private long amount;
}
