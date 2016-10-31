package unl.cse.assignments;

import java.util.Comparator;

import com.airamerica.Invoice;

public class ComparatorCustomerName implements Comparator<Invoice> {

	@Override
	public int compare(Invoice inv1, Invoice inv2) {
		return inv1.getCustomer().getName().compareTo(inv2.getCustomer().getName());
	}

}
