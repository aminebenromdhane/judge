package com.orion.judge.scripts;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.Attorney;
import com.orion.judge.domain.CivilCase;
import com.orion.judge.domain.Court;
import com.orion.judge.domain.Judge;
import com.orion.judge.domain.Partie;
import com.orion.judge.domain.Payment;
import com.orion.judge.scraper.component.ScraperUrl;
import com.orion.judge.scraper.component.SimpleScraperExt;
import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.AttorneyService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.CourtService;
import com.orion.judge.service.JudgeService;
import com.orion.judge.service.PartieService;
import com.orion.judge.service.PaymentService;

public class SFCaseScraper {
	
	private final String HOME_PDF = "/home/ubuntu/PDF/";
	//private final String HOME_PDF = "C:\\PDF\\";
	
	private CivilCaseService civilCaseService;
	
	private ActionCaseService actionCaseService;
	
	private AttorneyService attorneyService;

	private PartieService partieService;

	private JudgeService judgeService;

	private CourtService courtService;

	private PaymentService paymentService;
	
	private String caseNumber;
	
	public SFCaseScraper(PaymentService paymentService,JudgeService judgeService,
			CourtService courtService,PartieService partieService,AttorneyService attorneyService,
			ActionCaseService actionCaseService,CivilCaseService civilCaseService){
		this.partieService = partieService;
		this.paymentService = paymentService;
		this.actionCaseService = actionCaseService;
		this.attorneyService = attorneyService;
		this.civilCaseService = civilCaseService;
		this.courtService = courtService;
		this.judgeService = judgeService;
	}
	public void setCaseNumber(String caseNumber){
		this.caseNumber = caseNumber;
	}
	public void scrap(){
		CivilCase aCase =  civilCaseService.getCivilCase(this.caseNumber);
		if(aCase == null){
			ScraperUrl baseUrl = new ScraperUrl("http://webaccess.sftc.org/Scripts/Magic94/mgrqispi94.dll?APPNAME=WEB&PRGNAME=caseinfoscreens&ARGUMENTS=-A"+caseNumber);
			SimpleScraperExt caseScraper = new SimpleScraperExt(baseUrl,"caseScraper.xml");
			caseScraper.scrap();
			ArrayList<Hashtable<String,String>> info = caseScraper.getScrappedData();
			if(info.size() > 0 && !info.get(0).get("title").equals("Title: ") && !info.get(0).get("cause").equals("Cause of Action: ") && info.get(0).get("case") != null){
				this.searchData(info.get(0),caseScraper.getPage());	
			}
		}else{
			//this.saveDocs(aCase.getId());
		}
	}
	private void searchData(Hashtable<String,String> info,Document pagePrec){
		info.put("case", info.get("case").replace("Case Number:", "").replace(" ", "")); 
		info.put("cause", info.get("cause").replace("Cause of Action:", "").trim()); 
		info.put("title", info.get("title").replace("Title:", "").trim()); 
		CivilCase aCase =  civilCaseService.getCivilCase(info.get("case"));
		if(aCase == null){
			CivilCase myCase = new CivilCase();
			myCase.setNumber(info.get("case"));
			myCase.setTitle(info.get("title"));
			try{
				myCase.setDate(new Date(info.get("date")));
			}catch(Exception e){
				return;
			}
			myCase.setCause(info.get("cause"));
			civilCaseService.insertCivilCase(myCase);
			
			SimpleScraperExt caseActionScraper = new SimpleScraperExt("caseActionScraper.xml");
			caseActionScraper.setPage(pagePrec);
			caseActionScraper.scrap();
			
			for(Hashtable<String,String> action:caseActionScraper.getScrappedData()){
				ActionCase myAction = new ActionCase();
				myAction.setProceedings(action.get("actionProceedings"));
				myAction.setDocument(action.get("actionDocument"));
				myAction.setDate(new Date(action.get("actionDate")));
				myAction.setCivilCaseID(myCase.getId());
				actionCaseService.insertActionCase(myAction);
			}
			
			SimpleScraperExt casePartieScraper = new SimpleScraperExt(new ScraperUrl("http://webaccess.sftc.org"+info.get("linkParties")),"casePartieScraper.xml");
			casePartieScraper.scrap();
			
			for(Hashtable<String,String> partie:casePartieScraper.getScrappedData()){
				Partie myPartie = new Partie();
				myPartie.setName(partie.get("party"));
				myPartie.setType(partie.get("partyType"));
				myPartie.setAttorn(partie.get("attorneys"));
				myPartie.setFilings(partie.get("filings"));
				myPartie.setCivilCaseID(myCase.getId());
				partieService.insertPartie(myPartie);
			}

			SimpleScraperExt caseAttorneysScraper = new SimpleScraperExt(new ScraperUrl("http://webaccess.sftc.org"+info.get("linkAttorneys")),"caseAttorneysScraper.xml");
			caseAttorneysScraper.scrap();
			
			for(Hashtable<String,String> attorney:caseAttorneysScraper.getScrappedData()){
				Attorney myAttorney = new Attorney();
				myAttorney.setName(attorney.get("name"));
				myAttorney.setAddress(attorney.get("address"));
				try{
					myAttorney.setBarNumber(Long.parseLong(attorney.get("barNumber")));
				}catch(Exception e){
				}
				myAttorney.setPartiesRepresented(attorney.get("partiesRepresented"));
				myAttorney.setCivilCaseID(myCase.getId());
				attorneyService.insertAttorney(myAttorney);
			}
			
			SimpleScraperExt casePaymentsScraper = new SimpleScraperExt(new ScraperUrl("http://webaccess.sftc.org"+info.get("linkPayments")),"casePaymentScraper.xml");
			casePaymentsScraper.scrap();
			
			for(Hashtable<String,String> payment:casePaymentsScraper.getScrappedData()){
				Payment myPayment = new Payment();
				myPayment.setDate(new Date(payment.get("date")));
				myPayment.setType(payment.get("type"));
				myPayment.setReason(payment.get("reason"));
				myPayment.setReceiptNumber(payment.get("receiptNumber"));
				myPayment.setAmount(payment.get("amount"));
				myPayment.setCivilCaseID(myCase.getId());
				paymentService.insertPayment(myPayment);
			}
			
			SimpleScraperExt caseCalendarScraper = new SimpleScraperExt(new ScraperUrl("http://webaccess.sftc.org"+info.get("linkCalendar")),"caseCalendarScraper.xml");
			caseCalendarScraper.scrap();
			
			for(Hashtable<String,String> calendar:caseCalendarScraper.getScrappedData()){
				int idJ=0;
				Judge tmpJ = judgeService.getJudge(calendar.get("judge"));
				if(tmpJ == null){
					Judge myJ = new Judge();
					myJ.setName(calendar.get("judge"));
					judgeService.insertJudge(myJ);
					idJ = myJ.getId();
				}else{
					idJ = tmpJ.getId();
				}
				Court myCourt = new Court();
				myCourt.setDate(calendar.get("date"));
				myCourt.setJudgeID(idJ);
				myCourt.setLocation(calendar.get("location"));
				myCourt.setMatter(calendar.get("matter"));
				myCourt.setCivilCaseID(myCase.getId());
				courtService.insertCourt(myCourt);
			}
			//this.saveDocs(myCase.getId());
		}else{
			/*System.out.println("reload "+aCase.getId());
			this.saveDocs(aCase.getId());*/
		}
		
	}
	
}
