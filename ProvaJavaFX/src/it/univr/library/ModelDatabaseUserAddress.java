package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseUserAddress implements Model
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
}
