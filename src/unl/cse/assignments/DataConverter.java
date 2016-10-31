package unl.cse.assignments;

/* Phase-I */
import com.airamerica.*;
import com.airamerica.utils.StandardUtils;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;










import org.joda.time.DateTime;






// Include imports for XML/JSON libraries if needed
import com.thoughtworks.xstream.XStream;
public class DataConverter {
	public static ArrayList<Person> peopleList = new ArrayList<Person>();
	public static ArrayList<Product> productList = new ArrayList<Product>();
	public static ArrayList<Customer> customerList = new ArrayList<Customer>();
	public static ArrayList<AirPort> airPortList = new ArrayList<AirPort>();
	public static ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();

	//Getter and Setter
	public static ArrayList<Invoice> getInvoiceList() {
		return invoiceList;
	}




	public static void setInvoiceList(ArrayList<Invoice> invoiceList) {
		DataConverter.invoiceList = invoiceList;
	}



	public static void getData() {

		// TODO: Add your code to read data from .dat files, create objects
		//and export them as XML or JSON 
		loadFilePersons();
		loadFileAirport();
		loadFileCustomer();
		loadFileProducts();
		loadFileInvoice();
	}









	// Method to load file Person
	public static void loadFilePersons(){
		Scanner s= null;
		try {
			s = new Scanner(new File("data/Persons.dat"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		// Create Strings to hold PersonCode, firstName, lastName...
		
		while(s.hasNext()){
			String line= s.nextLine();
			String tokens[]= line.split(";");
			String personCode = tokens[0];
			// Name
			String name[]= tokens[1].split(",");
			String firstName = name[0];
			String lastName = name[1];
			// Address
			String address[] = tokens[2].split(",");
			String street = address[0];
			String city = address[1];
			String state = address[2];
			String zipCode = address[3];
			String country = address[4];
			Address address1 = new Address(street,city,state,zipCode,country);//Create constructor in Address class
			address1.setStreet(street);										  //Add address to address class
			address1.setCity(city);
			address1.setState(state);
			address1.setZip(zipCode);
			address1.setCountry(country);
			String phoneNo = tokens[3];
			Person person = new Person(personCode, address1, firstName, lastName, phoneNo);
			if (tokens.length == 5 ){
				String emailaddresses[] = (tokens[4].split(","));
				for (int emailLength = 0; emailLength< emailaddresses.length; emailLength++){
					person.addEmail(emailaddresses[emailLength]);
				}
			} 
			peopleList.add(person);


		}
	}




	//Method to load AirPort Data
	public static void loadFileAirport(){
		Scanner s= null;
		try {
			s = new Scanner(new File("data/Airports.dat"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		// Create Strings to hold airportCode, name , address...
		while(s.hasNext()){
			String line= s.nextLine();
			String tokens[]= line.split(";");
			// airportCode
			String airportCode = tokens[0];
			//name
			String name = tokens[1];
			//Address
			String address[] = tokens[2].split(",");
			String street = address[0];
			String city = address[1];
			String state = address[2];
			String zipCode = address[3];
			String country = address[4];
			Address address1 = new Address(street,city,state,zipCode,country);//Creater constructor in Address class
			//Coordinates - find longtitude and latitude
			String coordinatesArr[] = tokens[3].split(","); 
			double latdges = Double.parseDouble(coordinatesArr[0]);
			double latmins = Double.parseDouble(coordinatesArr[1]);
			double londegs = Double.parseDouble(coordinatesArr[2]);
			double lonmins = Double.parseDouble(coordinatesArr[3]);
			Coordinates coordinates = new Coordinates( latdges,  latmins,  londegs,  lonmins);
			double latitude = coordinates.latitudeCalculate();
			double longtitude = coordinates.longtitudeCalculate();
			//passengerFacilityFee
			double passengerFacilityFee = Double.parseDouble(tokens[4]);
			AirPort airPort = new AirPort(airportCode, name, address1, latitude, longtitude, passengerFacilityFee);


			// add all the airport into list of airport
			airPortList.add(airPort);
			XStream xstream = new XStream();
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new File("data/airPort-example.xml"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			pw.print("<airPorts>\n");
			for(int i=0; i<airPortList.size(); i++){
				xstream.alias("airPort", AirPort.class);
				pw.print(xstream.toXML(airPortList.get(i)) + "\n");
			}
			pw.print("</airPorts>" + "\n");
			pw.close();
		}
	}





	// Method to load file Customer
	public static void loadFileCustomer(){
		Scanner s= null;
		try {
			s = new Scanner(new File("data/Customers.dat"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		// Create Strings to hold PersonCode, firstName, lastName...
		while(s.hasNext()){
			String line= s.nextLine();
			String tokens[]= line.split(";");
			// customerCode
			String customerCode = tokens[0];
			//type
			String type= tokens[1];
			//primaryContact
			Person primaryContact = null;
			for(int i=0; i< peopleList.size(); i++){
				if( peopleList.get(i).getPersonCode().equals(tokens[2])){
					primaryContact = peopleList.get(i);
				}
			}
			//Name	
			String name = tokens[3];

			//airlineMiles
			String airlineMiles;
			if(tokens.length == 5){
				airlineMiles= tokens[4];}
			else { airlineMiles = null;}
			Customer customer = new Customer(customerCode ,primaryContact, type, name,airlineMiles );
			// add all the customers into list of customer
			customerList.add(customer);
			XStream xstream = new XStream();
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new File("data/Customer-example.xml"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			pw.print("<customers>\n");
			for(int i=0; i<customerList.size(); i++){
				xstream.alias("customer", Customer.class);
				pw.print(xstream.toXML(customerList.get(i)) + "\n");
			}
			pw.print("</customers>" + "\n");
			pw.close();
		}
	}






	//Method to load Products
	public static void loadFileProducts(){
		Scanner s= null;
		try {
			s = new Scanner(new File("data/Products.dat"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		// Create Strings to hold PersonCode, firstName, lastName...
		AirPort depAirportCode = null;
		AirPort arrAirportCode = null;
		while(s.hasNext()){
			String line= s.nextLine();
			String tokens[]= line.split(";");
			// productCode
			String productCode = tokens[0];
			//type
			String type= tokens[1];

			//declare all the variables
			String seasonStartDate;
			String seasonEndDate;
			String depTime;
			String arrTime;
			String flightNo;
			String flightClass;
			String aircraftType;
			double rebate;
			double pointsPerMile;
			//Comparing the services
			//Standard ticket
			if(type.equals("TS")){
				for(int i=0; i< airPortList.size(); i++){
					if( airPortList.get(i).getAirportCode().equals(tokens[2])){

						depAirportCode = airPortList.get(i);
					}

					else if( airPortList.get(i).getAirportCode().equals(tokens[3])){
						arrAirportCode= airPortList.get(i);
					}

				}
				depTime= tokens[4];
				arrTime=tokens[5];
				flightNo= tokens[6];
				flightClass= tokens[7];
				aircraftType=tokens[8]; 				
				Product standardTicket = new StandardTickets(productCode,  type,  depAirportCode,  arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType);
				productList.add(standardTicket);


			}
			//Off season Ticket
			else if(type.equals("TO")){
				seasonStartDate = tokens[2];
				seasonEndDate= tokens[3];
				for(int i=0; i< airPortList.size(); i++){
					if( airPortList.get(i).getAirportCode().equals(tokens[4])){
						depAirportCode = airPortList.get(i);
					}

					if( airPortList.get(i).getAirportCode().equals(tokens[5])){
						arrAirportCode= airPortList.get(i);}

				}
				depTime= tokens[6];
				arrTime=tokens[7];
				flightNo= tokens[8];
				flightClass= tokens[9];
				aircraftType=tokens[10]; 
				rebate = Double.parseDouble(tokens[11]);
				Product offSeasonTickets =  new OffSeasonTickets( productCode, type, seasonStartDate, seasonEndDate,  depAirportCode,  arrAirportCode,  depTime,
						arrTime,flightNo,flightClass,aircraftType, rebate);
				productList.add(offSeasonTickets);

			}
			//Award ticket
			else if(type.equals("TA")){
				for(int i=0; i< airPortList.size(); i++){
					if( airPortList.get(i).getAirportCode().equals(tokens[2])){
						depAirportCode = airPortList.get(i);
					}

					if( airPortList.get(i).getAirportCode().equals(tokens[3])){
						arrAirportCode= airPortList.get(i);}

				}
				depTime= tokens[4];
				arrTime=tokens[5];
				flightNo= tokens[6];
				flightClass= tokens[7];
				aircraftType=tokens[8]; 
				pointsPerMile =Double.parseDouble(tokens[9]);
				Product awardTicket = new AwardTickets(productCode, type, depAirportCode,  arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType, pointsPerMile);
				productList.add(awardTicket);


			}
			//Checked baggage
			else if(type.equals("SC")){
				String ticketCode = tokens[2];
				Product checkedBaggage = new CheckedBagage(productCode, type,  ticketCode);
				productList.add(checkedBaggage);

			}
			//Insurance
			else if(type.equals("SI")){
				String name = tokens[2];
				String ageClass= tokens[3];
				double costPerMile=Double.parseDouble(tokens[4]);
				Product insurance = new Insurance(productCode, type, name, ageClass, costPerMile); 
				productList.add(insurance);

			}
			//Special assistance
			else if(type.equals("SS")){
				String typeofService=tokens[2];
				Product assistance = new SpecialAssistance(productCode, type, typeofService);
				productList.add(assistance);


			}
			//Refreshments
			else if(type.equals("SR")){
				String name= tokens[2];
				double cost=Double.parseDouble(tokens[3]);
				Product refreshment = new Refreshment(productCode, type, name, cost);
				productList.add(refreshment);
			}

		}


		XStream xstream = new XStream();
		xstream.alias("product", Product.class);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("data/Product-example.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		pw.print("<products>\n");
		for(int i=0; i<productList.size(); i++){
			//pw.print(xstream.toXML(productList.get(i)) + "\n");

			//pw.print("</products>" + "\n");

			if (productList.get(i).getType().equals("TS")){
				//xstream.alias("product", StandardTickets.class);
				pw.print("<standardTicket>\n");
				pw.print(xstream.toXML(productList.get(i)) + "\n");
				pw.print("</standardTicket>\n");

			}
			else if (productList.get(i).getType().equals("TO")){
				//xstream.alias("product", OffSeasonTickets.class);
				pw.print("<offSeasonTicket>\n");
				pw.print(xstream.toXML(productList.get(i)) + "\n");
				pw.print("</offSeasonTicket>" + "\n");
			}
			else if (productList.get(i).getType().equals("TA")){
				//xstream.alias("product", AwardTickets.class);
				pw.print("<awardTicket>\n");
				pw.print(xstream.toXML(productList.get(i)) + "\n");
				pw.print("</awardTicket>" + "\n");
			}
			else if (productList.get(i).getType().equals("SC")){
				//xstream.alias("product", CheckedBagage.class);
				pw.print("<checkedbagage>\n");
				pw.print(xstream.toXML(productList.get(i)) + "\n");
				pw.print("</checkedbagage>" + "\n");
			}
			else if (productList.get(i).getType().equals("SI")){
				//xstream.alias("product", Insurance.class);
				pw.print("<insurance>\n");
				pw.print(xstream.toXML(productList.get(i)));
				pw.print("</insurance>" + "\n");
			}
			else if (productList.get(i).getType().equals("SS")){
				//xstream.alias("product", SpecialAssistance.class);
				pw.print("<special assistance>\n");
				pw.print(xstream.toXML(productList.get(i)) + "\n");
				pw.print("</special assistance>" + "\n");
			}
			else if (productList.get(i).getType().equals("SR")){
				//xstream.alias("product", Refreshment.class);
				pw.print("<refreshment>\n");
				pw.print(xstream.toXML(productList.get(i)) + "\n");
				pw.print("</refreshment>" + "\n");
			}
		}
		pw.print("</products>" + "\n");
		pw.close();


	}

	// Method to load Invoice
	public static void loadFileInvoice() {
		Scanner s= null;
		try {
			s = new Scanner(new File("data/Invoices.dat"));
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}

		// Create Strings to hold PersonCode, firstName, lastName...
		while(s.hasNext()){
			ArrayList<Product> listOfTicket = new ArrayList<Product>(); 
			ArrayList<Services> listOfService = new ArrayList<Services>(); 

			String line= s.nextLine();
			String tokens[]= line.split(";");
			// invoiceCode
			String invoiceCode = tokens[0];
			//customerCode
			String customerCode= tokens[1];
			Customer customer = null;
			for(int z =0; z<customerList.size(); z++){
				if (customerList.get(z).getCustomerCode().equals(customerCode)){
					customer = customerList.get(z);
				}
			}
			// salepersonCode
			String salePersonCode = tokens[2];
			Person salePerson = null;
			for(int z =0; z< peopleList.size(); z++){
				if (peopleList.get(z).getPersonCode().equals(salePersonCode)){
					salePerson= peopleList.get(z);
				}
				else if (salePersonCode.equals("online")){
					salePerson = null;
				}
			}
			//invoiceDate
			String invoiceDate= tokens[3];
			//Product
			String stringProduct[] = tokens[4].split(",");

			for(int a = 0; a<stringProduct.length; a++){
				String eachPartOfProduct[] = stringProduct[a].split(":");//This is used to check if the product has more than 3 attributes
				// get ticket
				if(eachPartOfProduct.length>=8){
					//System.out.println("count" );
					String ticket[]= stringProduct[a].split(":");
					String ticketCode = ticket[0];
					String[] seatNumberArray= new String[10];
					for(int s1 = 0 ; s1 < productList.size(); s1++ ){
						if(productList.get(s1).getProductCode().equals(ticketCode)){
							ArrayList<Passenger> passengerList = new ArrayList<Passenger>();
							Ticket productReplace = null;
							if(productList.get(s1).getType().equals("TS")){
								productReplace = (StandardTickets) productList.get(s1).makeCopy();}
							else if(productList.get(s1).getType().equals("TO")){
								productReplace = (OffSeasonTickets) productList.get(s1).makeCopy();}
							else if(productList.get(s1).getType().equals("TA")){
								productReplace = (AwardTickets) productList.get(s1).makeCopy();}
							productReplace.setTicketCode(ticket[0]);
							productReplace.setTravelDate(ticket[1]);
							productReplace.setType(productList.get(s1).getType());
							int numberOfPassenger= Integer.parseInt(ticket[2]);
							productReplace.setNumberOfPassenger(numberOfPassenger);
							for(int i = 0; i < numberOfPassenger; i++){
								seatNumberArray[i] = ticket[3+i*5];
								String seatNumber = ticket[3+i*5];
								String personCode = ticket[4+i*5];
								String identityCode = ticket[5+i*5];
								String age = ticket[6+i*5];
								String nationality = ticket[7+i*5];
								for(int z =0; z< peopleList.size(); z++){
									if (peopleList.get(z).getPersonCode().equals(personCode)){
										Address address = peopleList.get(z).getAddress();
										String firstName= peopleList.get(z).getFirstName();
										String lastName= peopleList.get(z).getLastName();
										String phoneNumber= peopleList.get(z).getPhoneNumber();
										Passenger passenger= new Passenger(seatNumber, personCode, identityCode, age, nationality,address, firstName, lastName,phoneNumber );
										passengerList.add(passenger);
									}
								}
							}
							String ticketNote = ticket[ticket.length-1] ;
							productReplace.setTicketNote(ticketNote);
							productReplace.setListOfPassengers(passengerList);
							productReplace.setListOfSeatNumber(seatNumberArray);
							listOfTicket.add(productReplace);
						}
					}
				}
				//checkedBaggage
				else if(eachPartOfProduct.length==2){
					String checkedBaggage[]=stringProduct[a].split(":");//In this checked baggage will include checked baggage and refreshment
					String productCode = checkedBaggage[0];

					for(int s1 = 0 ; s1 < productList.size(); s1++ ){
						if(productList.get(s1).getProductCode().equals(productCode) && checkedBaggage[1].length()==1){
							int quantity = Integer.parseInt(checkedBaggage[1]);
							((Services) productList.get(s1)).setQuantity(quantity);
							listOfService.add((Services) productList.get(s1));
						}
						else if (productList.get(s1).getProductCode().equals(productCode) && checkedBaggage[1].length() <= 5 && checkedBaggage[1].length() >= 2){
							((Services) productList.get(s1)).setPersonCode(checkedBaggage[1]);
							listOfService.add((Services) productList.get(s1));
						}
					}
				}
				//insurance
				else if(eachPartOfProduct.length==3){
					String insuranceArray[]= stringProduct[a].split(":");
					String productCodeInsurance = insuranceArray[0];
					for(int s1 = 0 ; s1 < productList.size(); s1++ ){
						if(productList.get(s1).getProductCode().equals(productCodeInsurance)){
							Insurance insuranceReplace = (Insurance)productList.get(s1).makeCopy();
							int quantity = Integer.parseInt(insuranceArray[1]);
							insuranceReplace.setQuantity(quantity);
							String ticketCodeInsurance = insuranceArray[2]; 
							insuranceReplace.setTicketCode(ticketCodeInsurance);
							listOfService.add(insuranceReplace);
						}
					}
				}				
			}

			String PNR = StandardUtils.generatePNR();
			Invoice invoice = new Invoice(invoiceCode, invoiceDate, PNR,
					listOfTicket, customer,
					salePerson, listOfService); // Customer customer 
			invoiceList.add(invoice);
		}
	}
}



