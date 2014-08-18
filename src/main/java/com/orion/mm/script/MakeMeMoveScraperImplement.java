package com.orion.mm.script;

import com.orion.mm.scraper.component.Scraper;
import com.orion.mm.scraper.component.ScraperUrl;
import com.orion.mm.scraper.component.SimpleScraper;
import com.orion.mm.scraper.decorator.MultiPage;
import com.orion.mm.service.ListingService;

public class MakeMeMoveScraperImplement {
	
	private ListingService caseService;
	
	public MakeMeMoveScraperImplement(ListingService caseService){
		this.caseService = caseService;
	}
	
	public void scrap(){
		ScraperUrl url = new ScraperUrl("http://www.zillow.com/homes/make_me_move/UT/list/mmm_pt/55_rid/43.889975,-104.534912,34.461277,-119.476318_rect/5_zm/{{1}}_p/");
		Scraper mmScraper = new SimpleScraper(url,"listingScraper.xml");
		mmScraper = new MultiPage(mmScraper);
		mmScraper.scrap();
		System.out.println(mmScraper.getScrappedData());
	}
}
