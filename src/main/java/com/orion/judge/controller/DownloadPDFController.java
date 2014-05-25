package com.orion.judge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orion.judge.scripts.SFScraperPDF;
import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.DocumentService;


@RestController
public class DownloadPDFController {
	
	@Autowired
	private CivilCaseService civilCaseService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private ActionCaseService actionCaseService;
	
	@RequestMapping(value = "downloadpdf/start",method = RequestMethod.GET)
	public void start(@RequestParam(value = "numberThread") int numberThread) {
		
		SFScraperPDF downloader = new SFScraperPDF(actionCaseService,civilCaseService,numberThread);
		downloader.download();
 
	}
}


