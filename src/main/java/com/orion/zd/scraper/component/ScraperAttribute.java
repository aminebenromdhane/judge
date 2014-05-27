package com.orion.zd.scraper.component;

public class ScraperAttribute {
	
	private String name;
	private String path;
	private String typeValue;
	
	public ScraperAttribute(String name,String path){
		this.name = name;
		this.path = path;
		this.typeValue = "text";
	}
	
	public void addParam(String paramName,String paramValue){
		if(paramValue == null)
			return;
		if(paramValue.equals("typeValue")){
			this.typeValue = paramValue;
		}
	}
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	
	
}
