package com.orion.mm.data.filter;

public class ListingFilter extends DataFilter{


	public String address(String data){
		return data.trim();
	}
	
	public int price(String data){
		return super.priceFilter(data);
	}
	
	public int zestimate(String data){
		return super.priceFilter(data);
	}
	
	public int bed(String data){
		return super.intFilter(data);
	}
	
	public int bath(String data){
		return super.intFilter(data);
	}
	
	public int sqft(String data){
		return super.intFilter(data);
	}
	
	public double lot(String data){
		return super.doubleFilter(data);
	}
	
	public int yearBuilt(String data){
		return super.intFilter(data);
	}
}
