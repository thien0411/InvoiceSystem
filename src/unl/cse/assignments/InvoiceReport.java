package unl.cse.assignments;
import unl.cse.assignments.DataConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jdk.internal.org.objectweb.asm.tree.InsnList;

import com.airamerica.Customer;
import com.airamerica.Invoice;
import com.airamerica.Ticket;


/* Assignment 3,5 and 6 (Project Phase-II,IV and V) */


public class InvoiceReport {
	 int i = 0;

	private String generateSummaryReport() {

		StringBuilder sb = new StringBuilder();
		sb.append("Executive Summary Report\n");
		sb.append("=========================\n");
		//TODO: Add code for generating summary of all Invoices
		double totalSubtotal = 0;
		double totalFees = 0;
		double totalTaxes = 0;
		double totalDiscount = 0;
		double total = 0;
		sb.append(String.format("%-10s %-50s %-10s %20s %10s %11s %15s %10s\n", "Invoice","Customer","SalesPerson","Subtotals","Fee","Taxes","Discount","Total"));
		for(int z =0; z< DataConverter.invoiceList.size(); z++){
			String salePerson = null;
			Invoice invoice =  DataConverter.invoiceList.get(z);
			invoice.calculating();


			if (invoice.getSalesperson() == null){
				salePerson = " Online";
			}
			else{
				salePerson= invoice.getSalesperson().getLastName() + ","+invoice.getSalesperson().getFirstName(); 
			}
			sb.append(String.format("%-10s %-49s %-23s %s %-14.2f %s %-7.2f %s %-10.2f %s -%-11.2f %s %.2f\n", invoice.getInvoiceCode(),invoice.getCustomer().getName() +"(" +invoice.getCustomer().getType()+ ")", salePerson
							,"$", invoice.getSubTotal(),"$", invoice.getFees(),"$", invoice.getTaxes(),"$", invoice.getDiscount(),"$", invoice.getTOTAL()));
			totalSubtotal += invoice.getSubTotal();
			totalFees += invoice.getFees();
			totalTaxes += invoice.getTaxes();
			totalDiscount += invoice.getDiscount();
			total += invoice.getTOTAL();
			}
		sb.append("======================================================================================================================================================\n");
		sb.append(String.format("%-84s %s %-14.2f %s %-7.2f %s %-10.2f %s -%-11.2f %s %.2f","TOTALS","$" ,totalSubtotal,"$" ,totalFees, "$", totalTaxes, "$", totalDiscount, "$", total ));
		return sb.toString();
	}

