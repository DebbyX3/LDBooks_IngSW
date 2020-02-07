package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModelDatabaseUnregisteredUser implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public void insertNewUnregisteredClient(RegisteredClient client, String street, String houseNumber, String city, String postalCode)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO notRegisteredUsers(email, name, surname, phoneNumber, addressStreet, addressHouseNumber, cityName, cityCAP) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ", List.of(client.getName(), client.getSurname(), client.getPhoneNumber(), street, houseNumber, city, postalCode));
    }
}
