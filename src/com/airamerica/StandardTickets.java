package com.airamerica;

public class StandardTickets extends Ticket {
	private double standardTicketPrice;
	private double standardTicketTax;
	
	
	public StandardTickets(String code, String type, AirPort departureAirport, AirPort arrivalAirport,String depTime,String arrTime,String flightNo,String flightClass,String aircraftType){
	setProductCode(code);
	setType(type);
	super.setDepartureAirport(departureAirport);
	this.setArrivalAirport(arrivalAirport); 
	super.setDepTime(depTime);
	super.setArrTime(arrTime);
	super.setFlightNumber(flightNo);
	super.setFlightClass(flightClass);
	super.setAircraftType(aircraftType); 
	}
	public StandardTickets() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public double getTicketPrice(double distance) {
		standardTicketPrice = super.getTicketPrice(distance);
		return standardTicketPrice;
	}
	@Override
	public int getAwardMile(double distance) {
		return 0;
	}
	//Error on this method, cannot call AirPort.getPassengerFacilityFee(), it has to be changed to Static in order to call this function
	@Override
	public double getTax() {
		standardTicketTax = (standardTicketPrice * 0.075) + (9.6 * super.getNumberOfPassenger());
		return standardTicketTax;
	}
	@Override
	public double Total() {
		return standardTicketTax + standardTicketPrice;
	}
	@Override
	public double getRebate() {
		// TODO Auto-generated method stub
		return 0;
	}
	public StandardTickets clone(String code, String type, AirPort departureAirport, AirPort arrivalAirport,String depTime,String arrTime,String flightNo,String flightClass,String aircraftType){
		return new StandardTickets (this.getProductCode(), this.getType(),this.getDepartureAirport(), this.getArrivalAirport(), this.getDepTime(), this.getArrTime(), this.getFlightNumber(), this.getFlightClass(),this.getAircraftType()); 
		}
	@Override
	public Product makeCopy()
	{
		Ticket t = new StandardTickets();
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






