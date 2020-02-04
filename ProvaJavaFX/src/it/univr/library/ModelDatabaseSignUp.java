package it.univr.library;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseSignUp implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public List<String> getCities()
    {
        return getCitiesPostalCodes().getCities();
    }

    @Override
    public List<String> getPostalCodes()
    {
        return getCitiesPostalCodes().getPostalCodes();
    }

    private CitiesPostalCodes getCitiesPostalCodes()
    {
        CitiesPostalCodes citiesAndPostalCodes;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name, CAP "+
                            "FROM cities " +
                            "ORDER BY name ASC");

        citiesAndPostalCodes = resultSetToCitiesAndPostalCodes(db.getResultSet());
        db.DBCloseConnection();

        return citiesAndPostalCodes;
    }

    private CitiesPostalCodes resultSetToCitiesAndPostalCodes(ResultSet rs)
    {
        List<String> cities = new ArrayList<>();
        List<String> postalCodes = new ArrayList<>();

        String singleCity;
        String singlePostalCode;

        CitiesPostalCodes citiesAndPostalCodes;

        try
        {
            while (rs.next())
            {
                singleCity = db.getSQLString(rs, "name");
                singlePostalCode = db.getSQLString(rs, "CAP");

                cities.add(singleCity);
                postalCodes.add(singlePostalCode);
            }

            citiesAndPostalCodes = new CitiesPostalCodes(cities, postalCodes);

            return citiesAndPostalCodes;
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

    /**
     * This method tells if the mail inserted by the user during sign up already exists.
     * @param user contains user's form data
     * @return  true if the mail already exists in DB in registeredUser and/or manager tables
     *          false if the mail does not exist
     */
    @Override
    public Boolean doesMailAlreadyExist(RegisteredClient user)
    {
        boolean result = false;

        db.DBOpenConnection();

        //First, check the registeredUser table
        db.executeSQLQuery( "SELECT email " +
                            "FROM registeredUsers " +
                            "WHERE email LIKE ?", List.of(user.getEmail()));

        try
        {
            //If the mail does not exist in the registeredUser table
            //return false if there are no rows, true if there are some rows
            result = db.getResultSet().isBeforeFirst();
        }
        catch (SQLException e) {
            result = false;
        }

        //If the user doesn't exist in the registeredUser table, then check in the Manager table
        db.executeSQLQuery( "SELECT email " +
                            "FROM managers " +
                            "WHERE email LIKE ?", List.of(user.getEmail()));

        try
        {
            //If the mail does not exist in the managers table,
            //return false if there are no rows, true if there are some rows
            result = result || db.getResultSet().isBeforeFirst();
        }
        catch (SQLException e) {
            result = result || false;
        }

        db.DBCloseConnection();

        return result;
    }

    @Override
    public void addUser(RegisteredClient testUser)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO registeredUsers(email, name, surname, phoneNumber, password) " +
                        "VALUES (?, ?, ?, ?, ?)",
                List.of(testUser.getEmail(), testUser.getName(), testUser.getSurname(), testUser.getPhoneNumber(), testUser.getPassword()));


        System.out.println("INSERT INTO registeredUsers (email, name, surname, phoneNumber, password) " +
                "VALUES ('" + testUser.getEmail() + "','" + testUser.getName() + "','" + testUser.getSurname()
                + "','" + testUser.getPhoneNumber() + "','" + testUser.getPassword() + "')");

        db.DBCloseConnection();
    }

    @Override
    public void addAddress(RegisteredClient testUser)
    {
       if(!addressAlreadyExists(testUser.getAddresses()))
       {
           //create address
           createNewAddress(testUser.getAddresses());
       }
        //only link it to the user
        linkAddressToUser(testUser);
    }

    private void linkAddressToUser(RegisteredClient testUser)
    {
        Address a = new Address();

        for (Address address: testUser.getAddresses())
        {
            a.setStreet(address.getStreet());
            a.setHouseNumber(address.getHouseNumber());
            a.setCity(address.getCity());
            a.setPostalCode(address.getPostalCode());
        }

        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO ship(emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP) " +
                            "VALUES(?, ?, ?, ?, ?)",
                            List.of(testUser.getEmail(), a.getStreetQuery(), a.getHouseNumber(), a.getCity(), a.getPostalCode()));

        System.out.println("INSERT INTO ship(emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP) " +
                "VALUES( '" + testUser.getEmail() + "', '"+ a.getStreetQuery() + "', '" + a.getHouseNumber() +
                "', '" + a.getCity() + "', '" + a.getPostalCode() + "')");

        db.DBCloseConnection();
    }

    private void createNewAddress(List<Address> addresses)
    {
        Address a = new Address();

        for (Address address:addresses) {
            a.setStreet(address.getStreet());
            a.setHouseNumber(address.getHouseNumber());
            a.setCity(address.getCity());
            a.setPostalCode(address.getPostalCode());
        }

        db.DBOpenConnection();

        db.executeSQLUpdate("INSERT INTO addresses(street, houseNumber, cityName, cityCAP) " +
                           "VALUES(?, ?, ?, ?)",
                            List.of(a.getStreetQuery(), a.getHouseNumber(), a.getCity(), a.getPostalCode()));

        System.out.println("INSERT INTO addresses(street, houseNumber, cityName, cityCAP) " +
                "VALUES( '" + a.getStreetQuery() + "', '" + a.getHouseNumber() + "', '" + a.getCity() + "', '" +
                a.getPostalCode() + "')");

        db.DBCloseConnection();
    }

    /**
     * @param addresses, list of addresses
     * @return true if address already exists in db otherwise false
     */
    private boolean addressAlreadyExists(List<Address> addresses) {

        Address a = new Address();
        boolean result = false;
        db.DBOpenConnection();

        for (Address address:addresses) {
            a.setStreet(address.getStreet());
            a.setHouseNumber(address.getHouseNumber());
            a.setCity(address.getCity());
            a.setPostalCode(address.getPostalCode());
        }

        db.executeSQLQuery("SELECT addressStreet, addressHouseNumber, cityName, cityCAP " +
                            "FROM ship " +
                            "WHERE addressStreet LIKE ? " +
                            "AND addressHouseNumber LIKE ? " +
                            "AND cityName LIKE ? "+
                            "AND cityCAP LIKE ?",
                            List.of(a.getStreetQuery(), a.getHouseNumber(), a.getCity(), a.getPostalCode()));

        System.out.println("SELECT addressStreet, addressHouseNumber, cityName, cityCAP " +
                "FROM ship " +
                "WHERE addressStreet LIKE '" + a.getStreetQuery() + "' AND addressHouseNumber LIKE '" +
                a.getHouseNumber() + "' AND cityName LIKE '" + a.getCity() +
                "' AND cityCAP LIKE '" + a.getPostalCode() + "'");

        try
        {
            result = db.getResultSet().isBeforeFirst(); //false if there are no rows, true if there are some rows
        }
        catch (SQLException e) {
            result = false;
        }

        db.DBCloseConnection();
        return result;
    }

    @Override
    public void createLibroCard(RegisteredClient testUser)
    {
        Date unixTime = new Date(System.currentTimeMillis() / 1000L);

        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO libroCards (totalPoints, issueDate, email) " +
                           "VALUES('0', ?, ?);",
                            List.of(unixTime, testUser.getEmail()));

        System.out.println( "INSERT INTO table (totalPoints, issueDate, email) " +
                            "VALUES('0', '" + unixTime + "', '" + testUser.getEmail() +"');");

        db.DBCloseConnection();
    }
}
