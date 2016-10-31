package com.airamerica;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class Invoice{
	private String Date;
	private String PNR;
	private ArrayList<Product> ListOfTickets;
	private Customer Customer;
	private Person Salesperson;
	private String InvoiceCode;
	private ArrayList<Services> ListOfService;
	private double subTotal;
	private double Fees ;
	private double Taxes ;
	private double discount ;
	private double Total;
	private double TOTAL;


	public double getFees() {
		return Fees;
	}

	public void setFees(double fees) {
		Fees = fees;
	}

	public double getTaxes() {
		return Taxes;
	}

	public void setTaxes(double taxes) {
		Taxes = taxes;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotal() {
		return Total;
	}

	public void setTotal(double total) {
		Total = total;
	}

	public Invoice(String code, String date, String PNR,
			ArrayList<Product> listOfTickets, Customer customer,
			Person salesperson, ArrayList<Services> listOfService) {
		InvoiceCode = code;
		Date = date;
		this.PNR = PNR;
		ListOfTickets = listOfTickets;
		Customer = customer;
		Salesperson = salesperson;
		ListOfService = listOfService;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getPNR() {
		return PNR;
	}

	public void setPNR(String pNR) {
		PNR = pNR;
	}

	public ArrayList<Product> getListOfTickets() {
		return ListOfTickets;
	}

	public void setListOfTickets(ArrayList<Product> listOfTickets) {
		ListOfTickets = listOfTickets;
	}

	public Customer getCustomer() {
		return Customer;
	}

	public void setCustomer(Customer customer) {
		Customer = customer;
	}

	public Person getSalesperson() {
		return Salesperson;
	}

	public void setSalesperson(Person salesperson) {
		Salesperson = salesperson;
	}

	public String getInvoiceCode() {
		return InvoiceCode;
	}

	public void setInvoiceCode(String personCode) {
		this.InvoiceCode = personCode;
	}

	public ArrayList<Services> getListOfService() {
		return ListOfService;
	}

	public void setListOfService(ArrayList<Services> listOfService) {
		ListOfService = listOfService;
	}
	public void calculating(){
		Ticket ticket = null;
		subTotal = 0;
		double ticketPrice;
		for (int j = 0; j < this.ListOfTickets.size(); j++){
			ticket = (Ticket) this.ListOfTickets.get(j);
			double distance = ticket.airPortsDistance(ticket.getDepartureAirport(), ticket.getArrivalAirport());
			ticketPrice = ticket.getTicketPrice(distance);

			double tax = ticket.getTax() + (ticket.getArrivalAirport().getPassengerFacilityFee()*ticket.getNumberOfPassenger());
			double total = ticket.Total() + (ticket.getArrivalAirport().getPassengerFacilityFee()*ticket.getNumberOfPassenger());
			subTotal =  subTotal + ticketPrice;
			Taxes = Taxes+ tax;
			Total = Total + total;
		}
		for(int z =0 ; z< this.ListOfService.size(); z++){
			double distance = ticket.airPortsDistance(ticket.getDepartureAirport(), ticket.getArrivalAirport());
			double servicePrice = this.ListOfService.get(z).getServicesPrice(distance);
			double tax = this.getListOfService().get(z).getTaxes();
			double total = this.getListOfService().get(z).Total();
			subTotal =  subTotal + servicePrice;
			Taxes = Taxes+ tax;
			Total = Total + total;

		}
		if (this.getCustomer().getType().equals("V")){
			discount = Taxes;
			TOTAL = subTotal + Taxes  + Fees -  discount; 

		}
		else if (this.getCustomer().getType().equals("G")){
			TOTAL = subTotal + Taxes + Fees -  discount; 
		}
		else if (this.getCustomer().getType().equals("C")){
			discount = subTotal/100 *12;
			Fees = 40;
			TOTAL = subTotal + Taxes + Fees -  discount; 

		}






	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(double tOTAL) {
		TOTAL = tOTAL;
	}
}