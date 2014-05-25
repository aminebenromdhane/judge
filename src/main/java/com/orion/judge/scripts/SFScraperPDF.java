package com.orion.judge.scripts;

import java.util.ArrayList;
import java.util.List;

import com.orion.judge.domain.CivilCase;
import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.AttorneyService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.CourtService;
import com.orion.judge.service.DocumentService;
import com.orion.judge.service.JudgeService;
import com.orion.judge.service.PartieService;
import com.orion.judge.service.PaymentService;

public class SFScraperPDF {

	private CivilCaseService civilCaseService;
	
	private ActionCaseService actionCaseService;
	
	private DocumentService documentService;
	
	private int totalThread;
	
	public SFScraperPDF(ActionCaseService actionCaseService,CivilCaseService civilCaseService,int totalThread){
		this.civilCaseService = civilCaseService;
		this.actionCaseService = actionCaseService;
		this.totalThread = totalThread;
	}
	
	public SFScraperPDF(ActionCaseService actionCaseService,CivilCaseService civilCaseService,DocumentService documentService,int totalThread){
		this.civilCaseService = civilCaseService;
		this.documentService = documentService;
		this.actionCaseService = actionCaseService;
		this.totalThread = totalThread;
	}
	
	private List<List<Integer>> casesArray(int totalThreads){
		List<Integer> allCases = new ArrayList<Integer>();
		List<List<Integer>> allLists = new ArrayList<List<Integer>>();
		for(CivilCase aCase : civilCaseService.getAllCivilCases()){
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
		SFDownloadDocs[] myThreads = new SFDownloadDocs[totalThread];
		List<List<Integer>> cases= this.casesArray(totalThread);
		for(int i=0;i<totalThread;i++){
			myThreads[i] = new SFDownloadDocs(actionCaseService,cases.get(i));
			myThreads[i].start();
		}
		for(int i=0;i<totalThread;i++){
			try {
				myThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	public void ocr(){
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
	
	}
}
