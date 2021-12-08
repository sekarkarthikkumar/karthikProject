package com.zinkworks.atm.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zinkworks.atm.model.AtmCashDetails;

@Repository
public interface AtmCashDetailRespository extends JpaRepository<AtmCashDetails, Integer>{

	

}
