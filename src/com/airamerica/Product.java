package com.airamerica;

import java.text.DecimalFormat;

import com.airamerica.utils.Haversine;

public abstract class Product {
	private String productCode;
	private String type;	
	private double distance;
	public void setDistance(double distance) {
		this.distance = distance;
	}
	private int quantity;
	
	public double getDistance() {
		return distance;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double airPortsDistance(AirPort departureCity, AirPort arrivalCity){
		distance = Haversine.getMiles(departureCity.getLatitude(),departureCity.getLongtitude(),arrivalCity.getLatitude(),arrivalCity.getLongtitude());
		distance = Double.parseDouble(new DecimalFormat(".##").format(distance));
		return distance;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Product makeCopy() {
		// TODO Auto-generated method stub
		return null;
	}
}
