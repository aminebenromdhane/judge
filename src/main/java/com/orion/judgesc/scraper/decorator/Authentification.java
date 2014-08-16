package com.orion.judgesc.scraper.decorator;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.orion.judgesc.scraper.component.Scraper;
import com.orion.judgesc.scraper.component.ScraperUrl;

public class Authentification extends ScraperDecorator{

	private ScraperUrl urlToConnect;
	protected Map<String,String> sessionCookies;
	private boolean authetificated;
	
	public Authentification(Scraper scraper) {
		super(scraper);
		authetificated = false;
		sessionCookies = null;
	}

	@Override
	public Document connect(ScraperUrl url) {
		if(!this.authetificated){
			authentificate();
			url.setCookies(this.sessionCookies);
		}else{
			url.setCookies(this.sessionCookies);
		}
		return scraper.connect(url);
	}
	
	protected void authentificate(){
		try {
			this.urlToConnect.setJson(true).connect().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		authetificated = true;
		
	}
	
	@Override
	public void config() {
		//System.out.println("Auth config");
		this.scraper.config();
		String number="";
		Response firstUrl = null;
		try {
			firstUrl = new ScraperUrl("http://www.utahrealestate.com/auth/login.form/").setJson(true).connect().execute();
			sessionCookies = firstUrl.cookies();
			number = firstUrl.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.urlToConnect = new ScraperUrl(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("url"));
		urlToConnect.addDataToSend(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChild("username").getAttributeValue("var")+"_"+number, 
				this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("username"));		
		urlToConnect.addDataToSend(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChild("password").getAttributeValue("var")+"_"+number, 
				this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("password"));
		urlToConnect.setGetMethod(false);
		urlToConnect.setCookies(sessionCookies);
	}
}
