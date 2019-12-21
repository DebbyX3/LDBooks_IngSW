package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelDatabaseUserLibrocard implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public Librocard getLibrocardInformation(User user)
    {
        Librocard librocard;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT numberID, totalPoints, issueDate, email " +
                            "FROM libroCards " +
                            "WHERE email LIKE ?", List.of(user.getEmail()));
        librocard = resultSetToLibroCard(db.getResultSet());
        db.DBCloseConnection();

        return librocard;
    }

    private Librocard resultSetToLibroCard(ResultSet rs)
    {
        Librocard librocard = new Librocard();

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
            {
                librocard.setNumberID(db.getSQLString(rs, "numberID"));
                librocard.setTotalPoints(db.getSQLInt(rs, "totalPoints"));
                librocard.setIssueDate(db.getSQLLong(rs, "issueDate"));
                librocard.setEmail(db.getSQLString(rs, "email"));
            }
            
            //TODO: 08/11/2019 check date before return librocard object, and check if query is null
            return librocard;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
