package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ModelDatabaseRegisteredUser implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    public User getUser(User testUser)
    {
        User user;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name, surname, phoneNumber, email, password" +
                            "FROM registeredUsers " +
                            "WHERE UPPER (email) GLOB " + testUser.getEmail() +  " AND password LIKE " + testUser.getPassword());

        user = resultSetToUser(db.getResultSet());
        db.DBCloseConnection();

        return user;
    }

    private User resultSetToUser(ResultSet rs)
    {
        User user = null;

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
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
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

}
