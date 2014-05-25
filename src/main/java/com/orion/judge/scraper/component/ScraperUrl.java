package com.orion.judge.scraper.component;

import java.util.Hashtable;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;

public class ScraperUrl {
	private String url;
	private String referrer;
	private boolean getMethod;
	private Hashtable<String,String> dataToSend;
	private Map<String,String> cookies;
	private boolean isJson;
	private boolean isSilent;
	
	public ScraperUrl(String url){
		this.url = url;
		this.dataToSend = new Hashtable<String,String>();
		cookies = new Hashtable<String,String>();
		this.getMethod = true;
		this.isJson = false;
		this.isSilent = false;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public boolean isGetMethod() {
		return getMethod;
	}

	public void setGetMethod(boolean getMethod) {
		this.getMethod = getMethod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addDataToSend(String key,String value){
		this.dataToSend.put(key,value);
	}
	
	public Hashtable<String,String> getDataToSend(){
		return this.dataToSend;
	}

	public Map<String,String> getCookie() {
		return cookies;
	}

	public ScraperUrl setCookies(Map<String,String> cookies) {
		this.cookies = cookies;
		return this;
	}
	
	public void setJson(boolean isJson) {
		this.isJson = isJson;
	}

	public ScraperUrl setSilent(){
		this.isSilent = true;
		return this;
	}
		
	public Connection connect(){
		if(!this.isSilent){
			System.out.println(this.url);
		}
		Connection connection = Jsoup.connect(this.url).timeout(60000);
		connection.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
		if(this.dataToSend != null){
			connection.data(this.dataToSend);
		}
		if(this.referrer != null){
			connection.referrer(this.referrer);
		}
		if(this.cookies != null){
			connection.cookies(cookies);
		}
		if(this.isGetMethod()){
			connection.method(Method.GET);
		}else{
			connection.method(Method.POST);
		}
		if(this.isJson){
			connection.ignoreContentType(true);
		}
		return connection;
	}
}
