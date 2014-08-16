package com.orion.judgesc.script;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.orion.judgesc.domain.Action;
import com.orion.judgesc.service.ActionService;
import com.orion.judgesc.service.CaseService;


public class SCDownloadDocs extends Thread{
	
	
	private final String HOME_PDF = "/home/ubuntu/PDF2/";
	
	private ActionService actionService;
	
	private List<Integer> casesID;
	
	public SCDownloadDocs(ActionService actionCaseService,List<Integer> casesID){
		this.casesID = new ArrayList<Integer>();
		this.casesID.addAll(casesID);
		this.actionService = actionCaseService;
	}
	
	private void saveDocs(int caseID){
		//System.out.println(caseID);
		String folderName = Integer.toString(caseID);
		(new File(HOME_PDF+Integer.toString(caseID))).mkdirs();
		for(Action action:this.actionService.getActionByCaseID(caseID)){
			String fileName = action.getId()+".pdf";
			File thisPDF = new File(HOME_PDF+folderName+"/"+fileName);
			if(!thisPDF.exists()){
				System.out.println("**********************************");
				//System.out.println("https://services.saccourt.ca.gov"+action.getDocument());
				this.downloadPDF(action.getDocument(),fileName,folderName,action.getId());
				System.out.println("**********************************");
			}else{
			}
		}
	}
	private void downloadPDF(String url,String fileName,String folderName,int docID){
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
						if(url.contains("browse.php?")){
							System.out.println(proxyName+url);
							wurl = new URL(proxyName+url);
						}else{
							System.out.println(proxyName+"/browse.php?u="+URLEncoder.encode("https://services.saccourt.ca.gov"+url,"US-ASCII")+"&b=20&f=norefer");
							wurl = new URL(proxyName+"/browse.php?u="+URLEncoder.encode("https://services.saccourt.ca.gov"+url,"US-ASCII")+"&b=20&f=norefer");
						}						

						URL base = new URL("http://www.webproxy.ca/");
						HttpURLConnection urlConnection = (HttpURLConnection) base.openConnection();
						urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
						List<String> cookies = urlConnection.getHeaderFields().get("Set-Cookie");

						HttpURLConnection connection = (HttpURLConnection) wurl.openConnection(); 
						connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
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
