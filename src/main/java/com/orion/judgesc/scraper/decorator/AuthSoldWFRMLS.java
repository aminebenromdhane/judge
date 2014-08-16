package com.orion.judgesc.scraper.decorator;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.orion.judgesc.scraper.component.Scraper;
import com.orion.judgesc.scraper.component.ScraperUrl;

public class AuthSoldWFRMLS extends AuthentificationWFRMLS{

	public AuthSoldWFRMLS(Scraper scraper) {
		super(scraper);
	}

	@Override
	protected void initSearch() {
		//System.out.println("Auth initSearch");
		System.out.println(this.sessionCookies);
		ScraperUrl refreshUrl = new ScraperUrl("http://www.utahrealestate.com/search/form/");
		ScraperUrl searchUrl = new ScraperUrl("http://www.utahrealestate.com/search/chained.update/count/true/criteria/true");
		refreshUrl.setCookies(this.sessionCookies);
		searchUrl.setCookies(this.sessionCookies);
		refreshUrl.setGetMethod(false);
		searchUrl.setGetMethod(false);
		searchUrl.setJson(true);
		try {
			refreshUrl.connect().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		searchUrl.addDataToSend("param", "status");
		searchUrl.addDataToSend("value", "8");
		searchUrl.addDataToSend("chain", "criteriaAndCountAction");
		searchUrl.addDataToSend("op", "4");
		searchUrl.addDataToSend("advanced_search", "1");
		try {
			searchUrl.connect().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		searchUrl.addDataToSend("param", "historical_data");
		searchUrl.addDataToSend("value", "1");
		searchUrl.addDataToSend("chain", "criteriaAndCountAction");
		searchUrl.addDataToSend("op", "1");
		searchUrl.addDataToSend("advanced_search", "1");
		try {
			searchUrl.connect().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*protected void initSearch() {
		System.out.println("Auth initSearch");
		
		ScraperUrl refreshUrl = new ScraperUrl("http://www.utahrealestate.com/search/form/");
		ScraperUrl searchUrl = new ScraperUrl("http://www.utahrealestate.com/search/chained.update/count/true/criteria/true");
		refreshUrl.setCookies(this.sessionCookies);
		searchUrl.setCookies(this.sessionCookies);
		try {
			refreshUrl.connect().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		searchUrl.addDataToSend("param", "status");
		searchUrl.addDataToSend("value", "8");
		searchUrl.addDataToSend("chain", "criteriaAndCountAction");
		searchUrl.addDataToSend("op", "4");
		searchUrl.addDataToSend("advanced_search", "1");
		try {
			searchUrl.connect().execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
