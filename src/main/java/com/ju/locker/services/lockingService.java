package com.ju.locker.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.ju.locker.repository.lockingRepository;
import com.ju.locker.entity.locking;
@Service
public class lockingService {
	@Autowired
	lockingRepository lockingRepository;
	
	public List<locking> getAlllocking(){
		List<locking> lockingList = new ArrayList<>();
		lockingRepository.findAll().forEach(lockingList::add);
		return lockingList;
	}

	

}
