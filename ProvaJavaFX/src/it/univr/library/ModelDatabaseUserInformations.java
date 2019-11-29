package it.univr.library;

import java.sql.ResultSet;

public class ModelDatabaseUserInformations implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    public RegisteredUser getRegisteredUser(User testUser)
    {
        RegisteredUser regUser;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP " +
                            "FROM ship " +
                            "WHERE emailRegisteredUser LIKE '" + testUser.getEmail() + "'");

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
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
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
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

}
