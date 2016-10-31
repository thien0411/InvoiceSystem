package com.airamerica;

public class Customer  {
	
	
	private String customerCode;
	
	/* Note how Person has been used as 
	 * primary contact(Composition Relationship) */ 
	private Person primaryContact;

	/*TODO: Add other fields as necessary (eg. firstName, lastName,
	phoneNo etc) */
	private String type;
	private String name;
	private String airlineMiles;
	
	
	// TODO: Add constructor(s)

	
	public String getCustomerCode() {
		return customerCode;
	}

	public Customer(String customerCode, Person primaryContact, String type,
			String name, String airlineMiles) {
		this.customerCode = customerCode;
		this.primaryContact = primaryContact;
		this.type = type;
		this.name = name;
		this.airlineMiles = airlineMiles;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAirlineMiles() {
		return airlineMiles;
	}

	public void setAirlineMiles(String airlineMiles) {
		this.airlineMiles = airlineMiles;
	}

	public void setPrimaryContact(Person primaryContact) {
		this.primaryContact = primaryContact;
	}

	/*TODO: Add Getters and setters */
	public Person getPrimaryContact() {
		return primaryContact;
	}

	//TODO: Add additional methods if needed */
}
