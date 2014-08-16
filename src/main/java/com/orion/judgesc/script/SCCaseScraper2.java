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

import org.jsoup.Jsoup;

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

public class SCCaseScraper2 {
	
	/*private final String HOME_PDF = "/home/ubuntu/PDF2/";
	
	private CaseService caseService;
	
	private ActionService actionService;
	
	private DocumentService documentService;

	private EventService eventService;

	private ParticipantService participantService;
	
	private String caseLink;
	
	public SCCaseScraper2(CaseService caseService,ActionService actionService,
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
		
		Case aCase = this.caseService.getCaseByNumber(newCase.getNumber());
		if(aCase == null)
			return;
		List<Object> actions = this.getObjects(allObjects, Action.class);
		if(actions != null){
			for(Object action : actions){
				Action newAction = ((Action)action);
				if(newAction.getEntry() != null){
					if(newAction.getDocument() != null){
						Action theAction = this.actionService.getActionByEquality();
						String folderName = Integer.toString(aCase.getId());
						(new File(HOME_PDF+Integer.toString(aCase.getId()))).mkdirs();
						String fileName = theAction.getId()+".pdf";
						File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);		
						if(!thisPDF.exists()){
							this.downloadPDF(action.getKey(),action.getDocument(),fileName,folderName,theAction.getId());
						}
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
						
						URL wurl = null;
						String proxyName = "http://www.webproxy.ca";

						URL base = new URL("http://www.webproxy.ca/");
						HttpURLConnection urlConnection = (HttpURLConnection) base.openConnection();
						urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
						List<String> cookies = urlConnection.getHeaderFields().get("Set-Cookie");
							
						Jsoup.connect(proxyName+"/browse.php?u="+URLEncoder.encode("https://services.saccourt.ca.gov/PublicCaseAccess/Document/ViewDocument","US-ASCII")+"&b=20&f=norefer").
							cookie(cookies.get(0).split("=")[0],cookies.get(0).split("=")[1]);
						
						
						
						
						HttpURLConnection connection = (HttpURLConnection) wurl.openConnection(); 
						connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
						connection.setRequestMethod("POST");
						
						connection.addRequestProperty("Cookie", cookies.get(0));
						connection.getHeaderFields().get("Set-Cookie");

						
						
						
						
						
						URL base2 = new URL("http://www.webproxy.ca/includes/process.php?action=sslagree");
						HttpURLConnection urlConnection2 = (HttpURLConnection) base2.openConnection();
						urlConnection2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
						urlConnection2.addRequestProperty("Cookie", cookies.get(0));
						urlConnection2.setConnectTimeout(50000);
						
						in = new BufferedInputStream(urlConnection2.getInputStream());	
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
			} catch (Exception e1) {
				System.out.println("PROBLEM : "+url);
				tries++;
			}
			File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);
			
			if(thisPDF.length() != 0){
				
				if(!stringTest.contains((String)"PDF")){
					thisPDF.delete();
					tries++;
				}else{
					//System.out.println("http://54.183.93.241/"+folderName+"/"+fileName);
				}
				tries = maxTries;
			}else{
				//System.out.println("FAILED : "+url);
				thisPDF.delete();
				tries++;
			}
		}while(tries < maxTries);
		
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
	*/
	
}
