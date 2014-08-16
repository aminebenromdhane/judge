package com.orion.judgesc.domain;

import java.util.Date;

public class Case {
	
	private int id;
	private String number;
	private String type;
	private String category;
	private Date filedDate;
	private String title;
	private int sourceKey;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getFiledDate() {
		return filedDate;
	}
	public void setFiledDate(Date filedDate) {
		this.filedDate = filedDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getSourceKey() {
		return sourceKey;
	}
	public void setSourceKey(int sourceKey) {
		this.sourceKey = sourceKey;
	}
	@Override
	public String toString() {
		return "Case [id=" + id + ", number=" + number + ", type=" + type
				+ ", category=" + category + ", filedDate=" + filedDate
				+ ", title=" + title + "]";
	}
	
	
}
