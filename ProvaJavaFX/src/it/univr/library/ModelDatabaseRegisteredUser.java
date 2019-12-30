package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseRegisteredUser implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    public User getUser(User testUser)
    {
        User user;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name, surname, phoneNumber, email, password " +
                            "FROM registeredUsers " +
                            "WHERE email LIKE ? AND password LIKE ?",
                            List.of(testUser.getEmail(), testUser.getPassword()));

        user = resultSetToUser(db.getResultSet());
        db.DBCloseConnection();

        return user;
    }

    private User resultSetToUser(ResultSet rs)
    {
        User user = null;

        try
        {
            while (rs.next())
            {
                user = new User();
                user.setName(db.getSQLString(rs, "name"));
                user.setSurname(db.getSQLString(rs, "surname"));
                user.setEmail(db.getSQLString(rs, "email"));
                user.setPassword(db.getSQLString(rs, "password"));
                user.setPhoneNumber(db.getSQLString(rs, "phoneNumber"));
            }

            return user;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    public User getManager(User testUser)
    {
        User manager;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT email, name, surname, password " +
                        "FROM managers " +
                        "WHERE email LIKE ? AND password LIKE ?",
                        List.of(testUser.getEmail(), testUser.getPassword()));

        manager = resultSetToManager(db.getResultSet());
        db.DBCloseConnection();

        return manager;
    }

    private User resultSetToManager(ResultSet rs)
    {
        User manager = null;

        try
        {
            while (rs.next())
            {
                manager = new User();
                manager.setName(db.getSQLString(rs, "name"));
                manager.setSurname(db.getSQLString(rs, "surname"));
                manager.setEmail(db.getSQLString(rs, "email"));
                manager.setPassword(db.getSQLString(rs, "password"));
                manager.setPhoneNumber(null);
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
