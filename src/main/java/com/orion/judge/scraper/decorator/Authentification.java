package com.orion.judge.scraper.decorator;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import com.orion.judge.scraper.component.Scraper;
import com.orion.judge.scraper.component.ScraperUrl;

public class Authentification extends ScraperDecorator{

	private ScraperUrl urlToConnect;
	protected Map<String,String> sessionCookies;
	
	public Authentification(Scraper scraper) {
		super(scraper);
		sessionCookies = null;
	}

	@Override
	public Document connect(ScraperUrl url) {
		if(this.sessionCookies == null){
			authentificate();
			url.setCookies(this.sessionCookies);
		}else{
			url.setCookies(this.sessionCookies);
		}
		return scraper.connect(url);
	}
	
	protected void authentificate(){
		try {
			this.sessionCookies = this.urlToConnect.connect().execute().cookies();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void config() {
		System.out.println("Auth config");
		this.scraper.config();
		this.urlToConnect = new ScraperUrl(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("url"));
		urlToConnect.addDataToSend(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChild("username").getAttributeValue("var"), 
				this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("username"));		
		urlToConnect.addDataToSend(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChild("password").getAttributeValue("var"), 
				this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("password"));
		if(this.scraper.getConfigFile().getRootElement().getChild("connexion").getAttributeValue("method").equals("post")){
			urlToConnect.setGetMethod(false);
		}
	}
}
