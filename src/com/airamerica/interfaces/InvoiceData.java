package com.airamerica.interfaces;
/* Assignment 5 - (Phase IV) */
/* NOTE: Donot change the package name or any of the method signatures.
 *
 * There are 23 methods in total, all of which need to be completed as a
 * bare minimum as part of the assignment.You can add additional methods
 * for testing if you feel.
 *
 * It is also recommended that you write a separate program to read
 * from the .dat files and test these methods to insert data into your
 * database.
 *
 * Donot forget to change your reports generation classes to read from
 * your database instead of the .dat files.
 */
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {
    public static Connection conn = DatabaseInfo.getConnection();
    public static PreparedStatement ps;
    public static ResultSet rs;
    /**
     * Method that removes every person record from the database
     */
    public static void removeAllPersons() {
        PreparedStatement ps1;
        String deleteEmail = "DELETE FROM EmailAddress";
        String deletePerson = "DELETE FROM Person";
        try {
            ps1 = conn.prepareStatement(deleteEmail);
            ps = conn.prepareStatement(deletePerson);
            ps1.executeUpdate();
            ps.executeUpdate();
            ps1.close();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Method to add a person record to the database with the provided data.
     */
    public static void addPerson(String personCode, String firstName, String lastName,
                                 String phoneNo, String street, String city, String state,
                                 String zip, String country) {
        int countryID = 0;
        int addressID = 0;
        try {
            String getCountryID = "SELECT CountryID FROM Country WHERE CountryName = ?";
            ps = conn.prepareStatement(getCountryID);
            ps.setString(1, country);
            rs = ps.executeQuery();
            if (!rs.next()){
                try {
                    String insertCountry = "INSERT INTO Country (CountryName) VALUES (?)";
                    ps = conn.prepareStatement(insertCountry);
                    ps.setString(1, country);
                    ps.executeUpdate();
                    
                    ps = conn.prepareStatement(getCountryID);
                    ps.setString(1, country);
                    rs = ps.executeQuery();
                    rs.next();
                    countryID = rs.getInt("CountryID");
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            else {
                countryID = rs.getInt("CountryID");
            }
            try {
                String getAddressID = "SELECT AddressID FROM Address WHERE Street = ? AND City = ? AND State = ? AND Zip = ?";
                ps = conn.prepareStatement(getAddressID);
                ps.setString(1, street);
                ps.setString(2, city);
                ps.setString(3, state);
                ps.setString(4, zip);
                rs = ps.executeQuery();
                if(!rs.next()){
                    try {
                        String insertAddress = "INSERT INTO Address (Street, City, State, Zip, CountryID) VALUES (?, ?, ?, ?, ?)";
                        ps = conn.prepareStatement(insertAddress);
                        ps.setString(1, street);
                        ps.setString(2,city);
                        ps.setString(3, state);
                        ps.setString(4, zip);
                        ps.setInt(5, countryID);
                        ps.executeUpdate();
                        
                        ps = conn.prepareStatement(getAddressID);
                        ps.setString(1, street);
                        ps.setString(2, city);
                        ps.setString(3, state);
                        ps.setString(4, zip);
                        rs = ps.executeQuery();
                        rs.next();
                        addressID = rs.getInt("Address.AddressID");
                    } catch (SQLException e)
                    {
                        System.out.println("SQLException: ");
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
                else {
                    addressID = rs.getInt("Address.AddressID");
                }
                try {
                    String getPersonID = "SELECT PersonID FROM Person WHERE PersonCode = ?";
                    ps.setString(1, personCode);
                    rs = ps.executeQuery();
                    if (!rs.next()){
                        try {
                            String insertPerson = "INSERT INTO Person (PersonCode, FirstName, LastName, PhoneNo, AddressID) VALUES (?, ?, ?, ?, ?)";
                            ps = conn.prepareStatement(insertPerson);
                            ps.setString(1, personCode);
                            ps.setString(2, firstName);
                            ps.setString(3, lastName);
                            ps.setString(4, phoneNo);
                            ps.setInt(5, addressID);
                            ps.executeUpdate();
                        } catch (SQLException e)
                        {
                            System.out.println("SQLException: ");
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e)
            {
                System.out.println("SQLException: ");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        /*String getPersonID = "SELECT PersonID FROM Person WHERE PersonCode = ?";
         try {
         ps = conn.prepareStatement(getPersonID);
         ps.setString(1, "online");
         rs = ps.executeQuery();
         if(!rs.next()){
         String insertPerson = "INSERT INTO Person (PersonCode) VALUES (?)";
         try {
         ps = conn.prepareStatement(insertPerson);
         ps.setString(1, "online");
         ps.executeUpdate();
         } catch (SQLException e)
         {
         System.out.println("SQLException: ");
         e.printStackTrace();
         throw new RuntimeException(e);
         }
         }
         } catch (SQLException e)
         {
         System.out.println("SQLException: ");
         e.printStackTrace();
         throw new RuntimeException(e);
         }*/
    }
    /**
     * Method that removes every airport record from the database
     */
    public static void removeAllAirports() {
        removeAllProducts();
        String DeleteAirport = "DELETE FROM Airport";
        try {
            ps = conn.prepareStatement(DeleteAirport);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Method to add a airport record to the database with the provided data.
     */
    public static void addAirport(String airportCode, String name, String street,
                                  String city, String state, String zip, String country,
                                  int latdegs, int latmins, int londegs, int lonmins,
                                  double passengerFacilityFee) {
        int countryID = 0;
        int addressID = 0;
        String getCountryID = "SELECT CountryID FROM Country WHERE CountryName = ?";
        try {
            ps = conn.prepareStatement(getCountryID);
            ps.setString(1, country);
            rs = ps.executeQuery();
            if (!rs.next()){
                String addCountry = "INSERT INTO Country (CountryName) VALUES (?)";
                try {
                    ps = conn.prepareStatement(addCountry);
                    ps.setString(1, country);
                    ps.executeUpdate();
                    
                    ps = conn.prepareStatement(getCountryID);
                    ps.setString(1, country);
                    rs = ps.executeQuery();
                    rs.next();
                    countryID = rs.getInt("CountryID");
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            else {
                countryID = rs.getInt("CountryID");
                String getAddressID = "SELECT AddressID FROM Address WHERE Street = ? AND City = ? AND State = ? AND Zip = ?";
                try {
                    ps = conn.prepareStatement(getAddressID);
                    ps.setString(1, street);
                    ps.setString(2, city);
                    ps.setString(3, state);
                    ps.setString(4, zip);
                    rs = ps.executeQuery();
                    if (!rs.next()){
                        String insertAddress = "INSERT INTO Address (street, city, state, zip, CountryID) VALUES (?, ?, ?, ?, ?)";
                        try {
                            ps = conn.prepareStatement(insertAddress);
                            ps.setString(1, street);
                            ps.setString(2, city);
                            ps.setString(3, state);
                            ps.setString(4, zip);
                            ps.setInt(5, countryID);
                            ps.executeUpdate();
                            
                            ps = conn.prepareStatement(getAddressID);
                            ps.setString(1, street);
                            ps.setString(2, city);
                            ps.setString(3, state);
                            ps.setString(4, zip);
                            rs = ps.executeQuery();
                            rs.next();
                            addressID = rs.getInt("AddressID");
                        } catch (SQLException e)
                        {
                            System.out.println("SQLException: ");
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        addressID = rs.getInt("AddressID");
                        String getAirportID = "SELECT AirportID FROM Airport WHERE AirportCode = ? AND AirportName = ? AND LatDegs = ? AND LatMins = ? AND LongDegs = ? AND LongMins = ? AND FacilityFee = ?";
                        try {
                            ps = conn.prepareStatement(getAirportID);
                            ps.setString(1, airportCode);
                            ps.setString(2, name);
                            ps.setInt(3, latdegs);
                            ps.setInt(4, latmins);
                            ps.setInt(5, londegs);
                            ps.setInt(6, lonmins);
                            ps.setDouble(7, passengerFacilityFee);
                            rs = ps.executeQuery();
                            if (!rs.next()){
                                String insertAirport = "INSERT INTO Airport (AirportCode, AirportName, LatDegs, LatMins, LongDegs, LongMins, FacilityFee, AddressID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                try {
                                    ps = conn.prepareStatement(insertAirport);
                                    ps.setString(1, airportCode);
                                    ps.setString(2, name);
                                    ps.setInt(3, latdegs);
                                    ps.setInt(4, latmins);
                                    ps.setInt(5, londegs);
                                    ps.setInt(6, lonmins);
                                    ps.setDouble(7, passengerFacilityFee);
                                    ps.setInt(8, addressID);
                                    ps.executeUpdate();
                                } catch (SQLException e)
                                {
                                    System.out.println("SQLException: ");
                                    e.printStackTrace();
                                    throw new RuntimeException(e);
                                }
                            }
                        } catch (SQLException e)
                        {
                            System.out.println("SQLException: ");
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds an email record corresponding person record corresponding to the
     * provided <code>personCode</code>
     */
    public static void addEmail(String personCode, String email) {
        int personID;
        String getPersonID = "SELECT PersonID FROM Person WHERE PersonCode = ?";
        try {
            ps = conn.prepareStatement(getPersonID);
            ps.setString(1, personCode);
            rs = ps.executeQuery();
            while (rs.next()){
                personID = rs.getInt("PersonID");
                String insertEmail = "INSERT INTO EmailAddress (EmailAddress, PersonID) VALUES (?, ?)";
                try {
                    ps = conn.prepareStatement(insertEmail);
                    ps.setString(1, email);
                    ps.setInt(2, personID);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Method that removes every customer record from the database
     */
    public static void removeAllCustomers() {
        String DeleteCustomer = "DELETE FROM Customer";
        try {
            ps = conn.prepareStatement(DeleteCustomer);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Method to add a customer record to the database with the provided data.
     */
    public static void addCustomer(String customerCode, String customerType,
                                   String primaryContactPersonCode, String name,
                                   int airlineMiles) {
        String getPersonID = "SELECT PersonID FROM Person WHERE PersonCode = ?";
        try {
            ps = conn.prepareStatement(getPersonID);
            ps.setString(1, primaryContactPersonCode);
            rs = ps.executeQuery();
            rs.next();
            int personID = rs.getInt("PersonID");
            String insertCustomer = "INSERT INTO Customer (CustomerCode, CustomerType, PrimaryContact, CustomerName, PointPerMiles) VALUES (?, ?, ?, ?, ?)";
            try {
                ps = conn.prepareStatement(insertCustomer);
                ps.setString(1, customerCode);
                ps.setString(2, customerType);
                ps.setInt(3, personID);
                ps.setString(4, name);
                ps.setInt(5, airlineMiles);
                ps.executeUpdate();
            } catch (SQLException e)
            {
                System.out.println("SQLException: ");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Removes all product records from the database
     */
    public static void removeAllProducts() {
        removeAllInvoices();
        String DeleteProduct = "DELETE FROM Product";
        try {
            ps = conn.prepareStatement(DeleteProduct);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds an standardTicket record to the database with the
     * provided data.
     */
    public static void addStandardTicket(String productCode,String depAirportCode,
                                         String arrAirportCode, String depTime, String arrTime,
                                         String flightNo, String flightClass, String aircraftType) {
        String getDepAirportID = "SELECT AirportID FROM Airport WHERE AirportCode = ?";
        int depAirportCodeID = 0;
        int arrAirportCodeID = 0;
        try {
            ps = conn.prepareStatement(getDepAirportID);
            ps.setString(1, depAirportCode);
            rs = ps.executeQuery();
            if (rs.next()){
                depAirportCodeID = rs.getInt("AirportID");
            }
        }catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String getArrAirportID = "SELECT AirportID FROM Airport WHERE AirportCode = ?";
        ResultSet rs1 = null;
        try {
            ps = conn.prepareStatement(getArrAirportID);
            ps.setString(1, arrAirportCode);
            rs1 = ps.executeQuery();
            if (rs1.next()){
                arrAirportCodeID = rs1.getInt("AirportID");
            }
        }catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String getProductID = "SELECT ProductID FROM Product WHERE ProductCode = ?";
        try {
            ps = conn.prepareStatement(getProductID);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            if (!rs.next()){
                String insertStandardTicket = "INSERT INTO Product (ProductCode, ProductType, depAirportCodeID, arrAirportCodeID, depTime, arrTime, flightNo, flightClass, aircraftType)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertStandardTicket);
                    ps.setString(1, productCode);
                    ps.setString(2, "TS");
                    ps.setInt(3, depAirportCodeID);
                    ps.setInt(4, arrAirportCodeID);
                    ps.setString(5, depTime);
                    ps.setString(6, arrTime);
                    ps.setString(7, flightNo);
                    ps.setString(8, flightClass);
                    ps.setString(9, aircraftType);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * Adds an offSeasonTicket record to the database with the
     * provided data.
     */
    public static void addOffSeasonTicket(String productCode, String seasonStartDate,
                                          String seasonEndDate, String depAirportCode, String arrAirportCode,
                                          String depTime, String arrTime,	String flightNo, String flightClass,
                                          String aircraftType, double rebate) {
        String getDepAirportID = "SELECT AirportID FROM Airport WHERE AirportCode = ?";
        int depAirportCodeID = 0;
        int arrAirportCodeID = 0;
        try {
            ps = conn.prepareStatement(getDepAirportID);
            ps.setString(1, depAirportCode);
            rs = ps.executeQuery();
            if (rs.next()){
                depAirportCodeID = rs.getInt("AirportID");
            }
        }catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String getArrAirportID = "SELECT AirportID FROM Airport WHERE AirportCode = ?";
        ResultSet rs1 = null;
        try {
            ps = conn.prepareStatement(getArrAirportID);
            ps.setString(1, arrAirportCode);
            rs1 = ps.executeQuery();
            if (rs1.next()){
                arrAirportCodeID = rs1.getInt("AirportID");
            }
        }catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String insertOffSeasonTicket = "INSERT INTO Product (ProductCode, ProductType, seasonStartDate , seasonEndDate, depAirportCodeID, arrAirportCodeID, depTime, arrTime, flightNo, flightClass, aircraftType, rebate)"
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(insertOffSeasonTicket);
            ps.setString(1, productCode);
            ps.setString(2, "TO");
            ps.setString(3, seasonStartDate);
            ps.setString(4, seasonEndDate);
            ps.setInt(5, depAirportCodeID);
            ps.setInt(6, arrAirportCodeID);
            ps.setString(7, depTime);
            ps.setString(8, arrTime);
            ps.setString(9, flightNo);
            ps.setString(10, flightClass);
            ps.setString(11, aircraftType);
            ps.setDouble(12, rebate);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /** Adds an awardsTicket record to the database with the
     * provided data.
     */
    public static void addAwardsTicket(String productCode,String depAirportCode,
                                       String arrAirportCode, String depTime, String arrTime,
                                       String flightNo, String flightClass,
                                       String aircraftType, double pointsPerMile) {
        String getDepAirportID = "SELECT AirportID FROM Airport WHERE AirportCode = ?";
        int depAirportCodeID = 0;
        int arrAirportCodeID = 0;
        try {
            ps = conn.prepareStatement(getDepAirportID);
            ps.setString(1, depAirportCode);
            rs = ps.executeQuery();
            if (rs.next()){
                depAirportCodeID = rs.getInt("AirportID");
            }
        }catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String getArrAirportID = "SELECT AirportID FROM Airport WHERE AirportCode = ?";
        ResultSet rs1 = null;
        try {
            ps = conn.prepareStatement(getArrAirportID);
            ps.setString(1, arrAirportCode);
            rs1 = ps.executeQuery();
            if (rs1.next()){
                arrAirportCodeID = rs1.getInt("AirportID");
            }
        }catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String getProductID = "SELECT ProductID FROM Product WHERE ProductCode = ?";
        try {
            ps = conn.prepareStatement(getProductID);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            if (!rs.next()){
                String insertAwardTicket = "INSERT INTO Product (ProductCode, ProductType, depAirportCodeID, arrAirportCodeID, depTime, arrTime, flightNo, flightClass, aircraftType, pointsPerMile)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertAwardTicket);
                    ps.setString(1, productCode);
                    ps.setString(2, "TA");
                    ps.setInt(3, depAirportCodeID);
                    ps.setInt(4, arrAirportCodeID);
                    ps.setString(5, depTime);
                    ps.setString(6, arrTime);
                    ps.setString(7, flightNo);
                    ps.setString(8, flightClass);
                    ps.setString(9, aircraftType);
                    ps.setDouble(10, pointsPerMile);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        
    }
    /**
     * Adds a CheckedBaggage record to the database with the
     * provided data.
     */
    public static void addCheckedBaggage(String productCode, String ticketCode) {
        String insertCheckedBaggage = "INSERT INTO Product (ProductCode, ProductType, TicketCode) VALUES (?, ?, ?)";
        try {
            ps = conn.prepareStatement(insertCheckedBaggage);
            ps.setString(1, productCode);
            ps.setString(2, "SC");
            ps.setString(3, ticketCode);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds a Insurance record to the database with the
     * provided data.
     */
    public static void addInsurance(String productCode, String productName,
                                    String ageClass, double costPerMile) {
        String insertInsurance = "INSERT INTO Product (ProductCode, ProductType, InsuranceName, ageClass, costPerMile) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(insertInsurance);
            ps.setString(1, productCode);
            ps.setString(2, "SI");
            ps.setString(3, productName);
            ps.setString(4, ageClass);
            ps.setDouble(5, costPerMile);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds a SpecialAssistance record to the database with the
     * provided data.
     */
    public static void addSpecialAssistance(String productCode, String assistanceType) {
        String insertSpecialAssistance = "INSERT INTO Product (ProductCode, ProductType, typeofService) VALUES (?, ?, ?)";
        try {
            ps = conn.prepareStatement(insertSpecialAssistance);
            ps.setString(1, productCode);
            ps.setString(2, "SS");
            ps.setString(3, assistanceType);
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds a refreshment record to the database with the
     * provided data.
     */
    public static void addRefreshment(String productCode, String name, double cost) {
        String insertRefreshment = "INSERT INTO Product (ProductCode, ProductType, RefreshmentName, RefreshmentCost) VALUES (?, ?, ?, ?)";
        try{
            ps = conn.prepareStatement(insertRefreshment);
            ps.setString(1, productCode);
            ps.setString(2, "SR");
            ps.setString(3, name);
            ps.setDouble(4, cost);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Removes all invoice records from the database
     */
    public static void removeAllInvoices() {
        PreparedStatement ps1;
        PreparedStatement ps2;
        String deleteMappingTable = "DELETE FROM MappingTable";
        String deletePassenger = "DELETE FROM Passenger";
        String deleteInvoice = "DELETE FROM Invoice";
        try {
            ps = conn.prepareStatement(deletePassenger);
            ps1 = conn.prepareStatement(deleteMappingTable);
            ps2 = conn.prepareStatement(deleteInvoice);
            ps.executeUpdate();
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps1.close();
            ps2.close();
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds an invoice record to the database with the given data.
     */
    public static void addInvoice(String invoiceCode, String customerCode,
                                  String salesPersonCode, String invoiceDate) {
        if (!salesPersonCode.equals("online")){
            String getID = "SELECT PersonID, CustomerID FROM Person, Customer WHERE PersonCode = ? AND CustomerCode = ?";
            try {
                ps = conn.prepareStatement(getID);
                ps.setString(1, salesPersonCode);
                ps.setString(2, customerCode);
                rs = ps.executeQuery();
                while (rs.next()){
                    int personID = rs.getInt("PersonID");
                    int customerID = rs.getInt("CustomerID");
                    String insertInvoice = "INSERT INTO Invoice (InvoiceCode, CustomerCode, InvoiceDate, SalesPerson) VALUES (?, ?, ?, ?)";
                    try {
                        ps = conn.prepareStatement(insertInvoice);
                        ps.setString(1, invoiceCode);
                        ps.setInt(2, customerID);
                        ps.setString(3, invoiceDate);
                        ps.setInt(4, personID);
                        ps.executeUpdate();
                    } catch (SQLException e)
                    {
                        System.out.println("SQLException: ");
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e)
            {
                System.out.println("SQLException: ");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else if (salesPersonCode.equals("online")){
            String getID = "SELECT CustomerID FROM Customer WHERE CustomerCode = ?";
            try {
                ps = conn.prepareStatement(getID);
                ps.setString(1, customerCode);
                rs = ps.executeQuery();
                rs.next();
                int customerID = rs.getInt("CustomerID");
                String insertInvoice = "INSERT INTO Invoice (InvoiceCode, CustomerCode, InvoiceDate) VALUES (?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertInvoice);
                    ps.setString(1, invoiceCode);
                    ps.setInt(2, customerID);
                    ps.setString(3, invoiceDate);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } catch (SQLException e)
            {
                System.out.println("SQLException: ");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Adds a particular Ticket (corresponding to <code>productCode</code>) to an
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * additional details
     */
    public static void addTicketToInvoice(String invoiceCode, String productCode,
                                          String travelDate, String ticketNote) {
        String getID = "SELECT ProductID, InvoiceID FROM Invoice, Product WHERE InvoiceCode = ? AND ProductCode = ?";
        try {
            ps = conn.prepareStatement(getID);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            rs = ps.executeQuery();
            while (rs.next()){
                int productID = rs.getInt("ProductID");
                int invoiceID = rs.getInt("InvoiceID");
                String insertTitcketToInvoice = "INSERT INTO MappingTable (InvoiceID, ProductID, TravelDate, TicketNote) VALUES (?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertTitcketToInvoice);
                    ps.setInt(1, invoiceID);
                    ps.setInt(2, productID);
                    ps.setString(3, travelDate);
                    ps.setString(4, ticketNote);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds a Passenger information to an 
     * invoice corresponding to the provided <code>invoiceCode</code> 
     */
    public static void addPassengerInformation(String invoiceCode, String productCode, 
                                               String personCode, 
                                               String identity, int age, String nationality, String seat){
        String getID = "SELECT InvoiceID, ProductID, PersonID FROM Invoice, Product, Person WHERE InvoiceCode = ? AND ProductCode = ? AND PersonCode = ?";
        try {
            ps = conn.prepareStatement(getID);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setString(3, personCode);
            rs = ps.executeQuery();
            while (rs.next()){
                int invoiceID = rs.getInt("InvoiceID");
                int productID = rs.getInt("ProductID");
                int personID = rs.getInt("PersonID");
                String insertPassenger = "INSERT INTO Passenger (InvoiceID, ProductCode, PersonalCode, IdentityNumber, Age, Nationality, Seat)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertPassenger);
                    ps.setInt(1, invoiceID);
                    ps.setInt(2, productID);
                    ps.setInt(3, personID);
                    ps.setString(4, identity);
                    ps.setInt(5, age);
                    ps.setString(6, nationality);
                    ps.setString(7, seat);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds an Insurance Service (corresponding to <code>productCode</code>) to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity and associated ticket information
     */
    public static void addInsuranceToInvoice(String invoiceCode, String productCode, 
                                             int quantity, String ticketCode) { 
        String getID = "SELECT ProductID, InvoiceID FROM Invoice, Product WHERE InvoiceCode = ? AND ProductCode = ?";
        try {
            ps = conn.prepareStatement(getID);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            rs = ps.executeQuery();
            while (rs.next()){
                int productID = rs.getInt("ProductID");
                int invoiceID = rs.getInt("InvoiceID");
                String insertInsuranceToInvoice = "INSERT INTO MappingTable (ProductID, InvoiceID, InsuranceQuantity, TicketCode) VALUES (?, ?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertInsuranceToInvoice);
                    ps.setInt(1, productID);
                    ps.setInt(2, invoiceID);
                    ps.setInt(3, quantity);
                    ps.setString(4, ticketCode);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds a CheckedBaggage Service (corresponding to <code>productCode</code>) to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     */
    public static void addCheckedBaggageToInvoice(String invoiceCode, String productCode, 
                                                  int quantity) {
        String getID = "SELECT ProductID, InvoiceID FROM Invoice, Product WHERE InvoiceCode = ? AND ProductCode = ?";
        try {
            ps = conn.prepareStatement(getID);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            rs = ps.executeQuery();
            while (rs.next()){
                int productID = rs.getInt("ProductID");
                int invoiceID = rs.getInt("InvoiceID");
                String insertCheckedBaggageToInvoice = "INSERT INTO MappingTable (ProductID, InvoiceID, BaggageQuantity) VALUES (?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertCheckedBaggageToInvoice);
                    ps.setInt(1, productID);
                    ps.setInt(2, invoiceID);
                    ps.setInt(3, quantity);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds a SpecialAssistance Service (corresponding to <code>productCode</code>) to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     */
    public static void addSpecialAssistanceToInvoice(String invoiceCode, String productCode, 
                                                     String personCode) { 
        String getID = "SELECT ProductID, InvoiceID, PersonID FROM Invoice, Product, Person WHERE InvoiceCode = ? AND ProductCode = ? AND PersonCode = ?";
        try {
            ps = conn.prepareStatement(getID);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            ps.setString(3, personCode);
            rs = ps.executeQuery();
            while (rs.next()){
                int productID = rs.getInt("ProductID");
                int invoiceID = rs.getInt("InvoiceID");
                int PersonID = rs.getInt("PersonID");
                String insertSpecialAssistanceToInvoice = "INSERT INTO MappingTable (ProductID, InvoiceID, PersonID) VALUES (?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertSpecialAssistanceToInvoice);
                    ps.setInt(1, productID);
                    ps.setInt(2, invoiceID);
                    ps.setInt(3, PersonID);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Adds a Refreshment service (corresponding to <code>productCode</code>) to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     */
    public static void addRefreshmentToInvoice(String invoiceCode, 
                                               String productCode, int quantity) { 
        String getID = "SELECT ProductID, InvoiceID FROM Invoice, Product WHERE InvoiceCode = ? AND ProductCode = ?";
        try {
            ps = conn.prepareStatement(getID);
            ps.setString(1, invoiceCode);
            ps.setString(2, productCode);
            rs = ps.executeQuery();
            while (rs.next()){
                int productID = rs.getInt("ProductID");
                int invoiceID = rs.getInt("InvoiceID");
                String insertRefreshmentToInvoice = "INSERT INTO MappingTable (ProductID, InvoiceID, RefreshmentQuantity) VALUES (?, ?, ?)";
                try {
                    ps = conn.prepareStatement(insertRefreshmentToInvoice);
                    ps.setInt(1, productID);
                    ps.setInt(2, invoiceID);
                    ps.setInt(3, quantity);
                    ps.executeUpdate();
                } catch (SQLException e)
                {
                    System.out.println("SQLException: ");
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try {
            ps.close();
            rs.close();
            //conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /*public static void main(String arg[]){
     removeAllInvoices();
     removeAllCustomers();
     removeAllProducts();
     removeAllAirports();
     removeAllPersons();
     //addPerson("944c", "Castro", "Starlin","142-241-6024" , "1060 West Addison Street", "Chicago", "IL", "60613", "USA");
     //addAirport("LNK", "Lincoln Municipal", "2400 W Adams St", "Lincoln", "NE", "68524", "USA", 40, 50,96, 40, 0);
     //addStandardTicket("1255", "LAX", "ATL", "09:30", "15:55", "NA2222", "BC", "Boeing757");
     
     }*/
    
}