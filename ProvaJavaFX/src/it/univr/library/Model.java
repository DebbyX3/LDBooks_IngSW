package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface Model
{
    public default ArrayList<Book> getAllBooks() { return null; }

    public default ArrayList<Book> getAllBooks(Filter filter) { return null; }

    public default ArrayList<Object> getCharts()
    {
        return null;
    }

    public default ArrayList<Genre> getGenres()
    {
        return null;
    }

    public default  ArrayList<Language> getLanguages(){ return null; }

    public default void addNewLanguage(Language language){};

    public default Client getClient(User testUser){ return null;}

    public default Manager getManager(User testUser){return null;};

    public default ArrayList<Charts> getCharts(Filter filter)
    {
        return null;
    }

    public default RegisteredClient getRegisteredUser(User testUser){ return null;}

    public default Librocard getLibrocardInformation(User user){return null;}

    public default ArrayList<String> getCities(){return null;}

    public default ArrayList<String> getCAPs(){return null;}

    public default Boolean doesMailAlreadyExist(RegisteredClient test){return null;}

    public default void addUser(RegisteredClient user){}

    public default void addAddress(RegisteredClient testUser){};

    public default void createLibroCard(RegisteredClient testUser){};

    public default ArrayList<Order> getOrders(User user){return null;};

    public default ArrayList<String> getMailsOrders(){return null;};

    public default ArrayList<Order> getAllOrders(){return null;};

    public default ArrayList<Order> getOrderNotRegisteresUser(String mailNotRegUser, String orderCode){return null;};

    public default ArrayList<Order> getSpecificMailOrders(String mail){return null;};

    public default  ArrayList<Author> getAuthors(){return null;};

    public default ArrayList<Author> getAuthorsForSpecificBook(String isbn){return null;};

    public default void addNewAuthor(String newNameAuthor, String newSurnameAuthor){};

    public default ArrayList<PublishingHouse> getPublishingHouses(){return null;};

    public default ArrayList<Format> getFormats(){return null;};

    public default void addNewBookToDB(Book book){};

    public default int getAuthorID(String authorName, String authorSurname){return 0;};

    public default void linkBookToAuthors(int idAuthor, String isbn){};

    public default void addNewPublishingHouse(PublishingHouse newPublishingHouse){};

    public default void addNewFormat(Format newFormat){};

    public default void addNewGenre(String newGenre){};

    public default ArrayList<Author> createArrayListAuthors(List<String> idNameSurnameAuthors){return null;};

    public default Book getSpecificBook(String isbn){return null;};

    public default void updateBook(Book book){};

    public default void deleteLinkBookToAuthors(int idAuthor, String isbn){};
}