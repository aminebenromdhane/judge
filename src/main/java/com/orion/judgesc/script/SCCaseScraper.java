package com.orion.judgesc.script;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.orion.judgesc.domain.Action;
import com.orion.judgesc.domain.Case;
import com.orion.judgesc.domain.Document;
import com.orion.judgesc.domain.Event;
import com.orion.judgesc.domain.Participant;
import com.orion.judgesc.scraper.component.ScraperUrl;
import com.orion.judgesc.scraper.component.SimpleScraperV2;
import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;
import com.orion.judgesc.service.DocumentService;
import com.orion.judgesc.service.EventService;
import com.orion.judgesc.service.ParticipantService;

public class SCCaseScraper {
	
	private CaseService caseService;
	
	private ActionService actionService;
	
	private DocumentService documentService;

	private EventService eventService;

	private ParticipantService participantService;
	
	private String caseLink;
	
	public SCCaseScraper(CaseService caseService,ActionService actionService,
			DocumentService documentService,EventService eventService,
			ParticipantService participantService){
		this.caseService = caseService;
		this.actionService = actionService;
		this.documentService = documentService;
		this.eventService = eventService;
		this.participantService = participantService;
	}
	public void setCaseLink(String link){
		this.caseLink = link;
	}
	public void scrap(){
		try{
			int sourceKey = Integer.parseInt(this.caseLink.substring(this.caseLink.indexOf("SourceKey%3D")+12,this.caseLink.indexOf("&b=20")));
			if(this.caseService.getCase(sourceKey) == null){
				ScraperUrl baseUrl = new ScraperUrl("https://services.saccourt.ca.gov"+this.caseLink);
				SimpleScraperV2 caseScraper = new SimpleScraperV2(baseUrl,"caseScraper.xml");
				caseScraper.scrap();
				if(caseScraper.getScrappedData().size() > 0){
					saveData(caseScraper.getScrappedData());
				}	
			}
		}catch(Exception e){
			String number = this.caseLink.substring(this.caseLink.indexOf("SourceKey%3D")+12,this.caseLink.indexOf("&b=20")).replace("%2520","");
			if(this.caseService.getCaseByNumber(number) == null){
				ScraperUrl baseUrl = new ScraperUrl("https://services.saccourt.ca.gov"+this.caseLink);
				SimpleScraperV2 caseScraper = new SimpleScraperV2(baseUrl,"caseScraper.xml");
				caseScraper.scrap();
				if(caseScraper.getScrappedData().size() > 0){
					saveData(caseScraper.getScrappedData());
				}	
			}
		}
		
		
	}
	private void saveData(List<ArrayList<Object>> allObjects){
		Case newCase = (Case)getObjects(allObjects,Case.class).get(0);
		if(this.caseService.getCaseByNumber(newCase.getNumber()) != null)
			return;
		this.caseService.addCase(newCase);
		List<Object> events = this.getObjects(allObjects, Event.class);
		if(events != null){
			for(Object event : events){
				if(((Event)event).getDate() != null && ((Event)event).getType() != null){
					((Event)event).setCaseId(newCase.getId());
					this.eventService.addEvent((Event)event);
				}
			}
		}
		List<Object> participants = this.getObjects(allObjects, Participant.class);
		if(participants != null){
			for(Object participant : participants){
				if(((Participant)participant).getName() != null){
					((Participant)participant).setCaseId(newCase.getId());
					this.participantService.addParticipant((Participant)participant);
				}
			}
		}
		
		List<Object> actions = this.getObjects(allObjects, Action.class);
		if(actions != null){
			for(Object action : actions){
				Action newAction = ((Action)action);
				if(newAction.getEntry() != null){
					newAction.setCaseId(newCase.getId());
					this.actionService.addAction(newAction);
					/*if(newAction.getDocument() != null){
						Document document = new Document();
						document.setActionId(newAction.getId());
						document.setUrl("https://services.saccourt.ca.gov"+newAction.getDocument());
						document.setContent(getDocumentContent(document.getUrl()));
						this.documentService.addDocument(document);
					}*/
				}
			}
		}
	}
	
	private String getDocumentContent(String url){
		try {
			String content = "";
			PdfReader reader = new PdfReader(new URL(url));
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			TextExtractionStrategy strategy;
	        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
	            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
	            content += strategy.getResultantText();
	        }
	        return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ArrayList<Object> getObjects(List<ArrayList<Object>> allObjects,Class typeOfObject){
		for(ArrayList<Object> objects : allObjects){
			if(objects.size() ==  0){
				return null; 
			}else if(objects.get(0).getClass() == typeOfObject){
				return objects;
			}
		}
		return null;
	}
	
	
}
