package com.orion.judgesc.cron;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.orion.judgesc.script.SCNewScraperPDF;
import com.orion.judgesc.script.SCScraper;
import com.orion.judgesc.script.SCScraperPDF;
import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;
import com.orion.judgesc.service.DocumentService;
import com.orion.judgesc.service.EventService;
import com.orion.judgesc.service.ParticipantService;

@Component
@Configuration
@EnableScheduling
public class SCCron {
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private ActionService actionService;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private EventService eventService;
	@Autowired
	private ParticipantService participantService;
	
	@Scheduled(fixedDelay=60000)
	private void job2012(){
		/*for(int i=2014;i>1990;i--){
			execute(i);
		}*/
		System.setProperty("jsse.enableSNIExtension", "false");
		download();
	}
	
	//@Scheduled(fixedDelay=60000)
	private void download(){
		SCNewScraperPDF pdfScraper = new SCNewScraperPDF(caseService,actionService,20);
		pdfScraper.download();
	}
	
	private void execute(int year){
		SCScraper scraper = new SCScraper(year,caseService,actionService,documentService,eventService,participantService);
		scraper.scrap();
	}
	
}