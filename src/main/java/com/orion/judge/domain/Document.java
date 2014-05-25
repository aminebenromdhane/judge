package com.orion.judge.domain;

import java.util.Date;

public class Document {
	
	private int id;
	private String url;
	private String content;
	private int actionCaseID;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getActionCaseID() {
		return actionCaseID;
	}
	public void setActionCaseID(int actionCaseID) {
		this.actionCaseID = actionCaseID;
	}
	
	
	
}
