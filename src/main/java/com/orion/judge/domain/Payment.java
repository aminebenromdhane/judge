package com.orion.judge.domain;

import java.util.Date;

public class Payment {
	
    private int id;
    private int civilCaseID;
    private Date date;
    private String amount;
    private String type;
    private String receiptNumber;
    private String reason;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCivilCaseID() {
		return civilCaseID;
	}
	public void setCivilCaseID(int civilCaseID) {
		this.civilCaseID = civilCaseID;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReceiptNumber() {
		return receiptNumber;
	}
	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
    
}
