package com.orion.judgesc.domain;

public class Document {

	private int id;
	private int numberPages;
	private String url;
	private String content;
	private int actionId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumberPages() {
		return numberPages;
	}
	public void setNumberPages(int numberPages) {
		this.numberPages = numberPages;
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
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	@Override
	public String toString() {
		return "Document [id=" + id + ", numberPages=" + numberPages + ", url="
				+ url + ", content=" + content + ", actionId=" + actionId + "]";
	}
	
	
}
