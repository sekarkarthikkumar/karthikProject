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
@Table(name="ATM_CASH_DETAILS")
public class AtmCashDetails {
	
	public AtmCashDetails() {
		//default constructor
	}
	 public AtmCashDetails(Integer notesValue, Integer notesVolume) {
	   this.notesValue=notesValue;
	   this.notesVolume =notesVolume;	
	}
	 
	@Id
	 private int notesValue;
	 private int notesVolume;
	
}
