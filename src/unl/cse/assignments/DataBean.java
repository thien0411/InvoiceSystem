package unl.cse.assignments;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.airamerica.Address;
import com.airamerica.AirPort;
import com.airamerica.AwardTickets;
import com.airamerica.CheckedBagage;
import com.airamerica.Coordinates;
import com.airamerica.Customer;
import com.airamerica.Insurance;
import com.airamerica.Invoice;
import com.airamerica.OffSeasonTickets;
import com.airamerica.Passenger;
import com.airamerica.Person;
import com.airamerica.Product;
import com.airamerica.Refreshment;
import com.airamerica.Services;
import com.airamerica.SpecialAssistance;
import com.airamerica.StandardTickets;
import com.airamerica.Ticket;
import com.airamerica.interfaces.DatabaseInfo;
import com.airamerica.utils.StandardUtils;


public class DataBean {
    public static ArrayList<Person> peopleList = new ArrayList<Person>();
    public static ArrayList<Product> productList = new ArrayList<Product>();
    public static ArrayList<Customer> customerList = new ArrayList<Customer>();
    public static ArrayList<AirPort> airPortList = new ArrayList<AirPort>();
    public static ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
    
     //Testing new invoice list
    public static InvoiceList<Invoice> trialListTotal = new InvoiceList<Invoice>();
    public static InvoiceList<Invoice> trialListCustomer = new InvoiceList<Invoice>();
    public static InvoiceList<Invoice> trialListType = new InvoiceList<Invoice>();

    
    static Connection conn = DatabaseInfo.getConnection();

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
    
    
    
