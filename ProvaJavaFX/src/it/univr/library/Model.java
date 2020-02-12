package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface Model
{
    public default ArrayList<Book> getAllBooks() { return null; }

    public default ArrayList<Book> getAllBooks(Filter filter) { return null; }

    public default ArrayList<Book> getSpecificBooksForGenre(Genre genre){return null;}

    public default ArrayList<Genre> getGenres()
    {
        return null;
    }

    public default  ArrayList<Language> getLanguages(){ return null; }

    public default void addNewLanguage(Language language){};

    public default RegisteredClient getRegisteredClient(User testUser){ return null;}

    public default Manager getManager(User testUser){return null;};

    public default List<Address> getAddressesRegisteredUser(User testUser){ return null;}

    public default String getPhoneNumber(User user){return null;};

    public default Librocard getLibrocardInformation(User user){return null;}

    public default List<String> getCities(){return null;}

    public default List<String> getPostalCodes(){return null;}

    public default Boolean doesMailAlreadyExist(RegisteredClient test){return null;}

    public default Boolean doesMailUnregisteredAlreadyExist(RegisteredClient user){return  null;};

    public default void addUnregisteredUser(RegisteredClient user, Address shipAddress){};

    public default void addUser(RegisteredClient user){}

    public default void updateUser(RegisteredClient user){}

    public default void addAddress(RegisteredClient user, Address address){};

    public default void unlinkAddressFromUser(RegisteredClient user, Address addressToDelete){};

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

    public default Book getSpecificBooksForGenre(String isbn){return null;};

    public default void updateBook(Book book){};

    public default void deleteLinkBookToAuthors(int idAuthor, String isbn){};

    public default ArrayList<String> getMailsLibroCards(){return null;};

    public default ArrayList<Librocard> getAllLibroCards(){return null;};

    public default ArrayList<Librocard> getSpecificLibroCard(String mail){return null;};

    public default void updateChartsGenreAllCategoryAll(Charts bookToUpdateInCharts){};

    public default void updateChartsGenreSelectedCategoryAll(Charts bookToUpdateInCharts){};

    public default void updateChartsGenreAllCategorySelected(Charts bookToUpdateInCharts){};

    public default void updateChartsGenreSelectedCategorySelected(Charts bookToUpdateInCharts){};

    public default void insertBookOnTheChartsSelectedGenreAllCategory(Charts bookToInsertOnTheCharts){};

    public default void insertBookOnTheChartsAllGenreAllCategory(Charts bookToInsertOnTheCharts){};

    public default void insertBookOnTheChartsAllGenreSelectedCategory(Charts bookToInsertOnTheCharts){};

    public default void insertBookOnTheChartsSelectedGenreSelectedCategory(Charts bookToInsertOnTheCharts){};

    public default void deleteBookFromChartsAllGenreAllCategory(String isbn){};

    public default void deleteBookFromChartsSelectedGenreAllCategory(String isbn, Genre genre){};

    public default void deleteBookFromChartsAllGenreSelectedCategory(String isbn, Category category){};

    public default void deleteBookFromChartsSelectedGenreSelectedCategory(String isbn, Genre genre, Category category){};

    public default ArrayList<Charts> getChartsForGenre(ChartFilter filter){return null;};

    public default ArrayList<Charts> getChartsForCategoryAndGenre(ChartFilter filter){return null;};

    public default  ArrayList<Book> getAllBooks(ChartFilter filter) {return null;};

    public default ArrayList<Category> getCategory(){return null;};

    public default ArrayList<Charts> getGeneralCharts(){return null;};

    public default ArrayList<Charts> getChartsForCategory(ChartFilter filter){return null;};

    public default void addNewOrderRegisteredClient(Order order){};

    public default void addNewOrderNotRegisteredClient(Order order){};

    public default Integer getLastOrderCode(){return null;};

    public default void updateLibroCardPoints(Order order){};

    public default void linkBookToOrder(Book book, int orderCode, int quantity){};

    public default void updateQuantityAvailableBook(int quantity, String isbn){};

    public default void insertNewUnregisteredClient(RegisteredClient client, String street, String houseNumber, String city, String postalCode){};


}
