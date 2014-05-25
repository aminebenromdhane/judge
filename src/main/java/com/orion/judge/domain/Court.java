package com.orion.judge.domain;

public class Court {

	private int id;
    private int civilCaseID;
    private int judgeID;
    private String date;
    private String matter;
    private String location;
    
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
	public int getJudgeID() {
		return judgeID;
	}
	public void setJudgeID(int judgeID) {
		this.judgeID = judgeID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMatter() {
		return matter;
	}
	public void setMatter(String matter) {
		this.matter = matter;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
    
}