package com.orion.judge.scripts;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.CivilCase;
import com.orion.judge.scraper.component.ScraperUrl;
import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.AttorneyService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.CourtService;
import com.orion.judge.service.JudgeService;
import com.orion.judge.service.PartieService;
import com.orion.judge.service.PaymentService;

public class SFDownloadDocs extends Thread{
	
	public static int fails = 0;
	public static int exists = 0;
	public static int goods = 0;
	public static int waitings = 0;
	
	private final String HOME_PDF = "/home/ubuntu/PDF/";
	
	private ActionCaseService actionCaseService;
	
	private List<Integer> casesID;
	
	public SFDownloadDocs(ActionCaseService actionCaseService,List<Integer> casesID){
		this.casesID = new ArrayList<Integer>();
		this.casesID.addAll(casesID);
		this.actionCaseService = actionCaseService;
	}
	
	private void saveDocs(int caseID){
		//System.out.println(caseID);
		String folderName = Integer.toString(caseID);
		(new File(HOME_PDF+Integer.toString(caseID))).mkdirs();
		for(ActionCase action:this.actionCaseService.getActionCasesByCaseID(caseID)){
			String fileName = action.getId()+".pdf";
			File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);
			if(!thisPDF.exists()){
				this.downloadPDF(action.getDocument(),fileName,folderName,action.getId());
			}else{
				exists++;
				this.actionCaseService.updateStatus(action.getId(), true);
			}
		}
	}
	private void downloadPDF(String url,String fileName,String folderName,int docID){
		int alltries = 0;
		int tries = 0,maxTries = 3;
		do{
			if(tries == 1){
				alltries++;
				waitings++;
			}
			try {
				Connection.Response res = (new ScraperUrl(url)).setSilent().connect().execute();
				Connection co = (new ScraperUrl("http://webaccess.sftc.org/minds_asp_pdf/Viewer/DownLoadDocument.asp?PGCNT=0")).setSilent().setCookies(res.cookies()).connect();
				String docName = "";
				docName = co.execute().parse().getElementById("Ads").attr("src");
				FileOutputStream fout = new FileOutputStream(HOME_PDF+folderName+"/"+fileName);
				BufferedInputStream in = null;
				
				int i=0,maxTry=3;
				do{
					if(i == 1){
						waitings++;
						alltries++;
					}
					try{
						URLConnection con = new URL(docName).openConnection();
						con.setConnectTimeout(30000);
						con.setReadTimeout(30000);
						in = new BufferedInputStream(con.getInputStream());	
						i=maxTry;
					}catch(Exception e){
						i++;
					}	
				}while(i<maxTry);
				if(in == null){
					System.out.println("PROBLEM : "+docName);
					tries++;
				}else{
					int count;
					byte data[] = new byte[1024];
					while ((count = in.read(data, 0, 1024)) != -1) {
						fout.write(data, 0, count);
					}
					fout.close();
					in.close();
				}
			} catch (Exception e1) {
				//e1.printStackTrace();
				tries++;
			}
			File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);
			System.out.println(thisPDF.length());
			if(thisPDF.length() != 0){
				tries = maxTries;
				goods++;
				this.actionCaseService.updateStatus(docID, true);
			}else{
				//System.out.println(url);
				//System.out.println(thisPDF.length());
				thisPDF.delete();
				tries++;
				if(tries >= maxTries){
					this.actionCaseService.updateStatus(docID, false);
				}
			}
		}while(tries < maxTries);
		if(tries != 0){
			waitings-=alltries;
		}
		if(tries >= maxTries){
			fails++;
		}
	}

	@Override
	public void run() {
		int i=0;
		for(int caseID:this.casesID){
			this.saveDocs(caseID);
			i++;
			//System.out.println(i+"/"+this.casesID.size());
		}
	}
}
