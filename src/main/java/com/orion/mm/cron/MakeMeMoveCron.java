package com.orion.mm.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.orion.mm.script.MakeMeMoveScraperImplement;
import com.orion.mm.service.ListingService;
@Component
@Configuration
@EnableScheduling
public class MakeMeMoveCron {
	
	@Autowired
	private ListingService caseService;
	
	@Scheduled(fixedDelay=60000)
	private void mmScraper(){
		System.out.println("begin");
		MakeMeMoveScraperImplement mmImpl = new MakeMeMoveScraperImplement(caseService);
		mmImpl.scrap();
	}
	
}