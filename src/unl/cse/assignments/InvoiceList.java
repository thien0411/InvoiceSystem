package unl.cse.assignments;

import java.util.Comparator; 
import java.util.Iterator;

import com.airamerica.Invoice;


public class InvoiceList<T> implements Iterator<Invoice>{
	private InvoiceListNode start;
	private InvoiceListNode end;
	private int size;



	public InvoiceList() {
		start = null;
		end = null;
		size = 0;
	}

	public boolean isEmpty() {
		return start == null;
	}

	public void clear() {
		start = null;
		end = null;
		size = 0;
	}

	public void addToStart(Invoice t) {
		InvoiceListNode aInv = new InvoiceListNode (t);
		size++;
		if (start == null){
			start = aInv;
			end = start;
		}
		else {
			aInv.setNext(start);
			start = aInv;
		}
	}

	public void addToEnd(Invoice t) {
		InvoiceListNode aInv = new InvoiceListNode (t);
		size++;
		if (start == null){
			start = aInv;
			end = start;
		}
		else {
			end.setNext(aInv);
			end = aInv;
		}
	}
	public void add(Invoice t, Comparator<Invoice> comp){
		InvoiceListNode node = new InvoiceListNode (t);

		/*Empty List*/
		if (start == null){
			start = node;
			end = start;

		}
		/*Insert at the head */
		if(comp.compare(start.getInvoice(),node.getInvoice()) > 0){
			node.setNext(start);
			start = node;

		}
		InvoiceListNode parentNode = start;
		InvoiceListNode newNode = start.getNext();
		if (newNode == null){
			start.setNext(node);
			end = node;
			end.setNext(null);
			
		}
			int i=0;
			while(newNode != null && i< size){
				i++;
				if(comp.compare(node.getInvoice(), end.getInvoice()) > 0){
					end.setNext(node);
					end = node;
				}
				else if(comp.compare(node.getInvoice(), newNode.getInvoice()) <  0){
					parentNode.setNext(node);
					node.setNext(newNode);


				}
				else{
					    parentNode = newNode; 
					    
						newNode = newNode.getNext();			
						//System.out.println("NewNode" + newNode.getInvoice());
					
				}
			}	
		
		size++;
	}
	public void remove(int position) {
		InvoiceListNode currentNode = start;
		if (position == 0){
			start = start.getNext();
			size--;
		}
		else if (size == position){
			for (int i = 0; i < position; i++){
				if (currentNode.getNext() == null){

				}
				currentNode = currentNode.getNext();
			}
			currentNode.setNext(null);
			end = currentNode;
			size--;
		}
		else {
			InvoiceListNode previousNode = start;
			for (int i = 0; i < position - 1; i++){
				if (previousNode.getNext() == null){
					System.out.println ("There is no item in the list.");
				}
				previousNode = previousNode.getNext();
			}
			InvoiceListNode nodeTobeRemoved = previousNode.getNext();
			InvoiceListNode nextNode = nodeTobeRemoved.getNext();
			previousNode.setNext(nextNode);
			end = nextNode;
			size--;
		}
	}

	private InvoiceListNode getInvoiceListNode(int position) {
		InvoiceListNode headNode = start;
		for (int i = 0; i < position; i++){
			if (headNode.getNext() == null){
				return null;
			}
			headNode = headNode.getNext();
		}
		return headNode.getNext();

	}

	public Invoice get(int position) {
		InvoiceListNode headNode = start;
		for (int i = 0; i < position; i++){
			if (headNode.getNext() == null){
				return null;
			}
			headNode = headNode.getNext();
		}
		return headNode.getInvoice();
	}

	public int getSize() {
		return size;
	}

	public void print() {
		for (int i = 0; i < size; i++){
			System.out.println(get(i).getInvoiceCode().toString());
		}
		System.out.println ("\n");
	}
	InvoiceListNode current = start;
	@Override
	public boolean hasNext() {
		return current != null;		
	}

	@Override
	public Invoice next() {
		Invoice t = current.getInvoice();
		current = current.getNext();
		return t;
	}
}
