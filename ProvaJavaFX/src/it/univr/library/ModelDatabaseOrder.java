package it.univr.library;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseOrder implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    public ArrayList<Order> getOrders(User user)
    {
        ArrayList<Order> orders;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, " +
                            "emailNotRegisteredUser, emailRegisteredUser, addressStreet, addressHouseNumber, cityName, " +
                            "cityCAP, shipping, status, books.ISBN, books.description, books.formatName, books.genreName, " +
                            "books.imagePath, books.languageName, books.maxQuantity, books.pages, books.points, books.price, " +
                            "books.publicationYear, books.publishingHouseName, books.title, " +
                            "GROUP_CONCAT(authors.name || ' ' || authors.surname) AS nameSurnameAuthors " +
                            "FROM orders JOIN makeUp on makeUp.code = orders.code JOIN books on makeUp.ISBN = books.ISBN " +
                            "JOIN write ON write.ISBN = books.ISBN JOIN authors ON authors.idAuthor = write.idAuthor " +
                            "WHERE emailRegisteredUser LIKE ? " +
                            "GROUP BY books.ISBN " +
                            "ORDER BY orders.code ASC",
                            List.of(user.getEmail()));

        System.out.println("SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, " +
                "emailNotRegisteredUser, emailRegisteredUser, addressStreet, addressHouseNumber, cityName, " +
                "cityCAP, shipping, status, books.ISBN, books.description, books.formatName, books.genreName, " +
                "books.imagePath, books.languageName, books.maxQuantity, books.pages, books.points, books.price, " +
                "books.publicationYear, books.publishingHouseName, books.title, " +
                "GROUP_CONCAT(authors.name || ' ' || authors.surname) AS nameSurnameAuthors " +
                "FROM orders JOIN makeUp on makeUp.code = orders.code JOIN books on makeUp.ISBN = books.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE emailRegisteredUser LIKE \'" + user.getEmail() + "\' or emailNorRegisteredUser LIKE \"" + user.getEmail() + "\" " +
                "GROUP BY books.ISBN " +
                "ORDER BY orders.code ASC");

        orders = resultSetToOrders(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(orders);
        return orders;
    }

    private ArrayList<Order> resultSetToOrders(ResultSet rs)
    {
        ArrayList<Order> orders = new ArrayList<>();
        Order order;
        ArrayList<Book> books;

        String originalOrderCode = "0";
        String currentOrderCode;

        try
        {
            while (rs.next())
            {
                currentOrderCode = db.getSQLString(rs,"code");

                if(!currentOrderCode.equals(originalOrderCode))
                {
                    order = new Order();
                    books = new ArrayList<>();
                    createSingleOrder(order,rs,books);
                    //after create the singleOrder add into Orders arrayList of orders
                    orders.add(order);
                    //and change the currentOrderCode
                    originalOrderCode = currentOrderCode;
                }
                else
                {
                    //If the orderCode doesn't change so take the last order and add to his arrayList of book the new book find
                    Order recentOrder = orders.get(orders.size()-1); //now inside recentOrder we've the last order create
                    addBookToArrayList(recentOrder.getBooks(), rs); //and add the single book to order
                }
            }

            return orders;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    /**
     * This method creates a single order with all the information
     * @param order
     * @param rs
     * @param books
     */
    private void createSingleOrder(Order order, ResultSet rs, ArrayList<Book> books)
    {
        // ------- ADDRESS -------
        Address address = new Address(db.getSQLString(rs, "addressStreet"), db.getSQLString(rs, "addressHouseNumber"),
                db.getSQLString(rs, "cityName"), db.getSQLString(rs, "cityCAP"));
        order.setAddress(address);
        // ------- BOOK  -------
        addBookToArrayList(books, rs);
        order.setBooks(books);

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
    }

    //TODO probabilmente meglio usare il metodo già disponibile nel modelDatabaseBooks
    /**
     * This method creates a single book and adds it to the arrayList<Book> books.
     * @param books
     * @param rs
     */
    private void addBookToArrayList(ArrayList<Book> books, ResultSet rs)
    {
        Book book = new Book(db.getSQLString(rs, "ISBN"), db.getSQLString(rs, "title"),
                db.getSQLStringArrayList(rs, "nameSurnameAuthors"), db.getSQLString(rs, "description"),
                db.getSQLInt(rs, "points"), db.getSQLNumeric(rs, "price"), db.getSQLInt(rs, "publicationYear"),
                db.getSQLString(rs, "publishingHouseName"), db.getSQLString(rs, "genreName"),
                db.getSQLString(rs, "languageName"), db.getSQLInt(rs, "maxQuantity"),
                db.getSQLInt(rs, "pages"), db.getSQLString(rs, "formatName"),
                db.getSQLString(rs, "imagePath"));

        books.add(book);
    }
}
