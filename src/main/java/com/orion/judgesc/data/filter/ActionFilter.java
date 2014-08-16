package com.orion.judgesc.data.filter;

import java.util.Date;


public class ActionFilter extends DataFilter{

	public String entry(String data){
		return data.trim();
	}
		
	public Date filedDate(String data){
		return super.dateFilter(data);
	}
		
	public String filedBy(String data){
		return data.trim();
	}
	
	public String document(String data){
		return data.trim();
	}
	
	public String key(String data){
		return data.trim();
	}
}
