package com.orion.judgesc.scraper.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.orion.judgesc.script.ProxyType1;


public class SimpleScraperV2 implements ScraperV2{

	private String configFileName;
	private org.jdom2.Document configFile;
	private ArrayList<ScraperObject> dataPointer;
	private List<ArrayList<Object>> scrappedData;
	private ScraperUrl urlToScrap;
	private ScraperV2 thisScraper;
	private boolean isConfigurated;
	private int countData;
	
	private String url;
	private ProxyType1 proxy;
	
	public SimpleScraperV2(String configFileName){
		dataPointer = new ArrayList<ScraperObject>();
		scrappedData = new ArrayList<ArrayList<Object>>();
		this.configFileName = configFileName;
		this.urlToScrap = null;
		thisScraper = this;
		isConfigurated = false;
		countData = -1;
		this.proxy = null;
	}
	public SimpleScraperV2(String url,ProxyType1 proxy,String configFileName){
		this(configFileName);
		this.url = url;
		this.proxy = proxy;
	}	
	public SimpleScraperV2(ScraperUrl urlToScrap,String configFileName){
		this(configFileName);
		this.urlToScrap = urlToScrap;
	}
	
	@Override
	public void scrap() {
		if(!isConfigurated){
			thisScraper.config();
		}
		org.jsoup.nodes.Document page = null;
		if(proxy == null){
			page = thisScraper.connect(this.urlToScrap);
		}else{
			page = thisScraper.connect(this.url);
		}
		
		if(page != null){
			thisScraper.readData(page);
		}
	}
	
	@Override
	public Document connect(ScraperUrl url) {
		Document doc = null;
		Connection connection = url.connect();
		int i=0;
		do{
			try {
				doc = connection.execute().parse();
				i=10;
			} catch (IOException e) {
				System.out.println("Failed to connect attempt : "+(i+1));
				System.out.println(url.getUrl());
				i++;
			}
		}while(i<10);
		return doc;
	}
	@Override
	public Document connect(String url) {
		Document doc = null;
		proxy.setUrl(url);
		proxy.connect();
		doc = Jsoup.parse(getStringFromInputStream(proxy.getContent()));
		return doc;
	}
	
	private String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public Document readData(Document doc) {
		for (ScraperObject object : this.dataPointer) {
			Elements miniDocs = doc.getAllElements();
			if(object.getInclude() != null){
				miniDocs = doc.select(object.getInclude());
			}
			ArrayList<Object> objects = new ArrayList<Object>();
			for(org.jsoup.nodes.Element miniDoc : miniDocs){
				Object newObject = null;
				try {
					newObject = object.getObjClass().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(ScraperAttribute attribute : object.getAttributes()){
					Elements elemntSelected = miniDoc.select(attribute.getPath());
					if(elemntSelected.size() == 1){
						newObject = object.setAttribute(newObject, attribute, 
								getValue(elemntSelected.get(0),attribute));
					}else{
						//System.out.println("ERROR in "+attribute.getName());
					}
				}
				objects.add(newObject);
			}
			scrappedData.add(objects);
		}
		return doc;
	}
	private String getValue(org.jsoup.nodes.Element element,ScraperAttribute attribute){
		if(attribute.getTypeValue().equals("text")){
			return element.text();
		}
		return element.attr(attribute.getTypeValue());
	}
	
	@Override
	public void config(){
		if(readConfigFile()){
			setDataPointer();
			isConfigurated = true;	
		}
	}
	
	private void setDataPointer() {
		List<Element> objects = this.configFile.getRootElement().getChildren("object");
		for(int i=0;i<objects.size(); i++){
			List<Element> attributes = objects.get(i).getChildren("attribute");	
			ScraperObject obj = null;
			try {
				obj = new ScraperObject(Class.forName(objects.get(i).getAttributeValue("name")),
										Class.forName(objects.get(i).getAttributeValue("filter")),
											objects.get(i).getChildText("include"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}
			for (int j = 0; j < attributes.size(); j++) {
				Element node = attributes.get(j);
				ScraperAttribute newAttribute = new ScraperAttribute(node.getChildText("key"),
																	node.getChildText("path"));
				newAttribute.addParam("typeValue",node.getAttributeValue("typeValue"));
				obj.addAttribute(newAttribute);
			}
			this.dataPointer.add(obj);
		}
		
		
	}
	
	private boolean readConfigFile(){
		SAXBuilder builder = new SAXBuilder();
		InputStream input = getClass().getResourceAsStream("/scrapers/"+configFileName);
		try {
			this.configFile = (org.jdom2.Document) builder.build(input);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void emptyData(){
		this.scrappedData.clear();
	}
	
	@Override
	public List<ArrayList<Object>> getScrappedData(){
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
	public ScraperV2 getThisScraper() {
		return thisScraper;
	}
	
	@Override
	public void setThisScraper(ScraperV2 thisScraper) {
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
	
}
