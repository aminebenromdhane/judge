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
import com.orion.judge.scripts.SFDownloadDocs;
import com.orion.judge.scripts.SFScraper;
import com.orion.judge.scripts.SFScraperPDF;
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
public class DocumentsCron {
	
	
	@Autowired
	private CivilCaseService civilCaseService;
	
	@Autowired
	private ActionCaseService actionCaseService;
	
	
	//@Scheduled(fixedRate=60000)
	private void execute1(){
		//SFScraperPDF downloader = new SFScraperPDF(actionCaseService,civilCaseService);
		//downloader.download();
	}
	
}
