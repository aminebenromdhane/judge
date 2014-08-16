package com.orion.judgesc.domain;

public class Participant {

	private int id;
	private String name;
	private String role;
	private String representer;
	private int caseId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRepresenter() {
		return representer;
	}
	public void setRepresenter(String representer) {
		this.representer = representer;
	}
	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	@Override
	public String toString() {
		return "Participant [id=" + id + ", name=" + name + ", role=" + role
				+ ", representer=" + representer + ", caseId=" + caseId + "]";
	}
	
	
}
