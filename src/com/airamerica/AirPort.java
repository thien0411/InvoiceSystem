package com.airamerica;

public class AirPort {
	private String airportCode;
	private String airportName;
	private Address address;
	private double latitude;
	private double longtitude;
	private double passengerFacilityFee;
	
	public AirPort(String airportCode, String airportName, Address address, double latitude, double longtitude, double passengerFacilityFee){
		this.airportCode = airportCode;
		this.airportName = airportName;
		this.address = address;
		this.latitude= latitude;
		this.longtitude=longtitude;
		this.passengerFacilityFee = passengerFacilityFee;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getName() {
		return airportName;
	}

	public void setName(String name) {
		this.airportName = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public double getPassengerFacilityFee() {
		return passengerFacilityFee;
	}

	public void setPassengerFacilityFee(double passengerFacilityFee) {
		this.passengerFacilityFee = passengerFacilityFee;
	}
	
	
	
}