	private String getTravelSummary(Invoice invoice) {
		//TODO: Add code for generating Travel Information of an Invoice
		StringBuilder sb = new StringBuilder();
		sb.append("FLIGHT INFORMATION\n");
		sb.append(String.format("%-12s %-10s %-10s %-30s %-30s %-10s\n","Day, Date", "Flight", "Class",
				"DepartureCity and Time",
				"ArrivalCity and Time", "Aircraft"));
		Ticket ticket = null; 
		for(int z =0 ; z< invoice.getListOfTickets().size(); z++ ){
			ticket = (Ticket) invoice.getListOfTickets().get(z);
			try
			{
				String s = ticket.getTravelDate();
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dddd");
				SimpleDateFormat date1 = new SimpleDateFormat("EEE,ddMMMyy");
				Date date2 = date.parse(s);
				sb.append(String.format("%-12s %-10s %-10s %-30s %-30s %-10s", date1.format(date2),
						ticket.getFlightNumber(), ticket.getFlightClass(),
						ticket.getDepartureAirport().getAddress().getCity()+","+ ticket.getDepartureAirport().getAddress().getState(),
						ticket.getArrivalAirport().getAddress().getCity()+","+ ticket.getArrivalAirport().getAddress().getState(),ticket.getAircraftType())+ "\n");
			}
			catch (ParseException ex)
			{
				System.out.println("Exception "+ex);
			}
			try
			{
				String s = ticket.getDepTime();
				String s1 = ticket.getArrTime();
				SimpleDateFormat date = new SimpleDateFormat("HH:mm");
				SimpleDateFormat date1 = new SimpleDateFormat("hh:mma");
				Date date2 = date.parse(s);
				Date date3 = date.parse(s1);
				sb.append(String.format("%49s %29s", "("+ticket.getDepartureAirport().getAirportCode()+") "+date1.format(date2),
						"("+ticket.getArrivalAirport().getAirportCode()+") "+date1.format(date3))+"\n");
			}
			catch (ParseException ex)
			{
				System.out.println("Exception "+ex);
			}

			sb.append(String.format("           %-30s %-10s %-15s\n","Traveller","Age","SeatNo"));
			for(int j = 0; j < ticket.getListOfPassengers().size(); j++){
				sb.append(String.format("           %-30s %-10s %-15s\n",ticket.getListOfPassengers().get(j).getFirstName()+","+ticket.getListOfPassengers().get(j).getLastName(),
						ticket.getListOfPassengers().get(j).getAge(),ticket.getListOfPassengers().get(j).getSeatNumber()));

			}
			sb.append(String.format("      *%s\n", ticket.getTicketNote()));
		}

		sb.append("------------------------------------------------------------------------------------------------------------\n");
		return sb.toString();
}
		private String getCustomerSummary(Invoice invoice){
		StringBuilder sb = new StringBuilder();
		sb.append("CUSTOMER INFORMATION:\n");
		Customer customer = (Customer) invoice.getCustomer();
		sb.append(String.format("       %-15s (%-4s)\n", customer.getName(), customer.getCustomerCode()));
		if (customer.getType().equals("C")){
			sb.append(String.format("       %-10s\n", "[Corporate]"));
		}
		else if (customer.getType().equals("V")){
			sb.append(String.format("       %-10s\n", "[Goverment]"));
		}
		else if (customer.getType().equals("G")){
			sb.append(String.format("       %-10s\n", "[General]"));
		}
		sb.append("       "+customer.getPrimaryContact().getFirstName()+","+ customer.getPrimaryContact().getLastName() +"\n");
		sb.append("       "+customer.getPrimaryContact().getAddress().getStreet()+"\n");
		sb.append("       "+customer.getPrimaryContact().getAddress().getCity()+" "
						   +customer.getPrimaryContact().getAddress().getState()+" "
						   +customer.getPrimaryContact().getAddress().getZip()+" "
						   +customer.getPrimaryContact().getAddress().getCountry()+"\n");
		if (invoice.getSalesperson() != null){
		sb.append("Salesperson: "+invoice.getSalesperson().getFirstName()+", "+invoice.getSalesperson().getLastName()+"\n");
		}
		else {
			sb.append("SalesPerson: ONLINE," + invoice.getSalesperson()+"\n");
		}
		sb.append("------------------------------------------------------------------------------------------------------------\n");
		return sb.toString();
}
	
