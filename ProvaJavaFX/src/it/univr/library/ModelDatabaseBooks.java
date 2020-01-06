package it.univr.library;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

public class ModelDatabaseBooks implements Model {
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Book> getBooks() {
        return getBooks(new Filter());
    }

    @Override
    public ArrayList<Book> getBooks(Filter filter) {
        boolean isFirstInQuery = true;
        ArrayList<Book> books;
        ArrayList<Object> queryParameters = new ArrayList<>();

        String query = "SELECT books.ISBN, title, price, languageName, formatName, imagePath,  GROUP_CONCAT(name || ' ' || surname) AS nameSurnameAuthors " +
                "FROM books " +
                "JOIN write ON books.ISBN = write.ISBN " +
                "JOIN authors ON write.idAuthor = authors.idAuthor ";

        if (filter.isGenreSetted()) {
            queryParameters.add(filter.getGenre().getName());
            query += "WHERE genreName LIKE ? ";
            isFirstInQuery = false;
        }
        if (filter.isLanguageSetted()) {
            queryParameters.add(filter.getLanguage().getName());
            query += isFirstInQuery ? "WHERE " : "AND ";
            query += "languageName LIKE ? ";
            isFirstInQuery = false;
        }

        query += "GROUP BY books.ISBN, title, languageName, formatName " +
                "ORDER By books.title, nameSurnameAuthors ASC ";

        db.DBOpenConnection();
        db.executeSQLQuery(query, queryParameters);
        books = resultSetToArrayListBook(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(books);
        return books;
    }

    private ArrayList<Book> resultSetToArrayListBook(ResultSet rs) {
        ArrayList<Book> books = new ArrayList<>();
        Book singleBook;

        try {
            while (rs.next()) {
                singleBook = new Book();

                singleBook.setISBN(db.getSQLString(rs, "ISBN"));
                singleBook.setTitle(db.getSQLString(rs, "title"));
                singleBook.setAuthors(db.getSQLStringArrayList(rs, "nameSurnameAuthors"));
                singleBook.setDescription(db.getSQLString(rs, "description"));
                singleBook.setPoints(db.getSQLInt(rs, "points"));
                singleBook.setPrice(db.getSQLNumeric(rs, "price"));
                singleBook.setPublicationYear(db.getSQLInt(rs, "publicationYear"));
                singleBook.setPublishingHouse(db.getSQLString(rs, "publishingHouseName"));
                singleBook.setGenre(db.getSQLString(rs, "genreName"));
                singleBook.setLanguage(db.getSQLString(rs, "languageName"));
                singleBook.setMaxQuantity(db.getSQLInt(rs, "maxQuantity"));
                singleBook.setPages(db.getSQLInt(rs, "pages"));
                singleBook.setFormat(db.getSQLString(rs, "formatName"));
                singleBook.setImagePath(db.getSQLString(rs, "imagePath"));

                books.add(singleBook);
            }

            return books;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    public ArrayList<String> getAuthors() {
        ArrayList<String> authors;

        db.DBOpenConnection();
        db.executeSQLQuery("SELECT name, surname " +
                "FROM authors ");

        authors = resultSetToAuthors(db.getResultSet());
        db.DBCloseConnection();

        return authors;
    }

    private ArrayList<String> resultSetToAuthors(ResultSet rs) {

        ArrayList<String> authors = new ArrayList<>();
        String name_surname;

        try
        {
            while (rs.next())
            {
                name_surname = db.getSQLString(rs,"name") + " " + db.getSQLString(rs, "surname");
                authors.add(name_surname);
            }

            return authors;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;

    }



    public void addNewAuthor(String newNameAuthor, String newSurnameAuthor)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO authors(name,surname) " +
                    "VALUES (?, ?)",List.of(newNameAuthor, newSurnameAuthor));
    }

    public ArrayList<String> getPublishingHouses()
    {
        ArrayList<String> publishingHouses;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name " +
                "FROM publishingHouses ");

        publishingHouses = resultSetToPublishingHouses(db.getResultSet());
        db.DBCloseConnection();

        return publishingHouses;
    }

    private ArrayList<String> resultSetToPublishingHouses(ResultSet rs)
    {
        ArrayList<String> publishingHouses = new ArrayList<>();
        String publishingHouse;

        try
        {
            while (rs.next())
            {
                publishingHouse = db.getSQLString(rs,"name");
                publishingHouses.add(publishingHouse);
            }

            return publishingHouses;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;


    }

    public int getAuthorID(String authorName, String authorSurname) {
        int authorID;

        db.DBOpenConnection();
        db.executeSQLQuery("SELECT authors.idAuthor " +
                "FROM authors " +
                "WHERE authors.name LIKE ? AND authors.surname LIKE ?", List.of(authorName,authorSurname));

        authorID = resultSetToAuthorID(db.getResultSet());
        db.DBCloseConnection();

        return authorID;
    }

    private int resultSetToAuthorID(ResultSet rs)
    {

        int authorID = 0;

        try
        {
            while (rs.next())
            {
                authorID = db.getSQLInt(rs,"idAuthor");
            }

            return authorID;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return Integer.parseInt(null);

    }

    public void linkBookToAuthors(int idAuthor, String isbn)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO write(idAuthor, ISBN) " +
                "VALUES (?, ?)", List.of(idAuthor, isbn));
    }

    public ArrayList<String> getFormats()
    {
        ArrayList<String> formats;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name " +
                "FROM formats ");

        formats = resultSetToformats(db.getResultSet());
        db.DBCloseConnection();

        return formats;
    }

    private ArrayList<String> resultSetToformats(ResultSet rs)
    {
        ArrayList<String> formats = new ArrayList<>();
        String format;

        try
        {
            while (rs.next())
            {
                format = db.getSQLString(rs,"name");
                formats.add(format);
            }

            return formats;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;

    }

    public void addNewBookToDB(Book book)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO books(ISBN, title, description, points, publicationYear, price, " +
                                    "publishingHouseName, genreName, maxQuantity, pages, languageName, formatName, " +
                                    "imagePath) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",List.of(book.getISBN(), book.getTitle(), book.getDescription(),
                book.getPoints(), book.getPublicationYear(), book.getPrice(), book.getPublishingHouse(), book.getGenre(),
                book.getMaxQuantity(), book.getPages(), book.getLanguage(), book.getFormat(), book.getImagePath()));
    }

    public void addNewPublishingHouse(String newPublishingHouse)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO publishingHouses(name) " +
                            "VALUES(?)", List.of(newPublishingHouse));
    }

    public void addNewFormat(String newFormat)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO formats(name) " +
                "VALUES(?)", List.of(newFormat));
    }

}
