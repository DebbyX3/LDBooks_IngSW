package it.univr.library;

import java.sql.ResultSet;

public class ModelDatabaseUserInformations implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    public RegisteredUser getRegisteredUser(User testUser)
    {

        //creo un address di prova
        Address addressTest = new Address();
        addressTest.setStreet("Via F. Baracca");
        addressTest.setHouseNumber("213");
        addressTest.setCity("Vicenza");
        addressTest.setPostalCode("36100");

        RegisteredUser registeredUser = new RegisteredUser(addressTest);
        registeredUser.setName("Giulia");
        registeredUser.setSurname("Rossi");
        registeredUser.setEmail("giuliarossi@gmail.com");
        registeredUser.setPassword("giulia");
        registeredUser.setPhoneNumber("3401215455");
        return registeredUser;
    }

    private RegisteredUser resultSetToregisteredUser(ResultSet rs)
    { return null;}
}