	private String getCostSummary(Invoice invoice) {
		//TODO: Add code for generating Cost Summary of all 
		//products and services in an Invoice
		StringBuilder sb = new StringBuilder();
		sb.append("FARES AND SERVICES\n");
		sb.append(String.format("%-10s %-75s %15s %10s %15s\n", "Code","Item","SubTotal","Tax","Total"));
		Ticket ticket = null;
		double ticketPrice = 0;
		for (int j = 0; j < invoice.getListOfTickets().size(); j++){
			ticket = (Ticket) invoice.getListOfTickets().get(j);
			double distance = ticket.airPortsDistance(ticket.getDepartureAirport(), ticket.getArrivalAirport());
			double ticketUnitPrice = ticket.getTicketPrice(ticket.getDistance())/ticket.getNumberOfPassenger();
			ticketPrice = ticket.getTicketPrice(ticket.getDistance());
			
			
			double tax = ticket.getTax() + (ticket.getArrivalAirport().getPassengerFacilityFee()*ticket.getNumberOfPassenger());
			double total = ticket.Total() + (ticket.getArrivalAirport().getPassengerFacilityFee()*ticket.getNumberOfPassenger());
			if(ticket.getType().equals("TS")){
				sb.append(String.format("%-10s %-82s %s %-13.2f %s %-11.2f %s %.2f\n",ticket.getTicketCode(),"StandardTicket ("+ticket.getFlightClass() +") "
						+ticket.getDepartureAirport().getAirportCode()+" to "+ticket.getArrivalAirport().getAirportCode()
						+" ("+distance+" miles)","$",ticketPrice,"$",tax,"$",total));
				sb.append(String.format("           (%d units @ $%.2f/units)\n",ticket.getNumberOfPassenger(),ticketUnitPrice));
				
			}
			if(ticket.getType().equals("TO")){
				sb.append(String.format("%-10s %-82s %s %-13.2f %s %-11.2f %s %.2f\n",ticket.getTicketCode(),"OffSeasonTicket ("+ticket.getFlightClass() +") "
						+ticket.getDepartureAirport().getAirportCode()+" to "+ticket.getArrivalAirport().getAirportCode()
						+" ("+distance+" miles) " + ticket.getRebate()*100+ 
						"% off", "$",ticketPrice,"$",tax,"$",total));
				
				sb.append(String.format("           (%d units @ $%.2f/units with $20.00 fee)\n",ticket.getNumberOfPassenger(),ticketUnitPrice));
			}
			if(ticket.getType().equals("TA")){
				sb.append(String.format("%-10s %-82s %s %-13.2f %s %-11.2f %s %.2f\n",ticket.getTicketCode(),"AwardTicket ("+ticket.getFlightClass() +") "
						+ticket.getDepartureAirport().getAirportCode()+" to "+ticket.getArrivalAirport().getAirportCode()
						+" ("+distance+" miles)","$",ticketPrice,"$",tax,"$",total));
				sb.append((String.format("           (%d units @ %d reward miles/unit with $30.0 ReedemptionFee)\n",ticket.getNumberOfPassenger(),ticket.getAwardMile(distance))));
			}

		}
		for(int z =0 ; z< invoice.getListOfService().size(); z++){
			double distance = ticket.airPortsDistance(ticket.getDepartureAirport(), ticket.getArrivalAirport());
			String temp = invoice.getListOfService().get(z).getServiceType();
			String code = invoice.getListOfService().get(z).getProductCode();
			int quantity = invoice.getListOfService().get(z).getQuantity();
			double servicePrice = invoice.getListOfService().get(z).getServicesPrice(distance);
			String serviceName = invoice.getListOfService().get(z).getServicesName();
			double taxes = invoice.getListOfService().get(z).getTaxes();
			double total = invoice.getListOfService().get(z).Total();
			if (temp.equals("SR")){
				sb.append(String.format("%-10s %-82s %s %-13.2f %s %-11.2f %s %.2f\n",code,serviceName+" (" +quantity + " units @ " +
										invoice.getListOfService().get(z).getCost()+"% off)","$",servicePrice,"$",taxes,"$",total));
			}
			if (temp.equals("SC")){
				sb.append(String.format("%-10s %-82s %s %-13.2f %s %-11.2f %s %.2f\n"
											,code,"Bagage " + "( " + quantity + " units @ 25.00 for 1st and $35.00 onwards","$",servicePrice,"$",taxes,"$",total));
			}
			if (temp.equals("SI")){
				sb.append(String.format("%-10s %-80s %s %s %-13.2f %s %-11.2f %s %.2f\n",code,"Insurance " + serviceName + " ("+invoice.getListOfService().get(z).getAge()+")"," "
						,"$",servicePrice,"$",taxes,"$",total));
				sb.append(String.format("           (%d units @ %.2f perMile x %.2f miles)\n", quantity, invoice.getListOfService().get(z).costPerMile(), ticket.getDistance()));
			}
			for(int j = 0; j < ticket.getListOfPassengers().size(); j++){
			if (temp.equals("SS")){
				
			sb.append(String.format("%-10s %-82s %s %-13.2f %s %-11.2f %s %.2f\n",code,"Special Assistance for "+ "["+ticket.getListOfPassengers().get(j).getFirstName() +
					ticket.getListOfPassengers().get(j).getLastName()+"]"+" ("+ serviceName+")","$",servicePrice,"$",taxes,"$",total));
				}
			}

			
		}
			

		
		
		
		sb.append(String.format("%134s", "=======================================\n"));
		sb.append(String.format("%-93s %s %-13.2f %s %-11.2f %s %.2f\n", "SUB-TOTALS","$", invoice.getSubTotal(),"$", invoice.getTaxes(),"$", invoice.getTotal()));
		if (invoice.getCustomer().getType().equals("V")){
			sb.append(String.format("%-123s %s -%-10.2f\n", "DISCOUNT (NO TAX)","$", invoice.getTaxes()));
			sb.append(String.format("%-123s %s %-10.2f\n", "ADDITIONAL FEE","$", invoice.getFees()));
			sb.append(String.format("%-123s %s %-10.2f\n", "TOTAL","$", invoice.getTOTAL()));

		}
		if (invoice.getCustomer().getType().equals("G")){
			sb.append(String.format("%-123s %s -%-10.2f\n", "DISCOUNT (NONE)","$", invoice.getDiscount()));
			sb.append(String.format("%-123s %s %-10.2f\n", "ADDITIONAL FEE","$", invoice.getFees()));
			sb.append(String.format("%-123s %s %-10.2f\n", "TOTAL","$", invoice.getTOTAL()));

		}
		else if (invoice.getCustomer().getType().equals("C")){
			sb.append(String.format("%-123s %s -%-10.2f\n", "DISCOUNT (12.00% of SUBTOTAL)","$",  invoice.getDiscount() ));
			sb.append(String.format("%-123s %s %-10.2f\n", "ADDITIONAL FEE","$",invoice.getFees() ));
			sb.append(String.format("%-123s %s %-10.2f\n", "TOTAL","$",invoice.getTOTAL()));
		}
		sb.append("-----------------------------------------------------------------------------------------------------------------------------------------\n\n\n");
		return sb.toString();
	}

