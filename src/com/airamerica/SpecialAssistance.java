package com.airamerica;

public class SpecialAssistance extends Services {
	private String typeofService;
	public  SpecialAssistance(String code, String type, String typeofService){
		setProductCode(code);
		setType(type);
		this.typeofService = typeofService;
	}
	public String getSpecialAssistance(double distance){
		return typeofService;
	}
	@Override
	public double getServicesPrice(double distance) {
		return 0;
	}
	@Override
	public String getServicesName() {
		return typeofService;
	}
	@Override
	public double getCost() {
		return 0;
	}
	@Override
	public double getTaxes() {
		return 0;
	}
	@Override
	public String getAge() {
		return null;
	}
	@Override
	public String getServiceType() {
		String a = super.getType();
		return a;
	}
	@Override
	public double costPerMile() {
		return 0;
	}
	@Override
	public double Total() {
		return 0;
	}
}
