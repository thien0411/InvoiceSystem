package com.airamerica;

public abstract class Services extends Product {
	private int quantity;
	private String personCode;

	public int getQuantity() {
		return quantity;
	}
	

	public String getPersonCode() {
		return personCode;
	}


	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public abstract double getServicesPrice(double distance);
	public abstract String getServicesName();
	public abstract double getCost();
	public abstract double getTaxes();
	public abstract double Total();
	public abstract String getAge();
	public abstract String getServiceType();
	public abstract double costPerMile();
}
