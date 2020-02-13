package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseRegisteredUser implements ModelRegisteredUser
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public RegisteredClient getRegisteredClient(User testUser)
    {
        RegisteredClient user;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name, surname, phoneNumber, email, password " +
                            "FROM registeredUsers " +
                            "WHERE email LIKE ? AND password LIKE ?",
                            List.of(testUser.getEmail(), testUser.getPassword()));

        user = resultSetToUser(db.getResultSet());
        db.DBCloseConnection();

        return user;
    }

    private RegisteredClient resultSetToUser(ResultSet rs)
    {
        RegisteredClient user = null;

        try
        {
            while (rs.next())
            {
                user = new RegisteredClient();
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
        db.DBCloseConnection();

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
