package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ModelDatabaseGenres implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Genre> getGenres()
    {
        ArrayList<Genre> genres;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name FROM genres " +
                            "ORDER BY name ASC");
        genres = resultSetToArrayListGenre(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(genres);
        return genres;
    }

    private ArrayList<Genre> resultSetToArrayListGenre(ResultSet rs)
    {
        ArrayList<Genre> genres = new ArrayList<>();
        Genre singlegenre;

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
            {
                singlegenre = new Genre(db.getSQLString(rs, "name"));

                genres.add(singlegenre);
            }

            return genres;
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }
}
