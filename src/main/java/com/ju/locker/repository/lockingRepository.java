package com.ju.locker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ju.locker.entity.locking;

@Repository 
public interface lockingRepository extends JpaRepository <locking,Long>{
	
	
	List<locking> findByphoneNoContaining(String PhoneNo);
	List<locking> findBystatus(boolean status);
	//List<locking> findByPhone(String Phone);
	List<locking> findByphoneNo(String phoneNo);
	locking findFirstByPhoneNo(String phoneNo);
	
	
	
}	
