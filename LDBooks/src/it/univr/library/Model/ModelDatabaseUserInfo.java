package it.univr.library.Model;

import it.univr.library.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseUserInfo implements ModelUserInfo
{
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public String getPhoneNumber(User user)
    {
        String phoneNumber;

        db.DBOpenConnection();
        db.executeSQLQuery( " SELECT phoneNumber " +
                                    "FROM registeredUsers " +
                                    "WHERE email LIKE ? " +
                                    "UNION " +
                                    "SELECT phoneNumber " +
                                    "FROM notRegisteredUsers " +
                                    "WHERE email LIKE ? ",
                                    List.of(user.getEmail(), user.getEmail()));

        phoneNumber = resultSetToPhoneNumber(db.getResultSet());


        return phoneNumber;
    }

    private String resultSetToPhoneNumber(ResultSet rs)
    {

        String phoneNumber = "";

        try
        {
            while (rs.next())
            {
                phoneNumber = db.getSQLString(rs,"phoneNumber");
            }


            return phoneNumber;
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

    /**
     * This method tells if the mail inserted by the user during sign up already exists.
     * @param user contains user's form data
     * @return  true if the mail already exists in DB in registeredUser and/or manager tables
     *          false if the mail does not exist
     */
    @Override
    public Boolean doesMailAlreadyExist(RegisteredClient user)
    {
        boolean result = false;

        db.DBOpenConnection();

        //First, check the registeredUser table
        db.executeSQLQuery(   "SELECT email " +
                                    "FROM registeredUsers " +
                                    "WHERE email LIKE ?", List.of(user.getEmail()));

        try
        {
            //If the mail does not exist in the registeredUser table
            //return false if there are no rows, true if there are some rows
            result = db.getResultSet().isBeforeFirst();
        }
        catch (SQLException e) {
            result = false;
        }

        //If the user doesn't exist in the registeredUser table, then check in the Manager table
        db.executeSQLQuery(   "SELECT email " +
                                    "FROM managers " +
                                    "WHERE email LIKE ?", List.of(user.getEmail()));

        try
        {
            //If the mail does not exist in the managers table,
            //return false if there are no rows, true if there are some rows
            result = result || db.getResultSet().isBeforeFirst();
        }
        catch (SQLException e) {
            result = result || false;
        }



        return result;
    }

    @Override
    public Boolean doesMailUnregisteredAlreadyExist(RegisteredClient user)
    {
        boolean result = false;

        db.DBOpenConnection();

        //First, check the registeredUser table
        db.executeSQLQuery(   "SELECT email " +
                "FROM notRegisteredUsers " +
                "WHERE email LIKE ?", List.of(user.getEmail()));

        try
        {
            //If the mail does not exist in the registeredUser table
            //return false if there are no rows, true if there are some rows
            result = db.getResultSet().isBeforeFirst();
        }
        catch (SQLException e) {
            result = false;
        }




        return result;
    }

    @Override
    public void addUser(RegisteredClient user)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO registeredUsers(email, name, surname, phoneNumber, password) " +
                                    "VALUES (?, ?, ?, ?, ?)",
                List.of(user.getEmail(), user.getName(), user.getSurname(), user.getPhoneNumber(), user.getPassword()));
    }

    @Override
    public void addUnregisteredUser(RegisteredClient user, Address shipAddress)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO notRegisteredUsers(email, name, surname, phoneNumber, addressStreet, addressHouseNumber, cityName, cityCAP) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", List.of(user.getEmail(), user.getName(), user.getSurname(), user.getPhoneNumber(), shipAddress.getStreet(), shipAddress.getHouseNumber(), shipAddress.getCity(), shipAddress.getPostalCode()));


    }

    @Override
    public void updateUser(RegisteredClient user)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "UPDATE registeredUsers " +
                                    "SET name = ?, " +
                                    "surname = ?, " +
                                    "phoneNumber = ?, " +
                                    "password = ? " +
                                    "WHERE email LIKE ?",
                List.of(user.getName(), user.getSurname(), user.getPhoneNumber(), user.getPassword(), user.getEmail()));


    }
}
