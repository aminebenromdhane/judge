package com.orion.judge.domain;

import java.util.Date;

public class ActionCase {
	
	private int id;
	private String proceedings;
	private String document;
	private Date date;
	private int civilCaseID;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProceedings() {
		return proceedings;
	}
	public void setProceedings(String proceedings) {
		this.proceedings = proceedings;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getCivilCaseID() {
		return civilCaseID;
	}
	public void setCivilCaseID(int civilCaseID) {
		this.civilCaseID = civilCaseID;
	}
	
	
}
