package com.airamerica;

//import org.joda.time.DateTime;

public class AwardTickets extends Ticket {
	private double pointsPerMile;
	private double awardTicketPrice;
	private double awardTicketTax;
// Constructor
	public AwardTickets(String code, String type, AirPort departureAirport, AirPort arrivalAirport,String depTime,String arrTime,String flightNo,String flightClass,String aircraftType, double pointsPerMile){
	setProductCode(code);
	setType(type);
	super.setDepartureAirport(departureAirport);
	this.setArrivalAirport(arrivalAirport); 
	super.setDepTime(depTime);
	super.setArrTime(arrTime);
	super.setFlightNumber(flightNo);
	super.setFlightClass(flightClass);
	super.setAircraftType(aircraftType); 
	this.pointsPerMile = pointsPerMile;
	}
	
public AwardTickets() {
	// TODO Auto-generated constructor stub
}

// Getter and Setter
	public double getPointsPerMile() {
		return pointsPerMile;
	}

	public void setPointsPerMile(double pointsPerMile) {
		this.pointsPerMile = pointsPerMile;
	}
	public double getTicketPrice(double distance){
		awardTicketPrice = 30.00;
		return awardTicketPrice;
	}
	@Override
	public int getAwardMile(double distance) {
		return (int)(super.getTicketPrice(distance) * pointsPerMile)/super.getNumberOfPassenger();
	}

	public void setAwardMile(int awardMile) {
	}

	@Override
	public double getTax() {
		awardTicketTax = (awardTicketPrice * 0.075) + (9.6 * super.getNumberOfPassenger());
		return awardTicketTax;
	}
	@Override
	public double Total() {
		return awardTicketTax + awardTicketPrice;
	}

	@Override
	public double getRebate() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Product makeCopy()
	{
		AwardTickets t = new AwardTickets();
		t.pointsPerMile = this. pointsPerMile;
		t.setArrivalAirport(super.getArrivalAirport());
		t.setTicketCode(super.getTicketCode());
		t.setTravelDate(super.getTravelDate());
		t.setFlightClass(super.getFlightClass());
		t.setDepartureAirport(super.getDepartureAirport());
		t.setDateTime(super.getDateTime());
		t.setDepTime(super.getDepTime());
		t.setArrTime(super.getArrTime());
		t.setFlightNumber(super.getFlightNumber());
		t.setNumberOfPassenger(super.getNumberOfPassenger());
		t.setArrivalAirport(super.getArrivalAirport());
		t.setAircraftType(super.getAircraftType());
		t.setListOfSeatNumber(super.getListOfSeatNumber());
		t.setListOfPassengers(super.getListOfPassengers());
		t.setTicketNote(super.getTicketCode());
		t.setPersonCode(super.getPersonCode());
		t.setIdentity(super.getIdentity());
		t.setNationality(super.getIdentity());
		t.setTicketPrice(super.getTicketPrice());
		return t;
	}
	
}