	public String generateDetailReport(ArrayList<Invoice> invoiceList ) {

		
	StringBuilder sb = new StringBuilder();		
	sb.append("Individual Invoice Detail Reports\n");
	sb.append("==================================================\n");
	/* TODO: Loop through all invoices and call the getTravelSummary() and 
	getCostSummary() for each invoice*/
	for ( i = 0; i < DataConverter.invoiceList.size(); i++){
		Invoice invoice = invoiceList.get(i);
		sb.append("Invoice "+ invoice.getInvoiceCode() +"\n");
		sb.append("------------------------------------------------------------------------------------------------------------\n");
		sb.append(String.format("%s %80s\n" , "AIR AMERICA", "PNR"));
		try
        {
        	String s = invoice.getDate();
	        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dddd");
	        SimpleDateFormat date1 = new SimpleDateFormat("MMM dd,yyyy");
            Date date2 = date.parse(s);
            sb.append(String.format("%s %s %72s\n","IssueDate:",date1.format(date2),invoice.getPNR()));
        }
		catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
		sb.append("------------------------------------------------------------------------------------------------------------\n");
		sb.append(getTravelSummary(DataConverter.invoiceList.get(i)));
		sb.append(getCustomerSummary(DataConverter.invoiceList.get(i)));
		sb.append(getCostSummary(DataConverter.invoiceList.get(i)));
	}
	
	return sb.toString();
}

	public static void main(String args[]) {
		DataConverter.getData();
		//System.out.print(DataConverter.invoiceList);
		InvoiceReport ir = new InvoiceReport();
		String summary = ir.generateSummaryReport();
		String details = ir.generateDetailReport(DataConverter.invoiceList);
		PrintWriter w = null;
		try {
			w = new PrintWriter(new File("data/invoice.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			w.write(summary);
			w.write(details);
			w.close();
		System.out.println(summary);
		System.out.println("\n\n");
		System.out.println(details);
		
		System.out.println("======================================================================================================================");
		System.out.println("\n\n");
		
	}
}
