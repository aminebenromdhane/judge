package com.orion.judgesc.scraper.component;

public class ScraperField {
	private String key;
	private String path;
	private String typeOfValue;
	private String pathInit;
	
	public ScraperField(String key,String path){
		this.key = key;
		this.path = path;
		this.typeOfValue = "text";
		this.pathInit = null;
	}
	public void setTypeOfValue(String typeOfValue) {
		this.typeOfValue = typeOfValue;
	}
	
	public String getKey() {
		return key;
	}
	public String getPath() {
		return path;
	}
	public String getTypeOfValue() {
		return typeOfValue;
	}
	public String getPathInit() {
		return pathInit;
	}
	public void setPathInit(String pathInit) {
		this.pathInit = pathInit;
	}
	
}
