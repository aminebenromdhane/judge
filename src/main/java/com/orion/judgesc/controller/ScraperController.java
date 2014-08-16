package com.orion.judgesc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orion.judgesc.scraper.component.ScraperUrl;
import com.orion.judgesc.script.SCScraper;
import com.orion.judgesc.service.DocumentService;
import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;
import com.orion.judgesc.service.EventService;
import com.orion.judgesc.service.ParticipantService;


@RestController
public class ScraperController {
	
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

	
	@RequestMapping(value = "proxy/change",method = RequestMethod.GET)
	public void start(@RequestParam(value = "ip") String ip,@RequestParam(value = "port") String port) {
		System.setProperty("http.proxyHost", ip);
		System.setProperty("http.proxyPort", port);
	}
}


