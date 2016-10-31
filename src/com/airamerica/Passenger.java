package com.airamerica;

public class Passenger extends Person {
	String seatNumber;
	String personCode;
	String identityNumber;
	String age;
	String nationality;
	public Passenger(String seatNumber, String personCode, String identityNumber, String age, String nationality,Address address,String firstName, String lastName,String phoneNumber ){
		super(personCode, address, firstName, lastName, phoneNumber);
		super.setFirstName(firstName);
		super.setLastName(lastName);
		this.seatNumber = seatNumber;
		this.personCode = personCode;
		this.identityNumber = identityNumber;
		this.age = age;
		this.nationality= nationality;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getIdentityNumber() {
		return identityNumber;
	}
	public void setIdentityNumber(String indentityNumber) {
		this.identityNumber = indentityNumber;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

}