package unl.cse.assignments;

import com.airamerica.Invoice;

public class SortedInvoice<T> {
	public String generateSummaryReport(InvoiceList invoiceList) {
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
		for(int z =0; z< invoiceList.getSize(); z++){
			String salePerson = null;
			Invoice invoice =   invoiceList.get(z);


			if (invoice.getSalesperson() == null){
				salePerson = "ONLINE";
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
	
	public static void main(String args[]) {
		DataBean.getData();
		SortedInvoice ir = new SortedInvoice();
		String summaryTotal = ir.generateSummaryReport(DataBean.trialListTotal);
		String summaryType = ir.generateSummaryReport(DataBean.trialListType);
		String summaryCustomerName = ir.generateSummaryReport(DataBean.trialListCustomer);
		System.out.println("Ordered by CustomerName(Ascending)");
		System.out.println(summaryCustomerName);	
		System.out.println("===============================================================================================================================================");
		System.out.println("\n\n");
		System.out.println("Ordered by Totals(Descending)");
		System.out.println(summaryTotal);	
		System.out.println("===============================================================================================================================================");
		System.out.println("\n\n");
		System.out.println("Ordered by CustomerType(Ascending), SalesPersonName(Ascending)");
		System.out.println(summaryType);	
		System.out.println("===============================================================================================================================================");
		System.out.println("\n\n");
		
	}

}
