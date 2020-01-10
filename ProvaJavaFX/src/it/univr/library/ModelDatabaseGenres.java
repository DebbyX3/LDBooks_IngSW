package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
