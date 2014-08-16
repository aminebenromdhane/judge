package com.orion.judgesc.data.filter;

import java.sql.Time;
import java.util.Date;


public class EventFilter extends DataFilter{

	public String type(String data){
		return data.trim();
	}
	
	public String department(String data){
		return data.trim();
	}
	
	public String status(String data){
		return data.trim();
	}
	
	public Date date(String data){
		return super.dateFilter(data);
	}
		
	public Time time(String data){
		try{
			return Time.valueOf(data);
		}catch(Exception e){
			return null;
		}
	}
	
}
