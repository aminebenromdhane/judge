package com.orion.mm.data.filter;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DataFilter {
	
	protected int priceFilter(String data){
		if(!data.equals("") && data != null){
			return intFilter(data.replace("$", "").replace(",", ""));
		}
		return 0;
	}
	protected int specialPriceFilter(String data){
		if(!data.equals("") && data != null){
			int mul = 1;
			if(data.contains("K")){
				mul *= 1000;
			}else if(data.contains("M")){
				mul *= 1000*1000;
			}
			return mul * priceFilter(data);
		}
		return 0;
	}
	protected Date dateFilter(String data){
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy");
			return formatter.parse(data);
		}catch(Exception e){
			return null;
		}
	}
	protected int intFilter(String data){
		try{
			return Integer.parseInt(data);
		}catch(Exception e){
			return 0;
		}
	}
	protected double doubleFilter(String data){
		try{
			return Double.parseDouble(data);
		}catch(Exception e){
			return 0;
		}
	}
	protected float floatFilter(String data){
		try{
			return Float.parseFloat(data);
		}catch(Exception e){
			return 0;
		}
	}
	protected String middleFilter(String data,String delim1,String delim2){
		if(data != null && !data.equals("")){
			if(data.indexOf(delim1) != -1 && data.indexOf(delim2) != -1){
				return data.substring(data.indexOf(delim1)+delim1.length(), data.indexOf(delim2));
			}
		}
		return "";
	}
}
