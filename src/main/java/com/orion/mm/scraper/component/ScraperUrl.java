package com.orion.mm.scraper.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	
	public ScraperUrl(String url){
		this.url = url;
		this.dataToSend = new Hashtable<String,String>();
		cookies = new Hashtable<String,String>();
		this.getMethod = true;
		this.isJson = false;
	}

	public String getReferrer() {
		return referrer;
	}

	public ScraperUrl setReferrer(String referrer) {
		this.referrer = referrer;
		return this;
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

	public ScraperUrl setUrl(String url) {
		this.url = url;
		return this;
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
	
	public ScraperUrl setJson(boolean isJson) {
		this.isJson = isJson;
		return this;
	}

	public Connection connect(){
		Connection baseConnection = Jsoup.connect("http://homefresh.info/").timeout(50000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
		Connection connection = null;
		try {
			connection = Jsoup.connect("http://homefresh.info/browse.php?u="+URLEncoder.encode(this.url,"US-ASCII")+"&b=20&f=norefer").timeout(50000);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			connection.cookies(baseConnection.execute().cookies());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Connection connection = Jsoup.connect(this.url).timeout(0);
		
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
			connection.header("X-Requested-With", "XMLHttpRequest");
			connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		}
		return connection;
	}
}
