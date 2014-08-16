package com.orion.judgesc.domain;

import java.sql.Time;
import java.util.Date;

public class Event {

	private int id;
	private Date date;
	private Time time;
	private String type;
	private String department;
	private String status;
	private int caseId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", date=" + date + ", time=" + time
				+ ", type=" + type + ", department=" + department + ", status="
				+ status + ", caseId=" + caseId + "]";
	}
	
	
}
