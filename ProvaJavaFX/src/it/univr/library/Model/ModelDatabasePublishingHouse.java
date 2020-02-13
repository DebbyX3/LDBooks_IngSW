package it.univr.library.Model;

import it.univr.library.DatabaseConnection;
import it.univr.library.PublishingHouse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabasePublishingHouse implements ModelPublishingHouse
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<PublishingHouse> getPublishingHouses()
    {
        ArrayList<PublishingHouse> publishingHouses;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name " +
                "FROM publishingHouses " +
                "ORDER BY name ASC");

        publishingHouses = resultSetToPublishingHouses(db.getResultSet());
        db.DBCloseConnection();

        return publishingHouses;
    }

    private ArrayList<PublishingHouse> resultSetToPublishingHouses(ResultSet rs) {
        ArrayList<PublishingHouse> publishingHouses = new ArrayList<>();
        PublishingHouse publishingHouse;

        try
        {
            while (rs.next())
            {
                publishingHouse = new PublishingHouse(db.getSQLString(rs, "name"));
                publishingHouses.add(publishingHouse);
            }

            return publishingHouses;

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public void addNewPublishingHouse(PublishingHouse newPublishingHouse)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO publishingHouses(name) " +
                "VALUES(?)", List.of(newPublishingHouse.getName()));
    }
}
