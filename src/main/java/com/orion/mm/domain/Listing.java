package com.orion.mm.domain;


public class Listing {
	
	private int id;
	private String address;
	private int price;
	private int zestimate;
	private int bed;
	private int bath;
	private int sqft;
	private double lot;
	private int yearBuilt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getZestimate() {
		return zestimate;
	}
	public void setZestimate(int zestimate) {
		this.zestimate = zestimate;
	}
	public int getBed() {
		return bed;
	}
	public void setBed(int bed) {
		this.bed = bed;
	}
	public int getBath() {
		return bath;
	}
	public void setBath(int bath) {
		this.bath = bath;
	}
	public int getSqft() {
		return sqft;
	}
	public void setSqft(int sqft) {
		this.sqft = sqft;
	}
	public double getLot() {
		return lot;
	}
	public void setLot(double lot) {
		this.lot = lot;
	}
	public int getYearBuilt() {
		return yearBuilt;
	}
	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	@Override
	public String toString() {
		return "Listing [address=" + address + ", price=" + price
				+ ", zestimate=" + zestimate + ", bed=" + bed + ", bath="
				+ bath + ", sqft=" + sqft + ", lot=" + lot + ", yearBuilt="
				+ yearBuilt + "]";
	}
	
}
