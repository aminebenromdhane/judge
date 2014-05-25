package com.orion.judge.cron;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.Attorney;
import com.orion.judge.domain.CivilCase;
import com.orion.judge.domain.Court;
import com.orion.judge.domain.Judge;
import com.orion.judge.domain.Partie;
import com.orion.judge.domain.Payment;
import com.orion.judge.scraper.component.Scraper;
import com.orion.judge.scraper.component.ScraperUrl;
import com.orion.judge.scraper.component.SimpleScraper;
import com.orion.judge.scraper.component.SimpleScraperExt;
import com.orion.judge.scripts.SFScraper;
import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.AttorneyService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.CourtService;
import com.orion.judge.service.JudgeService;
import com.orion.judge.service.PartieService;
import com.orion.judge.service.PaymentService;

@Component
@Configuration
@EnableScheduling
public class CaseCron {
	
	
	@Autowired
	private CivilCaseService civilCaseService;
	
	@Autowired
	private ActionCaseService actionCaseService;
	
	@Autowired
	private AttorneyService attorneyService;
	
	@Autowired
	private PartieService partieService;
	
	@Autowired
	private JudgeService judgeService;
	
	@Autowired
	private CourtService courtService;
	
	@Autowired
	private PaymentService paymentService;
	
	private static int index1 = 0;
	private static int index2 = 1;
	
	private static int[] ftable = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}; 
	
	//@Scheduled(fixedRate=60000)
	private void execute1(){
		while(index1 <= 25){
			System.out.println("BEGIN!!!!!!!!!!!!! "+(char)ftable[index1]);
			SFScraper scraper = new SFScraper((char)ftable[index1],paymentService,judgeService,courtService,partieService,attorneyService,
					actionCaseService,civilCaseService);
			scraper.scrap();
			System.out.println("END!!!!!!!!!!!!! "+(char)ftable[index1]);
			index1+=2;
			System.out.println(index1);
		}
	}
	
	//@Scheduled(fixedRate=60000)
	private void execute2(){
		while(index2 <= 25){
			System.out.println("BEGIN!!!!!!!!!!!!! "+(char)ftable[index2]);
			SFScraper scraper = new SFScraper((char)ftable[index2],paymentService,judgeService,courtService,partieService,attorneyService,
					actionCaseService,civilCaseService);
			scraper.scrap();
			System.out.println("END!!!!!!!!!!!!! "+(char)ftable[index2]);
			index2+=2;
			System.out.println(index2);
		}
	}
	
	/*@Scheduled(fixedRate=60000)
	private void execute3(){
		if(index3 > 25)
			return;
		SFScraper scraper = new SFScraper((char)ftable[index3],paymentService,judgeService,courtService,partieService,attorneyService,
				actionCaseService,civilCaseService);
		scraper.scrap();
		System.out.print("end "+(char)ftable[index3]);
		index3+=4;
	}
	
	@Scheduled(fixedRate=60000)
	private void execute4(){
		if(index4 > 25)
			return;
		SFScraper scraper = new SFScraper((char)ftable[index4],paymentService,judgeService,courtService,partieService,attorneyService,
				actionCaseService,civilCaseService);
		scraper.scrap();
		System.out.print("end "+(char)ftable[index4]);
		index4+=4;
	}*/
	
}
