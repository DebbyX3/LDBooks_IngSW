package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDatabaseUserInformations implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    public RegisteredUser getRegisteredUser(User testUser)
    {
        RegisteredUser regUser;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP " +
                            "FROM ship " +
                            "WHERE emailRegisteredUser LIKE ?",
                            List.of(testUser.getEmail()));

        regUser = resultSetToRegisteredUser(db.getResultSet());
        db.DBCloseConnection();

        return regUser;
    }

    private RegisteredUser resultSetToRegisteredUser(ResultSet rs)
    {
        RegisteredUser regUser = new RegisteredUser();
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

                regUser.setSingleAddress(address);
            }

            return regUser;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
