package com.airamerica;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.airamerica.AirPort;

import org.joda.time.DateTime;

public abstract class Ticket extends Product {
	private String ticketCode;
	private String travelDate;
	private String flightClass;
	private AirPort departureAirport;
	private DateTime dateTime;
	private String depTime;
	private String arrTime;
	private String flightNumber;
	private int numberOfPassenger;
	private AirPort arrivalAirport;
	private String aircraftType;
	private String[] listOfSeatNumber;
	private ArrayList<Passenger> listOfPassengers;
	private String ticketNote;
	private Person personCode;
	private String identity;
	private String nationality;
	private double ticketPrice;
	
	// List of setter and getter

	public int getNumberOfPassenger() {
		return numberOfPassenger;
	}
	public void setNumberOfPassenger(int numberOfPassenger) {
		this.numberOfPassenger = numberOfPassenger;
	}

	private String seatNumber;
	//public String seasonStartDate;
	public String getDepTime() {
		return depTime;
	}
	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	public String getFlightClass() {
		return flightClass;
	}
	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}
	public AirPort getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(AirPort departureAirport) {
		this.departureAirport = departureAirport;
	}
	public DateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	public AirPort getArrivalAirport() {
		return arrivalAirport;
	}
	public void setArrivalAirport(AirPort arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	public String getAircraftType() {
		return aircraftType;
	}
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
	public String[] getListOfSeatNumber() {
		return listOfSeatNumber;
	}
	public void setListOfSeatNumber(String[] listOfSeatNumber) {
		this.listOfSeatNumber = listOfSeatNumber;
	}
	public ArrayList<Passenger> getListOfPassengers() {
		return listOfPassengers;
	}
	public void setListOfPassengers(ArrayList<Passenger> listOfPassengers) {
		this.listOfPassengers = listOfPassengers;
	}
	public String getTicketNote() {
		return ticketNote;
	}
	public void setTicketNote(String ticketNote) {
		this.ticketNote = ticketNote;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public Person getPersonCode() {
		return personCode;
	}
	public void setPersonCode(Person personCode) {
		this.personCode = personCode;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	
	
	/*public void makeTicket(String travelDate, String flightClass, AirPort departureCity, DateTime dateTime,
			AirPort arrivalCity, String aircraftType, String arrivalDateTime, String[] listOfSeatNumber,     
			Passenger listOfPassenger, String ticketNote){
		this.travelDate= travelDate  ;
		this.flightClass = flightClass ;
		this.departureAirport= departureCity ;
		this.dateTime= dateTime ;
		this.arrivalAirport= arrivalCity;
		this.aircraftType= aircraftType;
		this.arrTime = arrivalDateTime;
		this.listOfSeatNumber= listOfSeatNumber;
		this.listOfPassengers= listOfPassenger;
		this.ticketNote= ticketNote;
		
	}*/ 
	
	
	
	//Calculating part
	
	public double getTicketPrice(double distance) {
		double rawPrice = 0;
		if(flightClass.equals("EC")){
			rawPrice = distance * 0.15;
		}
		if(flightClass.equals("BC")){
			rawPrice = distance * 0.5;
		}
		if(flightClass.equals("EP")){
			rawPrice = distance * 0.2;
		}
		ticketPrice = rawPrice * numberOfPassenger;
		return ticketPrice;
	}
	public String getTicketCode() {
		return ticketCode;
	}
	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}
	public Date convertDate(String day){
		Date date2 = null;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    try {
		date2 = date.parse(day);
		
	} catch (ParseException e) {
		e.printStackTrace();
	}
    return date2;
	}
	
	public double printPrice(){
		return getTicketPrice();
	}
	public abstract int getAwardMile(double distance);
	public abstract double getRebate();
	public abstract double getTax();
	public abstract double Total();
	public double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	

}