    public static void loadFilePersons(){
        PreparedStatement ps = null;
        
        String sql= " SELECT * from Person Join Address on Person.AddressID = Address.AddressID join Country on Address.CountryID = Country.CountryID ";
        ResultSet rs = null;
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                String personCode = rs.getString("PersonCode");
                // Name
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                // Address
                String street = rs.getString("Street");
                String city = rs.getString("City");
                String state = rs.getString("State");
                String zipCode = rs.getString("Zip");
                String country = rs.getString("CountryName");
                Address address1 = new Address(street,city,state,zipCode,country);//Create constructor in Address class
                String phoneNo = rs.getString("PhoneNo");
                Person person = new Person(personCode, address1, firstName, lastName, phoneNo);
                String emails = "SELECT EmailAddress from EmailAddress join Person on Person.PersonID = EmailAddress.PersonID ";
                try{
                    ps = conn.prepareStatement(emails);
                    ResultSet rsEmail = ps.executeQuery();
                    while(rsEmail.next()){
                        person.addEmail(rsEmail.getString("EmailAddress"));
                    }
                    rsEmail.close();
                } catch (SQLException e) {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                peopleList.add(person);
                
                
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
    }
    
    //Load file Airport
    public static void loadFileAirport(){
        PreparedStatement ps = null;
        String sql= " SELECT * from Airport Join Address on Airport.AddressID = Address.AddressID join Country on Address.CountryID= Country.CountryID ";
        ResultSet rs = null;
        
        // Create Strings to hold airportCode, name , address...
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                // airportCode
                String airportCode = rs.getString("AirportCode");
                //name
                String name = rs.getString("AirPortName");
                //Address
                String street = rs.getString("Street");
                String city = rs.getString("City");
                String state = rs.getString("State");
                String zipCode = rs.getString("Zip");
                String country = rs.getString("CountryName");
                Address address1 = new Address(street,city,state,zipCode,country);//Creater constructor in Address class
                //Coordinates - find longtitude and latitude
                double latdges = rs.getDouble("LatDegs");
                double latmins =rs.getDouble("LatMins");
                double londegs = rs.getDouble("LongDegs");
                double lonmins = rs.getDouble("LongMins");
                Coordinates coordinates = new Coordinates( latdges,  latmins,  londegs,  lonmins);
                double latitude = coordinates.latitudeCalculate();
                double longtitude = coordinates.longtitudeCalculate();
                //passengerFacilityFee
                double passengerFacilityFee = rs.getDouble("FacilityFee");
                AirPort airPort = new AirPort(airportCode, name, address1, latitude, longtitude, passengerFacilityFee);
                
                
                // add all the airport into list of airport
                airPortList.add(airPort);
                
            }
            rs.close();
            ps.close();
        }catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
            
        }
    }
    
    // Method to load file Customer
    public static void loadFileCustomer(){
        PreparedStatement ps = null;
        String sql= " SELECT * from Customer Join Person on Person.PersonID = Customer.PrimaryContact ";
        ResultSet rs = null;
        
        // Create Strings to hold airportCode, name , address...
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                // customerCode
                String customerCode = rs.getString("CustomerCode");
                //type
                String type= rs.getString("CustomerType");
                //primaryContact
                Person primaryContact = null;
                for(int i=0; i< peopleList.size(); i++){
                    if( peopleList.get(i).getPersonCode().equals(rs.getString("PersonCode"))){
                        primaryContact = peopleList.get(i);
                    }
                }
                //Name
                String name = rs.getString("CustomerName");
                
                //airlineMiles
                String airlineMiles;
                if(rs.getString("PointPerMiles") != null){
                    airlineMiles= rs.getString("PointPerMiles");}
                else { airlineMiles = null;}
                Customer customer = new Customer(customerCode ,primaryContact, type, name,airlineMiles );
                // add all the customers into list of customer
                customerList.add(customer);
                
            }
            rs.close();
            ps.close();
        }catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    //Method to load Products
    public static void loadFileProducts(){
        PreparedStatement ps = null;
        String sql= " SELECT * from Product";
        ResultSet rs = null;
        
        // Create Strings to hold airportCode, name , address...
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            AirPort depAirportCode = null;
            AirPort arrAirportCode = null;
            while(rs.next()){
                // productCode
                String productCode = rs.getString("ProductCode");
                //type
                String type= rs.getString("ProductType");
                
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
                for(int i=0; i< airPortList.size(); i++){
                    // departure
                    String dep= " SELECT Airport.AirportCode from Product Join Airport on Product.depAirportCodeID = Airport.AirportID where ProductCode =?";
                    ResultSet rsDep = null;
                    
                    // Create Strings to hold airportCode, name , address...
                    try {
                        ps = conn.prepareStatement(dep);
                        ps.setString(1 , rs.getString("ProductCode"));
                        rsDep = ps.executeQuery();
                        if(rsDep.next()){
                            if( airPortList.get(i).getAirportCode().equals(rsDep.getString("AirportCode"))){
                                depAirportCode = airPortList.get(i);
                            }
                        }
                        rsDep.close();
                    }catch (SQLException e) {
                        System.out.println("SQLException: ");
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    
                }
                for(int i=0; i< airPortList.size(); i++){
                    String arr= " SELECT Airport.AirportCode from Product Join Airport on Product.arrAirportCodeID = Airport.AirportID where ProductCode =?";
                    ResultSet rsArr = null;
                    
                    // Create Strings to hold airportCode, name , address...
                    try {
                        ps = conn.prepareStatement(arr);
                        ps.setString(1 , rs.getString("ProductCode"));
                        rsArr = ps.executeQuery();
                        if(rsArr.next()){
                            if( airPortList.get(i).getAirportCode().equals(rsArr.getString("AirportCode"))){
                                arrAirportCode = airPortList.get(i);
                            }
                        }
                        rsArr.close();
                    }catch (SQLException e) {
                        System.out.println("SQLException: ");
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
                if(rs.getString("ProductType").equals("TS")){
                    depTime= rs.getString("depTime");
                    arrTime= rs.getString("arrTime");
                    flightNo= rs.getString("flightNo");
                    flightClass= rs.getString("flightClass");
                    aircraftType= rs.getString("aircraftType");
                    Product standardTicket = new StandardTickets(productCode,  type,  depAirportCode,  arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType);
                    productList.add(standardTicket);
                }
                
                
                //Off season Ticket
                else if(rs.getString("ProductType").equals("TO")){
                    seasonStartDate = rs.getString("seasonStartDate");
                    seasonEndDate= rs.getString("seasonEndDate");
                    depTime= rs.getString("depTime");
                    arrTime= rs.getString("arrTime");
                    flightNo= rs.getString("flightNo");
                    flightClass= rs.getString("flightClass");
                    aircraftType= rs.getString("aircraftType");
                    rebate = rs.getDouble("rebate");
                    Product offSeasonTickets =  new OffSeasonTickets( productCode, type, seasonStartDate, seasonEndDate,  depAirportCode,  arrAirportCode,  depTime,
                                                                     arrTime,flightNo,flightClass,aircraftType, rebate);
                    productList.add(offSeasonTickets);
                    
                }
                //Award ticket
                else if(rs.getString("ProductType").equals("TA")){
                    depTime= rs.getString("depTime");
                    arrTime= rs.getString("arrTime");
                    flightNo= rs.getString("flightNo");
                    flightClass= rs.getString("flightClass");
                    aircraftType= rs.getString("aircraftType");
                    pointsPerMile =rs.getDouble("pointsPerMile");
                    Product awardTicket = new AwardTickets(productCode, type, depAirportCode,  arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType, pointsPerMile);
                    productList.add(awardTicket);
                    
                    
                }
                //Checked baggage
                else if(rs.getString("ProductType").equals("SC")){
                    String ticketCode = rs.getString("TicketCode");
                    Product checkedBaggage = new CheckedBagage(productCode, type,  ticketCode);
                    productList.add(checkedBaggage);
                    
                }
                //Insurance
                else if(rs.getString("ProductType").equals("SI")){
                    String name = rs.getString("InsuranceName");
                    String ageClass= rs.getString("ageClass");
                    double costPerMile= rs.getDouble("costPerMile");
                    Product insurance = new Insurance(productCode, type, name, ageClass, costPerMile);
                    productList.add(insurance);
                    
                }
                //Special assistance
                else if(rs.getString("ProductType").equals("SS")){
                    String typeofService=rs.getString("typeofService");
                    Product assistance = new SpecialAssistance(productCode, type, typeofService);
                    productList.add(assistance);
                    
                    
                }
                //Refreshments
                else if(rs.getString("ProductType").equals("SR")){
                    String name= rs.getString("RefreshmentName");
                    double cost= rs.getDouble("RefreshmentCost");
                    Product refreshment = new Refreshment(productCode, type, name, cost);
                    productList.add(refreshment);
                }
                
            }
            rs.close();
            ps.close();
        }catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    
    
    // Method to load Invoice
    public static void loadFileInvoice() {
        PreparedStatement ps = null;
        String sql= " SELECT * from Invoice";
        ResultSet rs = null;
        // Create Strings to hold airportCode, name , address...
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                
                ArrayList<Product> listOfTicket = new ArrayList<Product>();
                ArrayList<Services> listOfService = new ArrayList<Services>();
                String invoiceCode;
                String invoiceDate;
                Customer customer = null;
                // invoiceCode
                invoiceCode = rs.getString("InvoiceCode");
         
                //invoiceDate
                invoiceDate= rs.getString("InvoiceDate");
                //customerCode
                String customerCode = null;
                String sqlCustomer = " SELECT Customer.CustomerCode from Customer join Invoice on Invoice.CustomerCode = Customer.CustomerID where Invoice.InvoiceID = ? ";
                ResultSet rsCustomer = null;
                try {
                    ps = conn.prepareStatement(sqlCustomer);
                    ps.setString(1, rs.getString("InvoiceID"));
                    rsCustomer = ps.executeQuery();
                    if(rsCustomer.next()){
                        customerCode= rsCustomer.getString("CustomerCode"); //in person
                        rsCustomer.close();
                        
                    }
                }catch (SQLException e) {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                for(int z =0; z<customerList.size(); z++){
                    if (customerList.get(z).getCustomerCode().equals(customerCode)){
                        customer = customerList.get(z);
                    }
                }
                // salepersonCode
                Person salePerson = null;
                String sqlSalePerson = " SELECT Person.PersonCode from Person join Invoice on Invoice.SalesPerson = Person.PersonID where Invoice.InvoiceID = ? ";
                ResultSet rsSalePerson = null;
                try {
                    ps = conn.prepareStatement(sqlSalePerson);
                    ps.setString(1, rs.getString("InvoiceID"));
                    rsSalePerson = ps.executeQuery();
                    if(rsSalePerson.next()){
                        String salePersonCode = rsSalePerson.getString("PersonCode"); // in person
                        for(int z =0; z< peopleList.size(); z++){
                            if (peopleList.get(z).getPersonCode().equals(salePersonCode)){
                                salePerson= peopleList.get(z);
                                
                            }
                            else if (salePersonCode.equals("ONLINE") || salePersonCode.equals("null")){
                                salePerson = null;
                            }
                        }
                    }
                    rsSalePerson.close();
                }catch (SQLException e) {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                
                //Product
                
                
                String sqlProduct = " SELECT * from Product Join MappingTable on Product.ProductID = MappingTable.ProductID where InvoiceID = ?";
                try {
                    
                    ps = conn.prepareStatement(sqlProduct);
                    ps.setString(1, rs.getString("InvoiceID"));
                    ResultSet rsProduct = ps.executeQuery();
                    while(rsProduct.next()){
                        String ProductCode = rsProduct.getString("ProductCode");
                        String[] seatNumberArray= new String[10];
                        Ticket productReplace = null;
                        for(int s1 = 0 ; s1 < productList.size(); s1++ ){
                            if(productList.get(s1).getProductCode().equals(ProductCode)){
                                
                                //checkedBaggage
                                if(productList.get(s1).getType().equals("SC")){
                                	CheckedBagage checkedBagageReplace = (CheckedBagage) productList.get(s1).makeCopy();
                                    int quantity =rsProduct.getInt("BaggageQuantity");
                                    checkedBagageReplace.setQuantity(quantity);
                                         listOfService.add(checkedBagageReplace);
                                    
                                    
                                }
                                //refreshment
                                else if(productList.get(s1).getType().equals("SR")){
                                    Refreshment refreshmentReplace = (Refreshment)productList.get(s1).makeCopy();
                                    int quantity = rsProduct.getInt("RefreshmentQuantity");
                                    refreshmentReplace.setQuantity(quantity);
                                    listOfService.add(refreshmentReplace);
                                    
                                    
                                }
                                //special assistance
                                else if(productList.get(s1).getType().equals("SS")){
                                    listOfService.add((Services) productList.get(s1));
                                    
                                }
                                
                                //insurance
                                else if(productList.get(s1).getType().equals("SI")){
                                    
                                    Insurance insuranceReplace = (Insurance)productList.get(s1).makeCopy();
                                    int quantity = rsProduct.getInt("InsuranceQuantity");
                                    insuranceReplace.setQuantity(quantity);
                                    //String ticketCodeInsurance = insuranceArray[2];
                                    //insuranceReplace.setTicketCode(ticketCodeInsurance);
                                    listOfService.add(insuranceReplace);
                                    
                                }
                                
                                else if (productList.get(s1).getType().equals("TS")||productList.get(s1).getType().equals("TO")||productList.get(s1).getType().equals("TA")){
                                    if(productList.get(s1).getType().equals("TS")){
                                        productReplace = (StandardTickets) productList.get(s1).makeCopy();}
                                    else if(productList.get(s1).getType().equals("TO")){
                                        productReplace = (OffSeasonTickets) productList.get(s1).makeCopy();}
                                    else if(productList.get(s1).getType().equals("TA")){
                                        productReplace = (AwardTickets) productList.get(s1).makeCopy();}
                                    ArrayList<Passenger> passengerList = null;
                                    productReplace.setTicketCode(ProductCode);// in Product
                                    productReplace.setType(productList.get(s1).getType());
                                    
                                    String sqlMappingTable = " SELECT * from MappingTable Join Invoice on MappingTable.InvoiceID = Invoice.InvoiceID  join Product on Product.ProductID = MappingTable.ProductID where Product.ProductCode = ? and Invoice.InvoiceCode = ? ";
                                    try {
                                        ps = conn.prepareStatement(sqlMappingTable);
                                        ps.setString(1, ProductCode);
                                        ps.setString(2, invoiceCode);
                                        ResultSet rsMappingTable = ps.executeQuery();
                                        if(rsMappingTable.next()){
                                            String sqlnumberOfPassenger = " SELECT count(Seat) from Passenger JOIN Product ON Passenger.ProductCode = Product.ProductID JOIN Invoice ON Passenger.InvoiceID = Invoice.InvoiceID WHERE Invoice.InvoiceCode = ? AND Product.ProductCode = ?";
                                            try {
                                                ps = conn.prepareStatement(sqlnumberOfPassenger);
                                                ps.setString(1, invoiceCode);
                                                ps.setString(2, ProductCode);
                                                ResultSet rssqlOfPassenger = ps.executeQuery();
                                                if(rssqlOfPassenger.next()){
                                                    productReplace.setNumberOfPassenger(rssqlOfPassenger.getInt("count(Seat)"));
                                                }
                                            }catch (SQLException e) {
                                                System.out.println("SQLException: ");
                                                e.printStackTrace();
                                                throw new RuntimeException(e);
                                            }
                                            
                                            
                                            
                                            
                                            String ticketNote = rsMappingTable.getString("TicketNote") ;
                                            productReplace.setTicketNote(ticketNote);
                                            rsMappingTable.close();
                                        }
                                    }catch (SQLException e) {
                                        System.out.println("SQLException: ");
                                        e.printStackTrace();
                                        throw new RuntimeException(e);
                                    }
                                    String getID = "SELECT InvoiceID, ProductID FROM Invoice, Product WHERE InvoiceCode = ? AND ProductCode = ?";
                                    try {
                                        ps = conn.prepareStatement(getID);
                                        ps.setString(1, invoiceCode);
                                        ps.setString(2, ProductCode);
                                        ResultSet rsGetID;
                                        rsGetID = ps.executeQuery();
                                        while (rsGetID.next()){
                                            int invoiceID = rsGetID.getInt("InvoiceID");
                                            int productID = rsGetID.getInt("ProductID");
                                            String sqlTravelDate = " SELECT TravelDate from MappingTable WHERE InvoiceID = ? AND ProductID = ?";
                                            String travelDate = null;
                                            try {
                                                ps = conn.prepareStatement(sqlTravelDate);
                                                ps.setInt(1, invoiceID);
                                                ps.setInt(2, productID);
                                                ResultSet rsTravelDate = ps.executeQuery();
                                                if(rsTravelDate.next()){
                                                    travelDate = rsTravelDate.getString("TravelDate");
                                                    productReplace.setTravelDate(travelDate);
                                                    
                                                    
                                                    String sqlPassenger = " SELECT Passenger.* from Passenger Join Person on Person.PersonID = Passenger.PersonalCode "
                                                    + "join Product on Product.ProductID = Passenger.ProductCode where Passenger.InvoiceID = ? and Product.ProductCode= ? ";
                                                    try {
                                                        ps = conn.prepareStatement(sqlPassenger);
                                                        ps.setString(1, rs.getString("InvoiceID"));
                                                        ps.setString(2, ProductCode);
                                                        
                                                        ResultSet rsPassenger = ps.executeQuery();
                                                        passengerList = new ArrayList<Passenger>();
                                                        while(rsPassenger.next()){
                                                            
                                                            int i = 0;
                                                            seatNumberArray[i] = rsPassenger.getString("Seat");
                                                            i++;
                                                            String seatNumber = rsPassenger.getString("Seat");
                                                            String sqlPersonCode = " SELECT Person.PersonCode from Person where PersonID = ? ";
                                                            String personCode = null;
                                                            try {
                                                                ps = conn.prepareStatement(sqlPersonCode);
                                                                ps.setString(1, rsPassenger.getString("PersonalCode"));
                                                                ResultSet rsPersonCode = ps.executeQuery();
                                                                if(rsPersonCode.next()){
                                                                    personCode = rsPersonCode.getString("PersonCode");
                                                                    rsPersonCode.close();
                                                                }
                                                            }
                                                            catch (SQLException e) {
                                                                System.out.println("SQLException: ");
                                                                e.printStackTrace();
                                                                throw new RuntimeException(e);
                                                            }
                                                            String identityCode = rsPassenger.getString("IdentityNumber");
                                                            String age = rsPassenger.getString("Age");
                                                            String nationality = rsPassenger.getString("Nationality");
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
                                                        rsPassenger.close();
                                                    }catch (SQLException e) {
                                                        System.out.println("SQLException: ");
                                                        e.printStackTrace();
                                                        throw new RuntimeException(e);
                                                    }
                                                }
                                                rsTravelDate.close();
                                            }catch (SQLException e) {
                                                System.out.println("SQLException: ");
                                                e.printStackTrace();
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    } catch (SQLException e) {
                                        System.out.println("SQLException: ");
                                        e.printStackTrace();
                                        throw new RuntimeException(e);
                                    }
                                    productReplace.setListOfPassengers(passengerList);
                                    productReplace.setListOfSeatNumber(seatNumberArray);
                                    listOfTicket.add(productReplace);
                                }
                                
                            }
                            
                        }
                    }
                }catch (SQLException e) {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                String PNR = StandardUtils.generatePNR();
                Invoice invoice = new Invoice(invoiceCode, invoiceDate, PNR,
                                              listOfTicket, customer,
                                              salePerson, listOfService);
                
                invoice.calculating();
                // Customer customer
                ComparatorTotal total = new ComparatorTotal();
                ComparatorType type = new ComparatorType();
                ComparatorCustomerName customerName = new ComparatorCustomerName();

                trialListTotal.add(invoice, total);
                trialListType.add(invoice, type);
                trialListCustomer.add(invoice, customerName);
                invoiceList.add(invoice);
                
            }
            
            
            rs.close();
            ps.close();
        }catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}




