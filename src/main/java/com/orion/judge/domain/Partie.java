package com.orion.judge.domain;

public class Partie {
	
    private int id;
    private int civilCaseID;
    private String name;
    private String type;
    private String attorn;
    private String filings;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCivilCaseID() {
		return civilCaseID;
	}
	public void setCivilCaseID(int civilCaseID) {
		this.civilCaseID = civilCaseID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAttorn() {
		return attorn;
	}
	public void setAttorn(String attorn) {
		this.attorn = attorn;
	}
	public String getFilings() {
		return filings;
	}
	public void setFilings(String filings) {
		this.filings = filings;
	}
    
    
}
