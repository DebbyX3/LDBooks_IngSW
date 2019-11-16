package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelDatabaseSignUp implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<String> getCities()
    {
        return getCitiesCAPs().getCities();
    }

    @Override
    public ArrayList<String> getCAPs()
    {
        return getCitiesCAPs().getCAPs();
    }

    private Cities getCitiesCAPs()
    {
        Cities citiesAndCAPs;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name, CAP "+
                            "FROM cities " +
                            "ORDER BY name ASC");

        citiesAndCAPs = resultSetToCitiesAndCAPs(db.getResultSet());
        db.DBCloseConnection();

        return citiesAndCAPs;
    }

    private Cities resultSetToCitiesAndCAPs(ResultSet rs)
    {
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<String> CAPs = new ArrayList<>();

        String singleCity;
        String singleCAP;

        Cities citiesAndCAPs = new Cities();

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
            {
                singleCity = db.getSQLString(rs, "name");
                singleCAP = db.getSQLString(rs, "CAP");

                cities.add(singleCity);
                CAPs.add(singleCAP);
            }

            citiesAndCAPs.setCAPs(CAPs);
            citiesAndCAPs.setCities(cities);

            return citiesAndCAPs;
        }
        catch (Exception e)
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
    public Boolean doesMailAlreadyExist(RegisteredUser user)
    {
        boolean result = false;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT email " +
                            "FROM registeredUsers " +
                            "WHERE email LIKE \"" + user.getEmail() + "\"");

        try {
            //if the mail does not exist in the registeredUser table
            result = db.getResultSet().isBeforeFirst(); //false if there are no rows, true if there are some rows
        }
        catch (SQLException e) {
            result = false;
        }

        db.executeSQLQuery( "SELECT email " +
                "FROM managers " +
                "WHERE email LIKE \"" + user.getEmail() + "\"");

        try {
            //if the mail does not exist in the managers table
            result = result || db.getResultSet().isBeforeFirst(); //false if there are no rows, true if there are some rows
        }
        catch (SQLException e) {
            result = result || false;
        }

        db.DBCloseConnection();

        return result;
    }
}
