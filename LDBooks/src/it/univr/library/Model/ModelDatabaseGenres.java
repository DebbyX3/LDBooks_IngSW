package it.univr.library.Model;

import it.univr.library.Utils.DatabaseConnection;
import it.univr.library.Data.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseGenres implements ModelGenres
{
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public ArrayList<Genre> getGenres()
    {
        ArrayList<Genre> genres;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name FROM genres " +
                            "ORDER BY name ASC");
        genres = resultSetToArrayListGenre(db.getResultSet());

        return genres;
    }

    private ArrayList<Genre> resultSetToArrayListGenre(ResultSet rs)
    {
        ArrayList<Genre> genres = new ArrayList<>();
        Genre singleGenre;

        try
        {
            while (rs.next())
            {
                singleGenre = new Genre(db.getSQLString(rs, "name"));
                genres.add(singleGenre);
            }

            return genres;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public void addNewGenre(String newGenre)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO genres(name) " +
                            "VALUES(?)",List.of(newGenre) );
    }
}
