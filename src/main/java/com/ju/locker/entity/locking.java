/**
 * 
 */
package com.ju.locker.entity;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "locking")
public class locking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "owner_name")
	private String ownerName;
	
	@Column(name = "phone_no")
	private String   phoneNo;
	
	@Column(name = "otp")
	private String   otp;
	
	@Column(name = "no_items")
	private String noItems;
	
	@Column(name = "lock_in")
	private LocalDateTime   lockIn;
	
	@Column(name = "check_out")
	private LocalDateTime   checkOut;
	
	//@Column(name = "park_area")
	//private String parkArea;
	

	
	@Column(name = "amount")
	private long   amount;
	
	@Column(name = "status")
	private boolean status;
	
	public locking() {
		
	}

	public locking(String ownerName, String phoneNo, String noItems, LocalDateTime lockIn,LocalDateTime checkOut,boolean status,
			 long amount) {
		this.ownerName = ownerName;
		this.phoneNo = phoneNo;
		this.noItems = noItems;
		this.lockIn = lockIn;
		this.checkOut = checkOut;
		this.status = status;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getownerName() {
		return ownerName;
	}

	public void setownerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getphoneNo() {
		return phoneNo;
	}

	public void setphoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getotp() {
		return otp;
	}

	public void setotp(String otp) {
		this.otp = otp;
	}

	public String getnoItems() {
		return noItems;
	}

	public void setnoItems(String noItems) {
		this.noItems = noItems;
	}

	public LocalDateTime getlockIn() {
		return lockIn;
	}

	public void setlockIn(LocalDateTime lockIn) {
		this.lockIn = lockIn;
	}

	public LocalDateTime getcheckOut() {
		return checkOut;
	}

	public void setcheckOut(LocalDateTime checkOut) {
		this.checkOut = checkOut;
	}

	//public String getParkArea() {
	//	return parkArea;
	//}

	//public void setParkArea(String parkArea) {
	//	this.parkArea = parkArea;
	//}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Parking [id=" + id + ", ownerName=" + ownerName + ", PhoneNo=" + phoneNo + ", noItems=" + noItems
				+ ", lockIn=" + lockIn + ", checkOut=" + checkOut + ", amount=" + amount + ", status=" + status + "]";
	}

	/*@Override
	public String toString() {
		return "Parking [id=" + id + ", parkerName=" + parkerName + ", parkerPhoneNo=" + parkerPhoneNo
				+ ", plateNumber=" + plateNumber + ", parkIn=" + parkIn + ", parkOut=" + parkOut + ", parkArea="
				+ parkArea + ", amount=" + amount + ", status=" + status + "]";
	}*/
	
	
	
}
