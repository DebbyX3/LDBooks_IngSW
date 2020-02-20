package it.univr.library.Model;

import it.univr.library.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDatabaseUserLibrocard implements ModelUserLibrocard
{
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public Librocard getLibrocardInformation(User user)
    {
        Librocard librocard;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT numberID, totalPoints, issueDate, email " +
                            "FROM libroCards " +
                            "WHERE email LIKE ?", List.of(user.getEmail()));
        librocard = resultSetToLibroCard(db.getResultSet());


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

    @Override
    public void updateLibroCardPoints(Order order)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate(  "UPDATE libroCards " +
                        "SET totalPoints = totalPoints + ? " +
                        "WHERE email LIKE ? ", List.of(order.getBalancePoints(), order.getEmailRegisteredUser() != null ? order.getEmailRegisteredUser() : order.getEmailNotRegisteredUser()));

    }

    @Override
    public void createLibroCard(RegisteredClient user)
    {
        Date unixTime = new Date(System.currentTimeMillis() / 1000L);

        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO libroCards (totalPoints, issueDate, email) " +
                        "VALUES('0', ?, ?);",
                List.of(unixTime, user.getEmail()));

        System.out.println( "INSERT INTO table (totalPoints, issueDate, email) " +
                "VALUES('0', '" + unixTime + "', '" + user.getEmail() +"');");


    }
}
