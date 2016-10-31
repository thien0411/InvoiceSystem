package com.airamerica;
/*
/* A partial implementation representing a 
 * Person */
import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String personCode;
	private String firstName;
	private String lastName;
	
	/* Note how Address has been used (Composition Relationship) */ 
	private Address address;
	
	
	/*TODO: Add other fields as necessary (eg. firstName, lastName,
	phoneNo etc) */
	
	private String phoneNumber;
	/* Note how email is used (a collection of variable size) */ 
	private List<String> emails;
	
	
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNo) {
		this.phoneNumber = phoneNo;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

	// TODO: Add appropriate constructor(s) DONE!!!! creating constructor
	public Person(String personCode, Address address, String firstName, String lastName, String phoneNumber) {
		this.personCode = personCode;
		this.address = address;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emails = new ArrayList<String>(); 
	}
	
	// TODO: Add Getters and setters as appropriate
	public Address getAddress() {
		return this.address;
	}
	
	public void setEmails(List<String> emails)
	{
		this.emails = emails;
	}
		
	// TODO: Add additional methods here
	public void addEmail(String email) {
		this.emails.add(email);
	}
}
