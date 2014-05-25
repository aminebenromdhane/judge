package com.orion.judge.scraper.decorator;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.orion.judge.scraper.component.Scraper;
import com.orion.judge.scraper.component.ScraperUrl;

public class MultiPage extends ScraperDecorator{

	private int pageNumbers;
	private String pageNumbersPointer;
	
	public MultiPage(Scraper scraper) {
		super(scraper);
		pageNumbers = -1;
		
	}
	
	@Override
	public void scrap() {
		System.out.println("MultiPage Scrap");
		String url = this.scraper.getUrlToScrap().getUrl();
		int varOpenIndex = url.indexOf("{{");
		int varCloseIndex = url.indexOf("}}");
		String[] urlParts = new String[2];
		int i=0;
		try{
			i = Integer.parseInt(url.substring(varOpenIndex+2, varCloseIndex));
		}catch(Exception e){
			System.out.println(url.substring(varOpenIndex+2, varCloseIndex));
		}
		urlParts[0] = url.substring(0,varOpenIndex);
		urlParts[1] = url.substring(varCloseIndex+2);
		do
		{
			this.scraper.getUrlToScrap().setUrl(String.format(urlParts[0]+"%s"+urlParts[1],String.valueOf(i)));
			this.scraper.scrap();
			i++;
		}while(i <= pageNumbers /*&& false*/);
	}

	@Override
	public Document readData(Document doc) {
		System.out.println("MultiPage readData");
		doc = this.scraper.readData(doc);
		if(pageNumbers == -1){
			setPageNumber(doc.select(pageNumbersPointer));
		}
		return doc;
	}
	
	private void setPageNumber(Elements paginationElements){
		System.out.println("MultiPage setPageNumber");
		try{
			pageNumbers = Integer.parseInt(paginationElements.attr("value"));
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(pageNumbers);
	}
	
	@Override
	public void config() {
		System.out.println("MultiPage config");
		this.scraper.config();
		this.pageNumbersPointer = this.scraper.getConfigFile().getRootElement().getChildText("pagenumber");
	}

	
}
