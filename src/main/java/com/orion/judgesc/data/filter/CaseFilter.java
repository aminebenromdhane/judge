package com.orion.judgesc.data.filter;

import java.util.Date;

public class CaseFilter extends DataFilter{


	public String number(String data){
		return data.trim();
	}
	
	public String type(String data){
		return data.trim();
	}
	
	public String category(String data){
		return data.trim();
	}
	
	public String title(String data){
		return data.trim();
	}
	
	public Date filedDate(String data){
		return super.dateFilter(data);
	}
	
	public int sourceKey(String data){
		return super.intFilter(data);
	}
}
