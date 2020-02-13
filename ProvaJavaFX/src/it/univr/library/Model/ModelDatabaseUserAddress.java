package it.univr.library.Model;

import it.univr.library.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseUserAddress implements ModelUserAddress
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public List<Address> getAddressesRegisteredUser(User testUser)
    {
        List<Address> regUser;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP " +
                            "FROM ship " +
                            "WHERE emailRegisteredUser LIKE ? ",
                            List.of(testUser.getEmail()));

        regUser = resultSetToListAddress(db.getResultSet());
        db.DBCloseConnection();

        return regUser;
    }

    private List<Address> resultSetToListAddress(ResultSet rs)
    {
        List<Address> regUserAddressed = new ArrayList<>();
        Address address;

        try
        {
            while (rs.next())
            {
                address = new Address();

                address.setStreet(db.getSQLString(rs, "addressStreet"));
                address.setHouseNumber(db.getSQLString(rs, "addressHouseNumber"));
                address.setCity(db.getSQLString(rs, "cityName"));
                address.setPostalCode(db.getSQLString(rs, "cityCAP"));

                regUserAddressed.add(address);
            }

            return regUserAddressed;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
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
}
