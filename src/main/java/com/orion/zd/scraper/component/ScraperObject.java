package com.orion.zd.scraper.component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ScraperObject {
	
	private Class objClass;
	private List<ScraperAttribute> attributes;
	private String include;
	
	public ScraperObject(Class objClass,String include){
		this.objClass = objClass;
		this.attributes = new ArrayList<ScraperAttribute>();
		this.include = include;
	}
	
	public void addAttribute(ScraperAttribute newAttribute){
		if(this.attributeBelongs(newAttribute.getName())){
			this.attributes.add(newAttribute);
		}else{
			System.out.println("ERROR : "+newAttribute.getName()+" don't belong to "+this.objClass);
		}
	}
	
	private boolean attributeBelongs(String attributeName){
		for(Field field : this.objClass.getDeclaredFields()){
			if(field.getName().equals(attributeName)){
				return true;
			}
		}
		return false;
	}

	public Class getObjClass() {
		return objClass;
	}

	public void setObjClass(Class objClass) {
		this.objClass = objClass;
	}

	public List<ScraperAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ScraperAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}
}
