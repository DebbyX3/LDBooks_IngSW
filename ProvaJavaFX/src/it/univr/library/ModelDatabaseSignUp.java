package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ModelDatabaseSignUp implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<String> getCities()
    {
        ArrayList<String> cities;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name FROM cities " +
                            "ORDER BY name ASC");
        cities = resultSetToArrayListCities(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(cities);
        return cities;
    }

    private ArrayList<String> resultSetToArrayListCities(ResultSet rs)
    {
        ArrayList<String> cities = new ArrayList<>();
        String singleCity;

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
            {
                singleCity = db.getSQLString(rs, "name");
                cities.add(singleCity);
            }

            return cities;
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

    @Override
    public Boolean checkMail(RegisteredUser test)
    {
        //todo finire il check della mail
        return true;
    }
}
