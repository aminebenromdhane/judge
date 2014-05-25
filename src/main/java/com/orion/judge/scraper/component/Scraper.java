package com.orion.judge.scraper.component;

import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.nodes.Document;

public interface Scraper {

	public void scrap();
	
	public Document connect(ScraperUrl url);
	
	public void config();
	
	public org.jdom2.Document getConfigFile();
	
	public ScraperUrl getUrlToScrap();
	
	public void setUrlToScrap(ScraperUrl urlToScrap);
	
	public Document readData(Document doc);
	
	public ArrayList<Hashtable<String,String>> getScrappedData();
	
	public Scraper getThisScraper();

	public void setThisScraper(Scraper thisScraper);
	
	public void setCountData(int countData);
	
	public int getCountData();

	void setConfigFileName(String configFileName);

	void emptyData();
}
