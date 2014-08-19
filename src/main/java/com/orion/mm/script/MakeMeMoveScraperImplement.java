package com.orion.mm.script;

import java.util.ArrayList;
import java.util.List;

import com.orion.mm.domain.Listing;
import com.orion.mm.scraper.component.Scraper;
import com.orion.mm.scraper.component.ScraperUrl;
import com.orion.mm.scraper.component.SimpleScraper;
import com.orion.mm.scraper.decorator.Authentification;
import com.orion.mm.scraper.decorator.MultiPage;
import com.orion.mm.service.ListingService;

public class MakeMeMoveScraperImplement {
	
	private ListingService listingService;
	
	public MakeMeMoveScraperImplement(ListingService listingService){
		this.listingService = listingService;
	}
	
	public void scrap(){
		// url of the main page to scraper ( the notation {{1}} means that the scraper will begin with page 1 and will increment it until max page number
		ScraperUrl url = new ScraperUrl("http://www.zillow.com/homes/make_me_move/UT/list/mmm_pt/55_rid/priced_sort/43.889975,-104.534912,34.461277,-119.476318_rect/5_zm/{{1}}_p/");
		// we create simple scraper with config file and the url
		Scraper mmScraper = new SimpleScraper(url,"listingScraper.xml");
		// add multipage decorator
		mmScraper = new MultiPage(mmScraper);
		// add authentification decorator
		mmScraper = new Authentification(mmScraper);
		mmScraper.scrap(); 
		// here is the result in a list of object we have to save these objects in db now, simple heh ?
		saveData(mmScraper.getScrappedData());
	}
	public void saveData(List<ArrayList<Object>> data){
		System.out.println(data.size());
		for(ArrayList<Object> page : data){
			for(Object obj : page){
				Listing newListing = (Listing)obj;
				listingService.addListing(newListing);
			}
		}
	}
}
