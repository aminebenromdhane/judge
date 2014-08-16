package com.orion.judgesc.scraper.component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ScraperObject {
	
	private Class objClass;
	private Class filterClass;
	private Object filterInstance;
	private List<ScraperAttribute> attributes;
	private String include;
	
	public ScraperObject(Class objClass,Class filterClass,String include){
		this.objClass = objClass;
		this.filterClass = filterClass;
		try {
			this.filterInstance = filterClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.attributes = new ArrayList<ScraperAttribute>();
		if(include != null){
			this.include = include;
		}
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
	
	public Object setAttribute(Object instance,ScraperAttribute attribute,String data){
		
		try {
			this.objClass.
			getMethod(attribute.getSetterMethod(), this.getAttributeType(attribute)).
			invoke(instance, this.filterClass.
					getMethod(attribute.getName(), String.class).
					invoke(this.filterInstance, data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return instance;
	}
	
	public Class getAttributeType(ScraperAttribute attribute){
		try {
			return this.getObjClass().getDeclaredField(attribute.getName()).getType();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
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
	
	@Override
	public String toString(){
		return this.objClass+"\n"+this.attributes;
	}
}
