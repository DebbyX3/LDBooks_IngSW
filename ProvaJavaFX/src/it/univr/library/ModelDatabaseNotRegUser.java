package it.univr.library;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseNotRegUser implements Model {
    private DatabaseConnection db = new DatabaseConnection();

    public Order getOrderNotRegisteresUser(String mailNotRegUser, String orderCode)
    {
        Order order;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, " +
                        "emailNotRegisteredUser, emailRegisteredUser, addressStreet, addressHouseNumber, cityName, " +
                        "cityCAP, shipping, status, books.ISBN, books.description, books.formatName, books.genreName, " +
                        "books.imagePath, books.languageName, books.maxQuantity, books.pages, books.points, books.price," +
                        "books.publicationYear, books.publishingHouseName, books.title, " +
                        "GROUP_CONCAT(authors.name || ' ' || authors.surname) AS nameSurnameAuthors " +
                        "FROM orders JOIN makeUp on makeUp.code = orders.code JOIN books on makeUp.ISBN = books.ISBN " +
                        "JOIN write ON write.ISBN = books.ISBN JOIN authors ON authors.idAuthor = write.idAuthor " +
                        "WHERE emailNotRegisteredUser LIKE ? AND orders.code LIKE ? " +
                        "GROUP BY books.ISBN", List.of(mailNotRegUser, orderCode));

        order = resultSetToOrder(db.getResultSet());
        db.DBCloseConnection();

        return order;
    }

    //TODO ho provato ad usare ModelDatabaseOrder.createSingleOrder() ma bisognerebbe renderlo static e se lo rendi static ci sono dei problemi con db.get...
    private Order resultSetToOrder(ResultSet rs)
    {
        Order order = new Order();
        ArrayList<Book> books = new ArrayList<>();
        Book book;

        // ------- ADDRESS -------
        Address address = new Address(  db.getSQLString(rs, "addressStreet"), db.getSQLString(rs, "addressHouseNumber"),
                db.getSQLString(rs, "cityName"), db.getSQLString(rs, "cityCAP"));
        order.setAddress(address);

        // ------- CODE -------
        String code = db.getSQLString(rs,"code");
        order.setCode(code);

        // ------- DATE -------
        Long date;
        date = db.getSQLLong(rs,"dateOrder");
        order.setDate(date);

        // ------- TOTAL PRICE -------
        BigDecimal totalPrice;
        totalPrice = db.getSQLNumeric(rs,"totalPrice");
        order.setTotalPrice(totalPrice);

        // ------- BALANCE POINTS -------
        int balancePoints;
        balancePoints = db.getSQLInt(rs,"balancePoints");
        order.setBalancePoints(balancePoints);

        // ------- PAYMENT TYPE -------
        String paymentType;
        paymentType = db.getSQLString(rs,"paymentType");
        order.setPaymentType(paymentType);

        // ------- MAIL NOT REG -------
        String emailNotRegisteredUser;
        emailNotRegisteredUser = db.getSQLString(rs,"emailNotRegisteredUser");
        order.setEmailNotRegisteredUser(emailNotRegisteredUser);

        // ------- MAIL REG -------
        String emailRegisteredUser;
        emailRegisteredUser = db.getSQLString(rs,"emailRegisteredUser");
        order.setEmailRegisteredUser(emailRegisteredUser);

        // ------- SHIPPING COST -------
        BigDecimal shippingCost;
        shippingCost = db.getSQLNumeric(rs,"shipping");
        order.setShippingCost(shippingCost);

        // ------- STATUS -------
        String status;
        status = db.getSQLString(rs,"status");
        order.setStatus(status);

        try
        {
            while (rs.next())
            {

                book = new Book(db.getSQLString(rs, "ISBN"), db.getSQLString(rs, "title"),
                        db.getSQLStringArrayList(rs, "nameSurnameAuthors"), db.getSQLString(rs, "description"),
                        db.getSQLInt(rs, "points"), db.getSQLNumeric(rs, "price"), db.getSQLInt(rs, "publicationYear"),
                        db.getSQLString(rs, "publishingHouseName"), db.getSQLString(rs, "genreName"),
                        db.getSQLString(rs, "languageName"), db.getSQLInt(rs, "maxQuantity"),
                        db.getSQLInt(rs, "pages"), db.getSQLString(rs, "formatName"),
                        db.getSQLString(rs, "imagePath"));

                books.add(book);
            }

            order.setBooks(books);
            return order;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
