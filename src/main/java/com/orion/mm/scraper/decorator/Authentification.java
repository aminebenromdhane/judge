package com.orion.mm.scraper.decorator;

import java.io.IOException;
import java.util.Map;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.orion.mm.scraper.component.Scraper;
import com.orion.mm.scraper.component.ScraperUrl;

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
		Response connexion = null;
		try {
			connexion = this.urlToConnect.setJson(true).connect().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.sessionCookies = connexion.cookies();
		authetificated = true;
		
	}
	
	@Override
	public void config() {
		this.scraper.config();
		this.urlToConnect = new ScraperUrl(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("url"));
		urlToConnect.addDataToSend(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChild("username").getAttributeValue("var"), 
				this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("username"));		
		urlToConnect.addDataToSend(this.scraper.getConfigFile().getRootElement().getChild("connexion").getChild("password").getAttributeValue("var"), 
				this.scraper.getConfigFile().getRootElement().getChild("connexion").getChildText("password"));
		urlToConnect.setGetMethod(false);
		urlToConnect.setCookies(sessionCookies);
	}
}
