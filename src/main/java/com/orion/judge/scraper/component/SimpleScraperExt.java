package com.orion.judge.scraper.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orion.judge.scraper.component.ScraperField;

public class SimpleScraperExt implements Scraper{

	private static final Logger logger = LoggerFactory.getLogger(SimpleScraperExt.class);
	
	private String configFileName;
	private org.jdom2.Document configFile;
	private ArrayList<ScraperField> dataPointer;
	private ArrayList<Hashtable<String,String>> scrappedData;
	private ScraperUrl urlToScrap;
	private Scraper thisScraper;
	private boolean isConfigurated;
	private int countData;
	private org.jsoup.nodes.Document page;
	
	public SimpleScraperExt(String configFileName){
		dataPointer = new ArrayList<ScraperField>();
		scrappedData = new ArrayList<Hashtable<String,String>>();
		this.configFileName = configFileName;
		this.urlToScrap = null;
		thisScraper = this;
		isConfigurated = false;
		countData = -1;
		page = null;
	}
	
	public SimpleScraperExt(ScraperUrl urlToScrap,String configFileName){
		this(configFileName);
		this.urlToScrap = urlToScrap;
	}
	
	@Override
	public void scrap() {
		if(!isConfigurated){
			thisScraper.config();
		}
		if(page == null){
			page = thisScraper.connect(this.urlToScrap);
		}
		//System.out.println(page);
		if(page != null){
			thisScraper.readData(page);
		}
	}
	
	@Override
	public Document connect(ScraperUrl url) {
		//logger.info(Calendar.getInstance().getTime()+" Begin connection to "+url.getUrl());
		Document doc = null;
		Connection connection = url.connect();
		int failCount = 0;
		int maxFails = 30;
		do{
			try {
				doc = connection.execute().parse();
				failCount = maxFails;
			} catch (IOException e) {
				System.out.println("try "+failCount+" : "+url.getUrl());
				failCount++;
			}
		}while(failCount<maxFails);
		
		//logger.info(Calendar.getInstance().getTime()+" End connection to "+url.getUrl());
		return doc;
	}
	
	@Override
	public Document readData(Document doc) {
		//logger.info(Calendar.getInstance().getTime()+" Begin readData");
		int beginIndex = 0;
		if(!scrappedData.isEmpty()){
			beginIndex = scrappedData.size();
		}
		for (ScraperField field : dataPointer) {
			int size = 0,init = 0;
			if(field.getPathInit() == null){
				size = doc.select(field.getPath()).size();
			}else{
				size = Math.max(scrappedData.size(), doc.select(field.getPathInit()).size());
				init = 1;
			}
			 
			//logger.info(Calendar.getInstance().getTime()+" selecting "+size+" elements");
			for(int i=0;i<size;i++){
				Hashtable<String,String> hashTable;
				if(scrappedData.size() <= i+beginIndex){
					hashTable = new Hashtable<String,String>();
					scrappedData.add(hashTable);
				}else{
					hashTable = scrappedData.get(i+beginIndex);
				}
				org.jsoup.nodes.Element elementToScrap = null;
				if(field.getPathInit() == null){
					elementToScrap = doc.select(field.getPath()).get(i);
				}else{
					elementToScrap = doc.select(field.getPath().replace("{{x}}", (i+init)+"")).first();
				}
				 
				if(elementToScrap != null){
					if(field.getTypeOfValue().equals("text")){
						hashTable.put(field.getKey(), elementToScrap.text());
					}else{
						hashTable.put(field.getKey(), elementToScrap.attr(field.getTypeOfValue()));
					}
				}
				
				//logger.info(Calendar.getInstance().getTime()+" selected "+i+"/"+size+" elements");
			}
			//logger.info(Calendar.getInstance().getTime()+" end selecting elements");
		}
		//logger.info(Calendar.getInstance().getTime()+" End readData");
		return doc;
	}
	
	@Override
	public void config(){
		readConfigFile();
		setDataPointer();
		isConfigurated = true;
	}
	
	private void setDataPointer() {
		List<Element> fields = this.configFile.getRootElement().getChildren("field");
		for (int i = 0; i < fields.size(); i++) {
			Element node = fields.get(i);
			ScraperField field = new ScraperField(node.getChildText("key"),node.getChildText("path"));
			String typeOfValue = node.getAttributeValue("typeValue");
			String pathInit = node.getChildText("pathInit");
			if(typeOfValue != null){
				field.setTypeOfValue(typeOfValue);
			}
			if(pathInit != null){
				field.setPathInit(pathInit);
			}
			this.dataPointer.add(field);
		}
	}
	
	private boolean readConfigFile(){
		SAXBuilder builder = new SAXBuilder();
		InputStream input = getClass().getResourceAsStream("/scrapers/"+configFileName);
		try {
			this.configFile = (org.jdom2.Document) builder.build(input);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void emptyData(){
		this.scrappedData.clear();
	}
	
	@Override
	public ArrayList<Hashtable<String,String>> getScrappedData(){
		return scrappedData;
	}
	
	@Override
	public ScraperUrl getUrlToScrap(){
		return this.urlToScrap;
	}
	
	@Override
	public void setUrlToScrap(ScraperUrl urlToScrap){
		this.urlToScrap = urlToScrap;
	}
	
	@Override
	public org.jdom2.Document getConfigFile() {
		return configFile;
	}

	@Override
	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
		this.isConfigurated = false;
	}
	public Scraper getThisScraper() {
		return thisScraper;
	}
	
	@Override
	public void setThisScraper(Scraper thisScraper) {
		this.thisScraper = thisScraper;
	}
	
	@Override
	public int getCountData() {
		return countData;
	}
	
	@Override
	public void setCountData(int countData) {
		this.countData = countData;
	}
	
	public void setPage(org.jsoup.nodes.Document page){
		this.page = page;
	}
	
	public org.jsoup.nodes.Document getPage(){
		return page;
	}
	
}
