package com.orion.judgesc.script;

import java.util.ArrayList;
import java.util.List;

import com.orion.judgesc.domain.Case;
import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;


public class SCScraperPDF {

	private CaseService caseService;
	
	private ActionService actionService;
	
	private int totalThread;
	
	public SCScraperPDF(CaseService caseService,ActionService actionService,int totalThread){
		this.caseService = caseService;
		this.actionService = actionService;
		this.totalThread = totalThread;
	}
	
	private List<List<Integer>> casesArray(int totalThreads){
		List<Integer> allCases = new ArrayList<Integer>();
		List<List<Integer>> allLists = new ArrayList<List<Integer>>();
		for(Case aCase : caseService.getAllCases()){
			allCases.add(aCase.getId());
		}
		int currentCursor=0;
		for(int i=0;i<totalThreads-1;i++){
			allLists.add(new ArrayList<Integer>());
			for(int j=0;j<(int)(allCases.size()/totalThreads);j++){
				allLists.get(i).add(allCases.get(currentCursor));
				currentCursor++;
			}
		}
		allLists.add(new ArrayList<Integer>());
		for(int i=currentCursor;i<allCases.size();i++){
			allLists.get(totalThreads-1).add(allCases.get(i));
		}
		return allLists;
	}
	public void download(){
		SCDownloadDocs2[] myThreads = new SCDownloadDocs2[totalThread];
		List<List<Integer>> cases= this.casesArray(totalThread);
		for(int i=0;i<totalThread;i++){
			myThreads[i] = new SCDownloadDocs2(actionService,caseService,cases.get(i));
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
	/*public void ocr(){
	SFOcrDocs[] myThreads = new SFOcrDocs[totalThread];
	List<List<Integer>> cases= this.casesArray(totalThread);
	for(int i=0;i<totalThread;i++){
		myThreads[i] = new SFOcrDocs(actionCaseService,documentService,cases.get(i));
		myThreads[i].start();
	}
	for(int i=0;i<totalThread;i++){
		try {
			myThreads[i].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}*/
}
