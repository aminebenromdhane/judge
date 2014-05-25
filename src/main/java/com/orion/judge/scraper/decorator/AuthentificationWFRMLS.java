package com.orion.judge.scraper.decorator;

import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import com.orion.judge.scraper.component.Scraper;
import com.orion.judge.scraper.component.ScraperUrl;

public abstract class AuthentificationWFRMLS extends Authentification{

	public AuthentificationWFRMLS(Scraper scraper) {
		super(scraper);
	}
	
	@Override
	public void authentificate(){
		super.authentificate();
		this.initSearch();
	}
	
	protected abstract void initSearch();

}
