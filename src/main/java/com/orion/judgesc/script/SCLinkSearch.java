package com.orion.judgesc.script;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;

import com.orion.judgesc.scraper.component.Scraper;
import com.orion.judgesc.scraper.component.ScraperUrl;
import com.orion.judgesc.scraper.component.SimpleScraper;
import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;
import com.orion.judgesc.service.DocumentService;
import com.orion.judgesc.service.EventService;
import com.orion.judgesc.service.ParticipantService;

public class SCLinkSearch extends Thread{
	
	private CaseService caseService;
	
	private ActionService actionService;
	
	private DocumentService documentService;

	private EventService eventService;

	private ParticipantService participantService;
	
	private String startDate;
	
	private String finishDate;
	
	public int size;
	
	public SCLinkSearch(String startDate,String finishDate,CaseService caseService,
			ActionService actionService,DocumentService documentService,EventService eventService,
			ParticipantService participantService){
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.caseService = caseService;
		this.actionService = actionService;
		this.documentService = documentService;
		this.eventService = eventService;
		this.participantService = participantService;
	}

	@Override
	public void run() {
		Scraper linkScraper = new SimpleScraper(new ScraperUrl("https://services.saccourt.ca.gov/PublicCaseAccess/Civil/CaseResultsByFilingDate?FilingDateBegin="+this.startDate+"&FilingDateEnd="+this.finishDate), "linksCase.xml");
        linkScraper.scrap();
        ArrayList<Hashtable<String, String>> firstLinks = linkScraper.getScrappedData();
        
        SCCaseScraper caseScraper = new SCCaseScraper(caseService,actionService,documentService,eventService,participantService);
        int i = 0;
        size=firstLinks.size();
        for (Hashtable link : firstLinks) {
        	caseScraper.setCaseLink((String)link.get("link"));
        	caseScraper.scrap();
			i++;
			//System.out.println(i+"/"+firstLinks.size());
        }
    }
	
	public String getFinishDate(){
		return this.getFinishDate();
	}
}
