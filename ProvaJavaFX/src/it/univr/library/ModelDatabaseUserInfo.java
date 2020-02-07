package it.univr.library;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseUserInfo implements Model
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
        db.executeSQLQuery(   "SELECT email " +
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
        db.executeSQLQuery(   "SELECT email " +
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
    public Boolean doesMailUnregisteredAlreadyExist(RegisteredClient user)
    {
        boolean result = false;

        db.DBOpenConnection();

        //First, check the registeredUser table
        db.executeSQLQuery(   "SELECT email " +
                "FROM notRegisteredUsers " +
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


        db.DBCloseConnection();

        return result;
    }

    @Override
    public void addUser(RegisteredClient user)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO registeredUsers(email, name, surname, phoneNumber, password) " +
                                    "VALUES (?, ?, ?, ?, ?)",
                List.of(user.getEmail(), user.getName(), user.getSurname(), user.getPhoneNumber(), user.getPassword()));


        System.out.println("INSERT INTO registeredUsers (email, name, surname, phoneNumber, password) " +
                "VALUES ('" + user.getEmail() + "','" + user.getName() + "','" + user.getSurname()
                + "','" + user.getPhoneNumber() + "','" + user.getPassword() + "')");

        db.DBCloseConnection();
    }

    @Override
    public void addUnregisteredUser(RegisteredClient user, Address shipAddress)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO notRegisteredUsers(email, name, surname, phoneNumber, addressStreet, addressHouseNumber, cityName, cityCAP) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", List.of(user.getEmail(), user.getName(), user.getSurname(), user.getPhoneNumber(), shipAddress.getStreet(), shipAddress.getHouseNumber(), shipAddress.getCity(), shipAddress.getPostalCode()));

        db.DBCloseConnection();
    }

    @Override
    public void updateUser(RegisteredClient user)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "UPDATE registeredUsers " +
                                    "SET name = ?, " +
                                    "surname = ?, " +
                                    "phoneNumber = ?, " +
                                    "password = ? " +
                                    "WHERE email LIKE ?",
                List.of(user.getName(), user.getSurname(), user.getPhoneNumber(), user.getPassword(), user.getEmail()));

        db.DBCloseConnection();
    }

    @Override
    public void addAddress(RegisteredClient user, Address address)
    {
        if(!addressAlreadyExists(address))
           createNewAddress(address); //create address

        //only link it to the user if it's not already linked
        if(!addressAlreadyLinked(user, address))
            linkAddressToUser(user, address);   // link address
    }

    private void linkAddressToUser(RegisteredClient user, Address address)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate(  "INSERT INTO ship(emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP) " +
                                    "VALUES(?, ?, ?, ?, ?)",
                            List.of(user.getEmail(), address.getStreetQuery(), address.getHouseNumber(), address.getCity(), address.getPostalCode()));

        System.out.println("INSERT INTO ship(emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP) " +
                "VALUES( '" + user.getEmail() + "', '"+ address.getStreetQuery() + "', '" + address.getHouseNumber() +
                "', '" + address.getCity() + "', '" + address.getPostalCode() + "')");

        db.DBCloseConnection();
    }

    @Override
    public void unlinkAddressFromUser(RegisteredClient user, Address addressToDelete)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate(  "DELETE FROM ship " +
                                    "WHERE emailRegisteredUser LIKE ? AND " +
                                    "addressStreet LIKE ? AND " +
                                    "addressHouseNumber LIKE ? AND " +
                                    "cityName LIKE ? AND " +
                                    "cityCAP LIKE ?",
                                    List.of(user.getEmail(), addressToDelete.getStreetQuery(), addressToDelete.getHouseNumber(),
                                            addressToDelete.getCity(), addressToDelete.getPostalCode()));

        db.DBCloseConnection();
    }

    private void createNewAddress(Address address)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate(  "INSERT INTO addresses(street, houseNumber, cityName, cityCAP) " +
                                    "VALUES(?, ?, ?, ?)",
                            List.of(address.getStreetQuery(), address.getHouseNumber(), address.getCity(), address.getPostalCode()));

        System.out.println("INSERT INTO addresses(street, houseNumber, cityName, cityCAP) " +
                "VALUES( '" + address.getStreetQuery() + "', '" + address.getHouseNumber() + "', '" + address.getCity() + "', '" +
                address.getPostalCode() + "')");

        db.DBCloseConnection();
    }

    /**
     * @param address, an address of Address class
     * @return true if address already exists in db otherwise false
     */
    private boolean addressAlreadyExists(Address address)
    {
        boolean existsRow = false;

        db.DBOpenConnection();

        db.executeSQLQuery(   "SELECT street, houseNumber, cityName, cityCAP " +
                                    "FROM addresses " +
                                    "WHERE street LIKE ? " +
                                    "AND houseNumber LIKE ? " +
                                    "AND cityName LIKE ? "+
                                    "AND cityCAP LIKE ?",
                            List.of(address.getStreetQuery(), address.getHouseNumber(), address.getCity(), address.getPostalCode()));

        System.out.println("SELECT street, houseNumber, cityName, cityCAP " +
                "FROM addresses " +
                "WHERE street LIKE '" + address.getStreetQuery() + "' AND houseNumber LIKE '" +
                address.getHouseNumber() + "' AND cityName LIKE '" + address.getCity() +
                "' AND cityCAP LIKE '" + address.getPostalCode() + "'");

        try
        {
            existsRow = db.getResultSet().isBeforeFirst(); //false if there are no rows, true if there are some rows
        }
        catch (SQLException e) {
            existsRow = false;
        }

        db.DBCloseConnection();
        return existsRow;
    }

    private boolean addressAlreadyLinked(RegisteredClient user, Address address)
    {
        boolean existsRow = false;

        db.DBOpenConnection();

        db.executeSQLQuery(   "SELECT emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP " +
                                    "FROM ship " +
                                    "WHERE emailRegisteredUser LIKE ? " +
                                    "AND addressStreet LIKE ? " +
                                    "AND addressHouseNumber LIKE ? "+
                                    "AND cityName LIKE ? " +
                                    "AND cityCAP LIKE ?",
                List.of(user.getEmail(), address.getStreetQuery(), address.getHouseNumber(), address.getCity(), address.getPostalCode()));

        System.out.println("SELECT emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP " +
                "FROM ship " +
                "WHERE emailRegisteredUser LIKE '" + user.getEmail() +
                "' AND addressStreet LIKE '" + address.getStreetQuery() +
                "' AND addressHouseNumber LIKE '" + address.getHouseNumber() +
                "' AND cityName LIKE '" + address.getCity() +
                "' AND cityCAP LIKE '" + address.getPostalCode() + "'");

        try
        {
            existsRow = db.getResultSet().isBeforeFirst(); //false if there are no rows, true if there are some rows
        }
        catch (SQLException e) {
            existsRow = false;
        }

        db.DBCloseConnection();
        return existsRow;
    }

    @Override
    public void createLibroCard(RegisteredClient user)
    {
        Date unixTime = new Date(System.currentTimeMillis() / 1000L);

        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO libroCards (totalPoints, issueDate, email) " +
                                    "VALUES('0', ?, ?);",
                            List.of(unixTime, user.getEmail()));

        System.out.println( "INSERT INTO table (totalPoints, issueDate, email) " +
                            "VALUES('0', '" + unixTime + "', '" + user.getEmail() +"');");

        db.DBCloseConnection();
    }
}
