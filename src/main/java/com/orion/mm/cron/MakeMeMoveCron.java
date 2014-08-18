package com.orion.mm.cron;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.orion.mm.service.ListingService;
@Component
@Configuration
@EnableScheduling
public class MakeMeMoveCron {
	
	@Autowired
	private ListingService caseService;
	
	@Scheduled(fixedDelay=60000)
	private void job2012(){
	}
	
}