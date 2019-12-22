package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ModelDatabaseOrder implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    public ArrayList<Order> getOrders(User user)
    {
        ArrayList<Order> orders;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, emailNotRegisteredUser, " +
                            "emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP, shipping, status, " +
                            "books.ISBN, books.description, books.formatName, books.genreName, books.imagePath, books.languageName, " +
                            "books.maxQuantity, books.pages, books.points, books.price, books.publicationYear, " +
                            "books.publishingHouseName, books.title," +
                            "GROUP_CONCAT(authors.name || ' ' || authors.surname) AS nameSurnameAuthors " +
                            "FROM orders JOIN makeUp on makeUp.code = orders.code JOIN books on makeUp.ISBN = books.ISBN " +
                            "JOIN authors ON authors.idAuthor = write.idAuthor " +
                            "WHERE emailRegisteredUser LIKE \"" + user.getEmail() + "\" or emailNotRegisteredUser LIKE \"" +
                            user.getEmail() + "\"" +
                            "GROUP BY books.ISBN");

        orders = resultSetToOrders(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(orders);
        return orders;
    }

    private ArrayList<Order> resultSetToOrders(ResultSet rs)
    {
        ArrayList<Order> orders = new ArrayList<>();
        Order order;
        Book book;
        ArrayList<Book> books;
        Address address;

        String originalCode = "";

        if(!rs.equals(null))
            originalCode = db.getSQLString(rs,"code");

        System.out.println(originalCode);

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
            {
                //se il codice dell'ordine è diverso da quello originale, ovvero il primo codice incontrato allora
                if(!db.getSQLString(rs,"code").equals(originalCode))
                {
                    //creo il un nuovo ordine che andrà nell'arrayList di ordini
                    order = new Order();

                    books = new ArrayList<>();
                    address = new Address(db.getSQLString(rs,"addressStreet"), db.getSQLString(rs, "addressHouseNumber"),
                            db.getSQLString(rs,"cityName"), db.getSQLString(rs,"cityCAP"));
                    order.setAddress(address);
                    book = new Book(db.getSQLString(rs, "ISBN"), db.getSQLString(rs, "title"),
                            db.getSQLStringArrayList(rs,"nameSurnameAuthors"), db.getSQLString(rs,"description"),
                            db.getSQLInt(rs,"points"),db.getSQLNumeric(rs,"price"), db.getSQLInt(rs,"publicationYear"),
                            db.getSQLString(rs,"publishingHouseName"),db.getSQLString(rs,"genreName"),
                            db.getSQLString(rs,"languageName"),db.getSQLInt(rs,"maxQuantity"),
                            db.getSQLInt(rs,"pages"), db.getSQLString(rs,"formatName"),
                            db.getSQLString(rs,"imagePath"));


                    orders.add(order);
                }


            }


            return orders;
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }
}
