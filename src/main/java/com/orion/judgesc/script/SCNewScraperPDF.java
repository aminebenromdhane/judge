package com.orion.judgesc.script;

import java.util.ArrayList;
import java.util.List;

import com.orion.judgesc.domain.Case;
import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;


public class SCNewScraperPDF {

	private CaseService caseService;
	
	private ActionService actionService;
	
	private int totalThread;
	
	public SCNewScraperPDF(CaseService caseService,ActionService actionService,int totalThread){
		this.caseService = caseService;
		this.actionService = actionService;
		this.totalThread = totalThread;
	}
	
	private List<List<Case>> casesArray(int totalThreads){
		List<Case> allCases = new ArrayList<Case>();
		List<List<Case>> allLists = new ArrayList<List<Case>>();
		for(Case aCase : caseService.getSpecialCases()){
			allCases.add(aCase);
		}
		int currentCursor=0;
		for(int i=0;i<totalThreads-1;i++){
			allLists.add(new ArrayList<Case>());
			for(int j=0;j<(int)(allCases.size()/totalThreads);j++){
				allLists.get(i).add(allCases.get(currentCursor));
				currentCursor++;
			}
		}
		allLists.add(new ArrayList<Case>());
		for(int i=currentCursor;i<allCases.size();i++){
			allLists.get(totalThreads-1).add(allCases.get(i));
		}
		return allLists;
	}
	public void download(){
		SCDocSecuredScraper[] myThreads = new SCDocSecuredScraper[totalThread];
		List<List<Case>> cases= this.casesArray(totalThread);
		for(int i=0;i<totalThread;i++){
			myThreads[i] = new SCDocSecuredScraper(actionService,cases.get(i),i%2);
			myThreads[i].start();
		}
		for(int i=0;i<totalThread;i++){
			try {
				myThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("FINISHHHHHHHHHHHHHHH");
	}
	
}
