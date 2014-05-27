package com.orion.zd.scraper.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class SimpleScraperV2 implements ScraperV2{

	private String configFileName;
	private org.jdom2.Document configFile;
	private ArrayList<ScraperObject> dataPointer;
	private List<ArrayList<Object>> scrappedData;
	private ScraperUrl urlToScrap;
	private ScraperV2 thisScraper;
	private boolean isConfigurated;
	private int countData;
	
	public SimpleScraperV2(String configFileName){
		dataPointer = new ArrayList<ScraperObject>();
		scrappedData = new ArrayList<ArrayList<Object>>();
		this.configFileName = configFileName;
		this.urlToScrap = null;
		thisScraper = this;
		isConfigurated = false;
		countData = -1;
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
		org.jsoup.nodes.Document page = thisScraper.connect(this.urlToScrap);
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
				i=3;
			} catch (IOException e) {
				System.out.println("Failed to connect attempt : "+(i+1));
				System.out.println(url.getUrl());
				i++;
			}
		}while(i<3);
		return doc;
	}
	
	@Override
	public Document readData(Document doc) {
		for (ScraperObject object : this.dataPointer) {
			Elements miniDocs = doc.parents();
			if(object.getInclude() == null){
				miniDocs = doc.select(object.getInclude());
			}
			ArrayList<Object> objects = new ArrayList<Object>(); 
			for(org.jsoup.nodes.Element miniDoc : miniDocs){
				Object newObject = null;
				try {
					newObject = object.getClass().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				for(ScraperAttribute attribute : object.getAttributes()){
					newObject = setAttribute(object,newObject,attribute,miniDoc);
				}
				objects.add(newObject);
			}
			scrappedData.add(objects);
		}
		return doc;
	}
	private Object setAttribute(ScraperObject object ,Object instance , ScraperAttribute attribute,org.jsoup.nodes.Element miniDoc){
		try {
			if(object.getClass().getField(attribute.getName()).getType() == List.class){
				object.getClass().
				getMethod(this.getSetterMethod(attribute.getName()), List.class).
				invoke(instance, this.getListValue(miniDoc, attribute));
			}else{
				if(object.getClass().getField(attribute.getName()).getType() == int.class){
					object.getClass().
					getMethod(this.getSetterMethod(attribute.getName()), int.class).
					invoke(instance, Integer.parseInt(getElementValue(miniDoc,attribute)));
				}else{
					object.getClass().
					getMethod(this.getSetterMethod(attribute.getName()), String.class).
					invoke(instance, getElementValue(miniDoc,attribute));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	private List<String> getListValue(org.jsoup.nodes.Element miniDoc,ScraperAttribute attribute){
		Elements elementsToScrap = miniDoc.select(attribute.getPath());
		List<String> elementsValue = new ArrayList<String>();
		for(org.jsoup.nodes.Element element : elementsToScrap){
			elementsValue.add(getValue(element,attribute));
		}
		return elementsValue;
	}
	private String getElementValue(org.jsoup.nodes.Element miniDoc,ScraperAttribute attribute){
		Elements element = miniDoc.select(attribute.getPath());
		return getValue(element.get(0),attribute);
	}
	private String getValue(org.jsoup.nodes.Element element,ScraperAttribute attribute){
		if(attribute.getTypeValue().equals("text")){
			return element.text();
		}
		return element.attr(attribute.getTypeValue());
	}
	private String getSetterMethod(String attribute){
		return "set"+Character.toUpperCase(attribute.charAt(0))+attribute.substring(1);
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
											objects.get(i).getChildText("include"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}
			for (int j = 0; j < attributes.size(); j++) {
				Element node = attributes.get(i);
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
