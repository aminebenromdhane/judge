package com.orion.judgesc.script;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Method;

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

public class SCDocSecuredScraper extends Thread{
	
	private final String HOME_PDF = "/home/ubuntu/PDF2/";
	//private final String HOME_PDF = "PDF/";
	
	
	private ActionService actionService;
	
	private List<Case> cases;
	
	private ProxyType1 proxy;
	private int id;
	public SCDocSecuredScraper(ActionService actionService,List<Case> cases,int id){
		this.id = id;
		this.actionService = actionService;
		this.cases = cases;
		this.proxy = new ProxyType1(id);
	}
	
	public void scrap(Case caseToScrap){
		File folder = new File(HOME_PDF+caseToScrap.getId());
		System.out.println(caseToScrap.getFiledDate());
		if(!folder.exists()){
			SimpleScraperV2 caseScraper = new SimpleScraperV2("https://services.saccourt.ca.gov/PublicCaseAccess/Civil/CaseDetails?SourceSystemId=1&SourceKey="+caseToScrap.getSourceKey(),proxy,"caseScraper.xml");
			caseScraper.scrap();
			if(caseScraper.getScrappedData().size() > 0){
				getDocument(caseScraper.getScrappedData(),caseToScrap);
			}	
		}else{
			System.out.println("Searching for a case ...");
		}
	}
	
	
	private void getDocument(List<ArrayList<Object>> allObjects,Case caseToScrap){
		
		List<Object> actions = this.getObjects(allObjects, Action.class);
		if(actions != null){
			for(Object action : actions){
				if(((Action)action).getDocument() != null){
					((Action)action).setCaseId(caseToScrap.getId());
					int actionID = 0;
					
					try{
						actionID = this.actionService.getActionID((Action)action);
					}catch(Exception e){
						System.out.println("Can't find this action");
						return;
					}
					System.out.println("new action");
					String folderName = Integer.toString(caseToScrap.getId());
					(new File(HOME_PDF+Integer.toString(caseToScrap.getId()))).mkdirs();
					String fileName = actionID+".pdf";
					File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);		
					if(!thisPDF.exists()){
						this.downloadPDF(((Action)action).getKey(),((Action)action).getDocument(),fileName,folderName,actionID);
					}else{
						System.out.println("Exist here ");
					}
				}
			}
		}
	}
	
	
	private void downloadPDF(String key,String url,String fileName,String folderName,int docID){
		int tries = 0,maxTries = 10;
		do{
			String stringTest = null;
			try {
				
				FileOutputStream fout = new FileOutputStream(HOME_PDF+folderName+"/"+fileName);
				BufferedInputStream in = null;
				
				int i=0,maxTry=10;
				do{
					
					try{
						proxy.setUrl("https://services.saccourt.ca.gov/PublicCaseAccess/Document/ViewDocument");
						proxy.usePost();
						proxy.setDataToSend("__RequestVerificationToken="+URLEncoder.encode(key,"UTF-8")+
											"&SourceKey="+URLEncoder.encode(url,"UTF-8")+
											"&SourceSystemId="+URLEncoder.encode("1","UTF-8")
											+"&PreviewButton="+URLEncoder.encode("Preview","UTF-8"));
						proxy.connect();
						in = new BufferedInputStream(proxy.getContent());	
						i=maxTry;
					}catch(Exception e){
						System.out.println("PROBLEM : "+url);
						i++;
					}	
				}while(i<maxTry);
				if(in == null){
					System.out.println("PROBLEM : "+url);
					tries++;
				}else{
					int count;
					boolean f=true;
					byte data[] = new byte[1024];
					while ((count = in.read(data, 0, 1024)) != -1) {
						fout.write(data, 0, count);
						if(f){
							stringTest = new String(data, 0, count);
							f=false;
						} 
					}
					fout.close();
					in.close();
				}
			}catch (Exception e1) {
				System.out.println("PROBLEM : "+url);
				tries++;
			}
			System.out.println("lol");
			if(!stringTest.contains((String)"PDF")){
				
				System.out.println("******************");
				System.out.println(id);
				System.out.println("Error");
				System.out.println(fileName);
				System.out.println("******************");

			}else{
				System.out.println("******************");
				System.out.println(id);
				System.out.println("Cool");
				System.out.println(fileName);
				System.out.println("******************");
			}
			File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);
			
			if(thisPDF.length() != 0){
				
				if(!stringTest.contains((String)"PDF")){
					
					System.out.println("******************");
					System.out.println(id);
					System.out.println("Error");
					System.out.println(fileName);
					System.out.println("******************");
					
					thisPDF.delete();
					tries++;
				}else{
					System.out.println("******************");
					System.out.println(id);
					System.out.println("Cool");
					System.out.println(fileName);
					System.out.println("******************");
				}
				tries = maxTries;
			}else{
				//System.out.println("FAILED : "+url);
				thisPDF.delete();
				tries++;
			}
		}while(tries < maxTries);
		
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
	
	@Override
	public void run() {
		for(Case aCase:this.cases){
			this.scrap(aCase);
		}
	}
	
}
