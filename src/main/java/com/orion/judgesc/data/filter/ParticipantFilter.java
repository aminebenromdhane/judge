package com.orion.judgesc.data.filter;


public class ParticipantFilter extends DataFilter{

	public String name(String data){
		return data.trim();
	}
		
	public String role(String data){
		return data.trim();
	}
		
	public String representer(String data){
		return data.trim();
	}
}
