package com.airamerica;

public class Coordinates {
	private double latdges;
	private double latmins;
	private double londegs;
	private double lonmins;
	
	
	public Coordinates(double latdges, double latmins, double londegs, double lonmins){
		this.latdges = latdges;
		this.latmins = latmins;
		this.londegs = londegs;
		this.lonmins = lonmins;
	}
	
	public double latitudeCalculate(){
		double latitude =  latdges+ (latmins/60);  
		return latitude;

	}
	public double longtitudeCalculate(){
		double longtitude = londegs+ (lonmins/60);
		return longtitude;
		
	}

	public double getLatdges() {
		return latdges;
	}

	public void setLatdges(double latdges) {
		this.latdges = latdges;
	}

	public double getLatmins() {
		return latmins;
	}

	public void setLatmins(double latmins) {
		this.latmins = latmins;
	}

	public double getLondegs() {
		return londegs;
	}

	public void setLondegs(double londegs) {
		this.londegs = londegs;
	}

	public double getLonmins() {
		return lonmins;
	}

	public void setLonmins(double lonmins) {
		this.lonmins = lonmins;
	}

}
