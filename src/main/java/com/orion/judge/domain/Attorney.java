package com.orion.judge.domain;

public class Attorney {

	private int id;
    private int civilCaseID;
    private String name;
    private long barNumber;
    private String address;
    private String partiesRepresented;
    
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
	public long getBarNumber() {
		return barNumber;
	}
	public void setBarNumber(long barNumber) {
		this.barNumber = barNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPartiesRepresented() {
		return partiesRepresented;
	}
	public void setPartiesRepresented(String partiesRepresented) {
		this.partiesRepresented = partiesRepresented;
	}
    
    
}
