package it.univr.library.Model;

import it.univr.library.Utils.DatabaseConnection;
import it.univr.library.Data.Manager;
import it.univr.library.Data.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDatabaseManagers implements ModelManager
{
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public Manager getManager(User testUser)
    {
        Manager manager;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT email, name, surname, password " +
                        "FROM managers " +
                        "WHERE email LIKE ? AND password LIKE ?",
                List.of(testUser.getEmail(), testUser.getPassword()));

        manager = resultSetToManager(db.getResultSet());


        return manager;
    }

    private Manager resultSetToManager(ResultSet rs)
    {
        Manager manager = null;

        try
        {
            while (rs.next())
            {
                manager = new Manager();
                manager.setName(db.getSQLString(rs, "name"));
                manager.setSurname(db.getSQLString(rs, "surname"));
                manager.setEmail(db.getSQLString(rs, "email"));
                manager.setPassword(db.getSQLString(rs, "password"));
            }

            return manager;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
