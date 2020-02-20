package it.univr.library.Model;

import it.univr.library.DatabaseConnection;
import it.univr.library.Librocard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseLibrocard implements ModelLibrocard
{
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public ArrayList<String> getMailsLibroCards()
    {
        ArrayList<String> mails;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT DISTINCT libroCards.email " +
                "FROM libroCards ");

        mails = resultSetToStringMailsLibroCards(db.getResultSet());


        return mails;
    }

    private ArrayList<String> resultSetToStringMailsLibroCards(ResultSet rs)
    {
        ArrayList<String> mails = new ArrayList<>();
        String mail;
        try
        {
            while (rs.next())
            {
                mail = db.getSQLString(rs,"email");
                mails.add(mail);
            }

            return mails;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    @Override
    public ArrayList<Librocard> getAllLibroCards()
    {
        ArrayList<Librocard> librocards;

        db.DBOpenConnection();
        db.executeSQLQuery("SELECT numberID, totalPoints, issueDate, email " +
                "FROM libroCards");

        librocards = resultSetToLibroCards(db.getResultSet());


        return librocards;
    }

    @Override
    public ArrayList<Librocard> getSpecificLibroCard(String mail)
    {
        ArrayList<Librocard> librocard;

        db.DBOpenConnection();
        db.executeSQLQuery("SELECT numberID, totalPoints, issueDate, email " +
                "FROM libroCards " +
                "WHERE libroCards.email LIKE ?", List.of(mail));

        librocard = resultSetToLibroCards(db.getResultSet());


        return librocard;
    }

    private ArrayList<Librocard> resultSetToLibroCards(ResultSet rs)
    {
        ArrayList<Librocard> librocards = new ArrayList<>();
        Librocard librocard;

        try
        {
            while (rs.next())
            {
               librocard = new Librocard(db.getSQLString(rs,"numberID"), db.getSQLInt(rs,"totalPoints"),
                       db.getSQLLong(rs,"issueDate"),db.getSQLString(rs,"email"));
               librocards.add(librocard);
            }

            return librocards;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
