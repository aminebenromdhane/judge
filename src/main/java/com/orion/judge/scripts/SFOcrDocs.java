package com.orion.judge.scripts;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.taglibs.standard.extra.spath.Path;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.CivilCase;
import com.orion.judge.domain.Document;
import com.orion.judge.scraper.component.ScraperUrl;
import com.orion.judge.service.ActionCaseService;
import com.orion.judge.service.AttorneyService;
import com.orion.judge.service.CivilCaseService;
import com.orion.judge.service.CourtService;
import com.orion.judge.service.DocumentService;
import com.orion.judge.service.JudgeService;
import com.orion.judge.service.PartieService;
import com.orion.judge.service.PaymentService;

public class SFOcrDocs extends Thread{
	
	public static int fails = 0;
	public static int exists = 0;
	public static int goods = 0;
	
	private final String HOME = "/home/ubuntu/";
	private final String HOME_PDF = "/home/ubuntu/PDF/";
	private final String HOME_OUT = "/home/ubuntu/OUT/";
	
	private ActionCaseService actionCaseService;
	
	private DocumentService documentService;
	
	private List<Integer> casesID;
	
	public SFOcrDocs(ActionCaseService actionCaseService,DocumentService documentService,List<Integer> casesID){
		this.casesID = new ArrayList<Integer>();
		this.casesID.addAll(casesID);
		this.actionCaseService = actionCaseService;
		this.documentService = documentService;
	}
	
	private void saveData(int caseID){
		//System.out.println(caseID);
		String folderName = Integer.toString(caseID);
		(new File(HOME_PDF+Integer.toString(caseID))).mkdirs();
		for(ActionCase action:this.actionCaseService.getDownloadedActionCasesByCaseID(caseID)){
			String fileName = action.getId()+".pdf";
			File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);
			if(thisPDF.exists()){
				this.ocrPDF(action.getDocument(),fileName,folderName,action.getId());
			}else{
				fails++;
			}
		}
	}
	private void ocrPDF(String url,String fileName,String folderName,int docID){
		if(documentService.getDocument(docID) != null){
			exists++;
			return;
		}

		executeCommand("cp "+HOME_PDF+folderName+"/"+fileName+" "+HOME+"scripts/");
		File outFolder = new File(HOME_OUT+folderName);
		outFolder.mkdirs();
		executeCommand("bash /home/ubuntu/scripts/pdf2txt.sh "+HOME+"scripts/"+fileName+" "+HOME_OUT+folderName+"/"+docID+".out");
		System.out.println("bash /home/ubuntu/scripts/pdf2txt.sh "+HOME+"scripts/"+fileName+" "+HOME_OUT+folderName+"/"+docID+".out");
		File outFile = new File(HOME_OUT+folderName+"/"+docID+".out");
		if(!outFile.exists()){
			fails++;
			System.out.println("File not created");
			return;
		}
		try {
			Document newDoc = new Document();
			byte[] encoded = Files.readAllBytes(Paths.get(HOME_OUT+folderName+"/"+docID+".out"));
			newDoc.setActionCaseID(docID);
			newDoc.setUrl(url);
			newDoc.setContent(new String(encoded, StandardCharsets.UTF_8));
			documentService.insertDocument(newDoc);
			outFile.delete();
			goods++;
		} catch (IOException e) {
			e.printStackTrace();
			fails++;
		}
	}

	private void executeCommand(String command) {
		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(output);
	}
	
	@Override
	public void run() {
		int i=0;
		for(int caseID:this.casesID){
			this.saveData(caseID);
			i++;
			//System.out.println(i+"/"+this.casesID.size());
		}
	}
}
