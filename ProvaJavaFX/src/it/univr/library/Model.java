package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface Model
{
    public default ArrayList<Book> getBooks() { return null; }

    public default ArrayList<Book> getBooks(Filter filter) { return null; }

    public default ArrayList<Object> getCharts()
    {
        return null;
    }

    public default ArrayList<Genre> getGenres()
    {
        return null;
    }

    public default  ArrayList<Language> getLanguages(){ return null; }

    public default User getUser(User testUser){ return null;}

    public default ArrayList<Charts> getCharts(Filter filter)
    {
        return null;
    }

    public default RegisteredUser getRegisteredUser(User testUser){ return null;}

    public default Librocard getLibrocardInformation(User user){return null;}

    public default ArrayList<String> getCities(){return null;}

    public default ArrayList<String> getCAPs(){return null;}

    public default Boolean doesMailAlreadyExist(RegisteredUser test){return null;}

    public default void addUser(RegisteredUser user){}

    public default void addAddress(RegisteredUser testUser){};

    public default void createLibroCard(RegisteredUser testUser){};

    public default ArrayList<Order> getOrders(User user){return null;};

    public default Order getOrderNotRegisteresUser(String mailNotRegUser, String orderCode){return null;};

}