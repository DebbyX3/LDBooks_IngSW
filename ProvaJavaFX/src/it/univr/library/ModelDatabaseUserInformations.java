package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDatabaseUserInformations implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public RegisteredClient getRegisteredUser(User testUser)
    {
        RegisteredClient regUser;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP " +
                            "FROM ship " +
                            "WHERE emailRegisteredUser LIKE ?",
                            List.of(testUser.getEmail()));

        regUser = resultSetToRegisteredUser(db.getResultSet());
        db.DBCloseConnection();

        return regUser;
    }

    private RegisteredClient resultSetToRegisteredUser(ResultSet rs)
    {
        RegisteredClient regUser = new RegisteredClient();
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
