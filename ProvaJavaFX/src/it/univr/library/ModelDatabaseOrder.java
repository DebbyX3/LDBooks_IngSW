package it.univr.library;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseOrder implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Order> getOrders(User user)
    {
        ArrayList<Order> orders;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, " +
                            "emailNotRegisteredUser, emailRegisteredUser, addressStreet, addressHouseNumber, cityName, " +
                            "cityCAP, shipping, status, books.ISBN, books.description, books.formatName, books.genreName, " +
                            "books.imagePath, books.languageName, books.maxQuantity, books.pages, books.points, books.price, " +
                            "books.publicationYear, books.publishingHouseName, books.title, " +
                            "GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors " +
                            "FROM orders JOIN makeUp on makeUp.code = orders.code JOIN books on makeUp.ISBN = books.ISBN " +
                            "JOIN write ON write.ISBN = books.ISBN JOIN authors ON authors.idAuthor = write.idAuthor " +
                            "WHERE emailRegisteredUser LIKE ? " +
                            "GROUP BY books.ISBN, orders.code " +
                            "ORDER BY orders.code ASC",
                            List.of(user.getEmail()));


        orders = resultSetToOrders(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(orders);
        return orders;
    }

    @Override
    public ArrayList<Order> getOrderNotRegisteresUser(String mailNotRegUser, String orderCode)
    {
        ArrayList<Order> order;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, " +
                "emailNotRegisteredUser, emailRegisteredUser, addressStreet, addressHouseNumber, cityName, " +
                "cityCAP, shipping, status, books.ISBN, books.description, books.formatName, books.genreName, " +
                "books.imagePath, books.languageName, books.maxQuantity, books.pages, books.points, books.price," +
                "books.publicationYear, books.publishingHouseName, books.title, " +
                "GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors " +
                "FROM orders JOIN makeUp on makeUp.code = orders.code JOIN books on makeUp.ISBN = books.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE emailNotRegisteredUser LIKE ? AND orders.code LIKE ? " +
                "GROUP BY books.ISBN", List.of(mailNotRegUser, orderCode));

        order = resultSetToOrders(db.getResultSet());
        db.DBCloseConnection();

        return order;
    }

    @Override
    public ArrayList<Order> getSpecificMailOrders(String mail)
    {
        ArrayList<Order> order;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, " +
                "emailNotRegisteredUser, emailRegisteredUser, addressStreet, addressHouseNumber, cityName, " +
                "cityCAP, shipping, status, books.ISBN, books.description, books.formatName, books.genreName, " +
                "books.imagePath, books.languageName, books.maxQuantity, books.pages, books.points, books.price," +
                "books.publicationYear, books.publishingHouseName, books.title, " +
                "GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors " +
                "FROM orders JOIN makeUp on makeUp.code = orders.code JOIN books on makeUp.ISBN = books.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE emailNotRegisteredUser LIKE ? OR emailRegisteredUser LIKE ? " +
                "GROUP BY books.ISBN, orders.code " +
                "ORDER BY orders.code", List.of(mail,mail));

        order = resultSetToOrders(db.getResultSet());
        db.DBCloseConnection();

        return order;
    }

    private ArrayList<Order> resultSetToOrders(ResultSet rs)
    {
        ArrayList<Order> orders = new ArrayList<>();
        Order order;
        ArrayList<Book> books;

        String originalOrderCode = "";
        String currentOrderCode;


        try
        {
            while (rs.next())
            {
                currentOrderCode = db.getSQLString(rs,"code");

                if(originalOrderCode.equals("") || !currentOrderCode.equals(originalOrderCode))
                {
                    //change the currentOrderCode
                    originalOrderCode = currentOrderCode;
                    order = new Order();
                    books = new ArrayList<>();
                    createSingleOrder(order,rs,books);
                    //after create the singleOrder add into Orders arrayList of orders
                    orders.add(order);
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

    @Override
    public ArrayList<String> getMailsOrders()
    {
        ArrayList<String> mails;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT DISTINCT orders.emailRegisteredUser, orders.emailNotRegisteredUser " +
                            "FROM orders ");

        mails = resultSetToStringMails(db.getResultSet());
        db.DBCloseConnection();

        return mails;
    }

    private ArrayList<String> resultSetToStringMails(ResultSet rs)
    {
        ArrayList<String> mails = new ArrayList<>();
        String mail;
        try
        {
            while (rs.next())
            {
                mail = new String();
                if(db.getSQLString(rs,"emailRegisteredUser") != null)
                    mail = db.getSQLString(rs,"emailRegisteredUser") + " (Registered)";
                else
                    mail = db.getSQLString(rs,"emailNotRegisteredUser") + " (Not Registered)";
                mails.add(mail);
            }

            return mails;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    @Override
    public ArrayList<Order> getAllOrders()
    {
        ArrayList<Order> orders;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT orders.code, dateOrder, totalPrice, balancePoints, paymentType, emailNotRegisteredUser, " +
                            "emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP, shipping, " +
                            "status, books.ISBN, books.description, books.formatName, books.genreName, books.imagePath, " +
                            "books.languageName, books.maxQuantity, books.pages, books.points, books.price, " +
                            "books.publicationYear, books.publishingHouseName, books.title, " +
                            "GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors " +
                            "FROM orders JOIN makeUp on makeUp.code = orders.code " +
                            "JOIN books on makeUp.ISBN = books.ISBN JOIN write ON write.ISBN = books.ISBN " +
                            "JOIN authors ON authors.idAuthor = write.idAuthor " +
                            "GROUP BY books.ISBN, orders.code " +
                            "ORDER BY orders.code");

        orders = resultSetToOrders(db.getResultSet());
        db.DBCloseConnection();

        return orders;
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
        Address address = new Address(  db.getSQLString(rs, "addressStreet"), db.getSQLString(rs, "addressHouseNumber"),
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
        Model a = new ModelDatabaseAuthor();
        ArrayList<Author> authors = a.createArrayListAuthors(db.getSQLStringList(rs, "idNameSurnameAuthors"));

        Book book = new Book(db.getSQLString(rs, "ISBN"), db.getSQLString(rs, "title"),
                authors, db.getSQLString(rs, "description"),
                db.getSQLInt(rs, "points"), db.getSQLNumeric(rs, "price"), db.getSQLInt(rs, "publicationYear"),
                new PublishingHouse(db.getSQLString(rs, "publishingHouseName")), new Genre(db.getSQLString(rs, "genreName")),
                new Language(db.getSQLString(rs, "languageName")), db.getSQLInt(rs, "maxQuantity"),
                db.getSQLInt(rs, "pages"), new Format(db.getSQLString(rs, "formatName")),
                db.getSQLString(rs, "imagePath"));

        books.add(book);
    }

    @Override
    public void addNewOrderRegisteredClient(Order order)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( " INSERT INTO orders (dateOrder, totalPrice, balancePoints, paymentType, emailRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", List.of(order.getDate(), order.getTotalPrice(), order.getBalancePoints(), order.getPaymentType(), order.getEmailRegisteredUser(),
                order.getAddress().getStreet(), order.getAddress().getHouseNumber(), order.getAddress().getCity(), order.getAddress().getPostalCode(), order.getStatus()));
    }


    @Override
    public void addNewOrderNotRegisteredClient(Order order)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( " INSERT INTO orders (dateOrder, totalPrice, balancePoints, paymentType, emailNotRegisteredUser, addressStreet, addressHouseNumber, cityName, cityCAP, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", List.of(order.getDate(), order.getTotalPrice(), order.getBalancePoints(), order.getPaymentType(), order.getEmailNotRegisteredUser(),
                order.getAddress().getStreet(), order.getAddress().getHouseNumber(), order.getAddress().getCity(), order.getAddress().getPostalCode(), order.getStatus()));
    }

    @Override
    public Integer getLastOrderCode()
    {
        int code;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT code " +
                            "FROM orders " +
                            "WHERE ROWID IN ( SELECT max( ROWID ) FROM orders ) ");
        code = resultSetToCode(db.getResultSet());
        return code;
    }

    private int resultSetToCode(ResultSet rs)
    {
        int code = 0;
        try
        {
            while (rs.next())
            {
                code = db.getSQLInt(rs,"code");
            }

            return code;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return Integer.parseInt(null);

    }

    @Override
    public void linkBookToOrder(Book book, int orderCode, int quantity)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO makeUp (ISBN, code, purchasedQuantity) " +
                "VALUES (?, ?, ?) ", List.of(book.getISBN(), orderCode, quantity));

    }

}
