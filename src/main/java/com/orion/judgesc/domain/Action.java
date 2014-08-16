package com.orion.judgesc.domain;

import java.util.Date;

public class Action {
	
	private int id;
	private String entry;
	private Date filedDate;
	private String filedBy;
	private String document;
	private String key;
	private int caseId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Date getFiledDate() {
		return filedDate;
	}
	public void setFiledDate(Date filedDate) {
		this.filedDate = filedDate;
	}
	public String getFiledBy() {
		return filedBy;
	}
	public void setFiledBy(String filedBy) {
		this.filedBy = filedBy;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "Action [id=" + id + ", entry=" + entry + ", filedDate="
				+ filedDate + ", filedBy=" + filedBy + ", document=" + document
				+ ", caseId=" + caseId+", key=" + key + "]";
	}
	
	
}
