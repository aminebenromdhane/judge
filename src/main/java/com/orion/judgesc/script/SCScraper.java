package com.orion.judgesc.script;

import java.sql.Date;

import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;
import com.orion.judgesc.service.DocumentService;
import com.orion.judgesc.service.EventService;
import com.orion.judgesc.service.ParticipantService;

public class SCScraper {
	
	private CaseService caseService;
	
	private ActionService actionService;
	
	private DocumentService documentService;

	private EventService eventService;

	private ParticipantService participantService;
	
	private int year;
	
	public SCScraper(int year,CaseService caseService,ActionService actionService,
			DocumentService documentService,EventService eventService,
			ParticipantService participantService){
		this.year = year;
		this.caseService = caseService;
		this.actionService = actionService;
		this.documentService = documentService;
		this.eventService = eventService;
		this.participantService = participantService;
	}
	
	public void scrap(){
		int size = 0;
		int totalThread = 372;//24
		int maxThread = 20;//24
		int currentThreadStart = 0;
		int month=1;
		int j=1;
		do{
			SCLinkSearch[] myThreads = new SCLinkSearch[Math.min(maxThread, totalThread-currentThreadStart)];
			for(int i=0;i<Math.min(maxThread, totalThread-currentThreadStart);i++){
				String startDate;
				
				startDate = month+"/"+j+"/"+year;
				
				if(j == 31){
					
					startDate = month+"/31/"+year;
					month++;
					j=1;
				}else{
					j++;
				}
				System.out.println(startDate);
				myThreads[i] = new SCLinkSearch(startDate,startDate,
						caseService,actionService,documentService,eventService,participantService);
				myThreads[i].start();
			}
			for(int i=0;i<Math.min(maxThread, totalThread-currentThreadStart);i++){
				try {
					myThreads[i].join();
					size += myThreads[i].size;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			currentThreadStart+=maxThread;
		}while(currentThreadStart < totalThread);
		System.out.println(" year "+this.year+" finished with found "+size+" actions");
	}
}
