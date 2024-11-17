package com.ju.locker.controller;


import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ju.locker.entity.locking;
import com.ju.locker.repository.lockingRepository;
import com.ju.locker.utils.*;




@RestController
@RequestMapping("/api")


public class ApiController {
	
	@Autowired
	lockingRepository lockingRepository;
	
	@Autowired
	sms Sms;
	
	@GetMapping("/store")
	public ResponseEntity<String> getAllItem() {
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@GetMapping("/locking/status")
	public ResponseEntity<List<locking>> findBylockeded() {
		try {
			List<locking> locking = lockingRepository.findBystatus(false);

			if (locking.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(locking, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		//return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	@GetMapping("/lockings")
	public ResponseEntity<List<locking>> getAlllocking(@RequestParam(required = false) String phoneNo) {
		try {
			System.out.print("sssssss: " + phoneNo);
			List<locking> locking = new ArrayList<locking>();

			if (phoneNo == null)
				lockingRepository.findAll().forEach(locking::add);
			else
				lockingRepository.findByphoneNoContaining(phoneNo).forEach(locking::add);

			if (locking.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(locking, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/lockings/otp")
	public ResponseEntity<locking> getAlllockingotp(@RequestParam(required = false) String phoneNo) {
		try {
			//System.out.print("sssssss: " + phoneNo);
			locking _locking=null;

			if (phoneNo == "") {
				System.out.print("nullllll: " + phoneNo);
			}
				//lockingRepository.findAll().forEach(locking::add);
			else {
				
				System.out.println("valll: " + phoneNo);
				_locking=lockingRepository.findFirstByPhoneNo(phoneNo);
				String otp = Genotp.generateOTP();
				
				String message;
				message="Dear " + _locking.getownerName();
				
				message+=", Your Locker varification code is : " + otp;
				System.out.println("Generated message: " + message);
				System.out.println("Generated phn: " + _locking.getphoneNo());
				System.out.println("Generated OTP: " + otp);
				Sms.sendSms(_locking.getphoneNo(), message);
				System.out.println("sent OTP: " + otp);
				_locking.setotp(otp);
				_locking=lockingRepository.save(_locking);
				System.out.print("otp: " + _locking.getotp());
				
				}
			if (_locking==null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_locking, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/lockings")
	public ResponseEntity<locking> createParking(@RequestBody locking locking) {
		String message;
		
		try {
			LocalDateTime currentTime = LocalDateTime.now();
			long lockingfee = 20;
			locking _locking = lockingRepository.save(new locking(locking.getownerName(), locking.getphoneNo(),
					locking.getnoItems(), currentTime, null, false, lockingfee));
			message="Dear " + locking.getownerName();
			message+=", You have locked " + locking.getnoItems() + " items at this time: ";
			message+=currentTime;
			message+=" Status: Locked Amount: 20tk Thank you for using our locker service!";
			Sms.sendSms(locking.getphoneNo(), message);
			return new ResponseEntity<>(_locking, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/searchbyid/{id}")
	  public ResponseEntity<locking> getlockingById(@PathVariable("id") long id) {
	    Optional<locking> lockingData = lockingRepository.findById(id);

	    if (lockingData.isPresent()) {
	      return new ResponseEntity<>(lockingData.get(), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
	  
	  @GetMapping("/locking/{phoneNo}")
		public ResponseEntity<List<locking>> findByphoneNo(@PathVariable("phoneNo") String phoneNo,
				@RequestBody locking locking) {
			try {
				List<locking> lockings = lockingRepository.findByphoneNo(phoneNo);
				
				if (lockings.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}

				return new ResponseEntity<>(lockings, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
		}
	  
	  @PutMapping("/checkout/{id}/{OTP}")
		public ResponseEntity<locking> checkout(@PathVariable("id") long id,@PathVariable("OTP") String OTP) {
			//System.out.println("submit otp: "+ OTP);
			Optional<locking> lockingData = lockingRepository.findById(id);
			LocalDateTime currentTime = LocalDateTime.now();
			
			if (lockingData.isPresent()) {
				locking _locking = lockingData.get();

				_locking.setcheckOut(currentTime);
				_locking.setStatus(true);
				//_parking.setAmount(costAmount);
				if(_locking.getotp().equals(OTP)) {
					System.out.println("matched otp: "+ _locking.getotp());
					return new ResponseEntity<>(lockingRepository.save(_locking), HttpStatus.OK);
				}else {
					System.out.println("not match otp: "+ OTP);
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		
		@PutMapping("/lockings/{id}")
		public ResponseEntity<locking> updateLocking(@PathVariable("id") long Eid, @RequestBody locking parking) {
			
			System.out.println("edit:" + parking.getownerName());
			Optional<locking> parkingData = lockingRepository.findById(Eid);

			if (parkingData.isPresent()) {
				locking _parking = parkingData.get();
				
				_parking.setnoItems(parking.getnoItems());
				_parking.setownerName(parking.getownerName());
				_parking.setphoneNo(parking.getphoneNo());
				

				return new ResponseEntity<>(lockingRepository.save(_parking), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}

}
