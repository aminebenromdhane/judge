package com.orion.judge.scripts;

import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.AttorneyService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.CourtService;
import com.orion.judge.service.JudgeService;
import com.orion.judge.service.PartieService;
import com.orion.judge.service.PaymentService;

public class SFScraper {
	
	private CivilCaseService civilCaseService;
	
	private ActionCaseService actionCaseService;
	
	private AttorneyService attorneyService;

	private PartieService partieService;

	private JudgeService judgeService;

	private CourtService courtService;

	private PaymentService paymentService;
	
	private char charToLook;
	
	public SFScraper(char charToLook,PaymentService paymentService,JudgeService judgeService,
			CourtService courtService,PartieService partieService,AttorneyService attorneyService,
			ActionCaseService actionCaseService,CivilCaseService civilCaseService){
		this.charToLook = charToLook;
		this.partieService = partieService;
		this.paymentService = paymentService;
		this.actionCaseService = actionCaseService;
		this.attorneyService = attorneyService;
		this.civilCaseService = civilCaseService;
		this.courtService = courtService;
		this.judgeService = judgeService;
	}
	
	public void scrap(){
		int totalThread = 26;
		int maxThread = 26;
		int currentThreadStart = 0;
		do{
			SFLinkSearch[] myThreads = new SFLinkSearch[Math.min(maxThread, totalThread-currentThreadStart)];
			for(int i=65+currentThreadStart;i<65+Math.min(maxThread, totalThread-currentThreadStart);i++){
				myThreads[i-(65+currentThreadStart)] = new SFLinkSearch((Character.toString(charToLook)+Character.toString((char)i)),
						paymentService,judgeService,courtService,partieService,attorneyService,
						actionCaseService,civilCaseService);
				myThreads[i-(65+currentThreadStart)].start();
			}
			for(int i=65+currentThreadStart;i<65+Math.min(maxThread, totalThread-currentThreadStart);i++){
				try {
					myThreads[i-(65+currentThreadStart)].join();
					//System.out.println("Thread "+(i-(65+currentThreadStart))+" stopped");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			currentThreadStart+=maxThread;
		}while(currentThreadStart < totalThread);
	}
}
