package com.orion.judge.scripts;

import java.util.ArrayList;
import java.util.Hashtable;

import com.orion.judge.scraper.component.Scraper;
import com.orion.judge.scraper.component.ScraperUrl;
import com.orion.judge.scraper.component.SimpleScraper;
import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.AttorneyService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.CourtService;
import com.orion.judge.service.JudgeService;
import com.orion.judge.service.PartieService;
import com.orion.judge.service.PaymentService;

public class SFLinkSearch extends Thread{
	
	private CivilCaseService civilCaseService;
	
	private ActionCaseService actionCaseService;
	
	private AttorneyService attorneyService;

	private PartieService partieService;

	private JudgeService judgeService;

	private CourtService courtService;

	private PaymentService paymentService;
	
	private String wordToLook;
	
	public SFLinkSearch(String wordToLook,PaymentService paymentService,JudgeService judgeService,
			CourtService courtService,PartieService partieService,AttorneyService attorneyService,
			ActionCaseService actionCaseService,CivilCaseService civilCaseService){
		this.wordToLook = wordToLook;
		this.partieService = partieService;
		this.paymentService = paymentService;
		this.actionCaseService = actionCaseService;
		this.attorneyService = attorneyService;
		this.civilCaseService = civilCaseService;
		this.courtService = courtService;
		this.judgeService = judgeService;
	}

	@Override
	public void run() {
		Scraper linkScraper = new SimpleScraper(new ScraperUrl("http://webaccess.sftc.org/Scripts/Magic94/mgrqispi94.dll"), "linksCase.xml");
        linkScraper.getUrlToScrap().addDataToSend("APPNAME", "WEB");
        linkScraper.getUrlToScrap().addDataToSend("PRGNAME", "NamePicklist");
        linkScraper.getUrlToScrap().addDataToSend("ARGUMENTS", "Name Filter {k}");
        linkScraper.getUrlToScrap().addDataToSend("Name Filter {k}", this.wordToLook);
        linkScraper.getUrlToScrap().setGetMethod(false);
        linkScraper.scrap();
        ArrayList<Hashtable<String, String>> firstLinks = linkScraper.getScrappedData();
        
        Scraper caseFilterScraper = new SimpleScraper("caseFilter.xml");
        SFCaseScraper caseScraper = new SFCaseScraper(paymentService,judgeService,courtService,
        							partieService,attorneyService,actionCaseService,civilCaseService);
        int i=0;
        for (Hashtable person : firstLinks) {
        	caseFilterScraper.setUrlToScrap(new ScraperUrl("http://webaccess.sftc.org" + (String)person.get("link")));
        	caseFilterScraper.scrap();
        	ArrayList<String> caseNumbers = new ArrayList<String>();
        	for (Hashtable caseFilter : caseFilterScraper.getScrappedData()) {
        		caseNumbers.add(((String)caseFilter.get("case")).replace("-", ""));
        	}
        	if(!caseNumbers.isEmpty()){
        		for(String caseNumber:caseNumbers){
        			caseScraper.setCaseNumber(caseNumber);
        			caseScraper.scrap();
        		}
        	}
        	caseFilterScraper.emptyData();
        	i++;
        	System.out.println(this.wordToLook+" REPORT : "+i+"/"+firstLinks.size());
        }
        System.out.println("FINISH : "+this.wordToLook);
	}
}
