package unl.cse.assignments;

import java.util.Comparator;

import com.airamerica.Invoice;

public class ComparatorTotal implements Comparator<Invoice> {

	@Override
	public int compare(Invoice inv1, Invoice inv2) {
		double total1 = inv1.getTOTAL();
		double total2 = inv2.getTOTAL();
		if(total1 == total2)
			return 0;
		else if (total1 < total2)
			return 1;
		else 
			return -1;
	}
}
