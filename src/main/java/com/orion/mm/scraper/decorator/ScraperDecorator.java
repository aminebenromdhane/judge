package com.orion.mm.scraper.decorator;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import com.orion.mm.scraper.component.Scraper;
import com.orion.mm.scraper.component.ScraperUrl;

public abstract class ScraperDecorator implements Scraper{
	
	protected Scraper scraper;
	
	public ScraperDecorator(Scraper scraper){
		this.scraper = scraper;
		scraper.setThisScraper(this);
	}

	@Override
	public void scrap() {
		scraper.scrap();
	}

	@Override
	public Document connect(ScraperUrl url) {
		return scraper.connect(url);
	}

	@Override
	public void config() {
		scraper.config();
	}

	@Override
	public org.jdom2.Document getConfigFile() {
		return scraper.getConfigFile();
	}

	@Override
	public ScraperUrl getUrlToScrap() {
		return scraper.getUrlToScrap();
	}
	
	@Override
	public List<ArrayList<Object>> getScrappedData() {
		return scraper.getScrappedData();
	}
	@Override
	public void setUrlToScrap(ScraperUrl urlToScrap) {
		scraper.setUrlToScrap(urlToScrap);
	}

	@Override
	public Document readData(Document doc) {
		return scraper.readData(doc);
	}

	@Override
	public Scraper getThisScraper() {
		return scraper.getThisScraper();
	}

	@Override
	public void setThisScraper(Scraper thisScraper) {
		scraper.setThisScraper(thisScraper);
	}

	@Override
	public void setCountData(int countData) {
		scraper.setCountData(countData);
	}
	
	@Override
	public int getCountData() {
		return scraper.getCountData();
	}

	@Override
	public void setConfigFileName(String configFileName) {
		scraper.setConfigFileName(configFileName);
		
	}

	@Override
	public void emptyData() {
		scraper.emptyData();
	}
}
