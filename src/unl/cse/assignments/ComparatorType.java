package unl.cse.assignments;

import java.util.Comparator;

import com.airamerica.Invoice;

public class ComparatorType implements Comparator<Invoice> {

	public int compare(Invoice inv1, Invoice inv2) {
		if (inv1.getSalesperson() == null && inv2.getSalesperson() == null){
			return inv1.getCustomer().getType().compareTo(inv2.getCustomer().getType());
		}
		else if (inv1.getSalesperson() == null){
			if(inv1.getCustomer().getType().equals(inv2.getCustomer().getType())){
				return inv2.getSalesperson().getLastName().compareTo("ONLINE");
			}
			else{
				return inv1.getCustomer().getType().compareTo(inv2.getCustomer().getType());
			}
		}
		else if (inv2.getSalesperson() == null){
			if(inv1.getCustomer().getType().equals(inv2.getCustomer().getType())){
				return inv1.getSalesperson().getLastName().compareTo("ONLINE");
			}
			else{
				return inv1.getCustomer().getType().compareTo(inv2.getCustomer().getType());
			}
		}
		else {
			if(inv1.getCustomer().getType().equals(inv2.getCustomer().getType())){
				if(inv1.getSalesperson().getLastName().equals(inv2.getSalesperson().getLastName())){
					return inv1.getSalesperson().getFirstName().compareTo(inv2.getSalesperson().getFirstName());
				}
				else 
					return inv1.getSalesperson().getLastName().compareTo(inv2.getSalesperson().getLastName());
			}
			else{
				return inv1.getCustomer().getType().compareTo(inv2.getCustomer().getType());
			}
		}

	}

}
