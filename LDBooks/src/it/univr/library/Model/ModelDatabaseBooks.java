package it.univr.library.Model;

import it.univr.library.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseBooks implements ModelBooks
{
    private DatabaseConnection db = DatabaseConnection.getInstance();

    @Override
    public ArrayList<Book> getAllBooks() {
        return getAllBooks(new Filter());
    }

    @Override
    public ArrayList<Book> getAllBooks(Filter filter)
    {
        ArrayList<Book> books;
        ArrayList<Object> queryParameters = new ArrayList<>();

        String query = "SELECT books.ISBN,GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, " +
                "books.description, books.formatName, books.genreName, books.imagePath,books.languageName, " +
                "books.maxQuantity, books.pages, books.points, books.price, books.publicationYear, " +
                "books.publishingHouseName, books.title " +
                "FROM books " +
                "JOIN write ON books.ISBN = write.ISBN " +
                "JOIN authors ON write.idAuthor = authors.idAuthor " +
                "WHERE isValid = true ";

        if (filter.isGenreSetted()) {
            queryParameters.add(filter.getGenre().getName());
            query += "AND genreName LIKE ? ";
        }
        if (filter.isLanguageSetted()) {
            queryParameters.add(filter.getLanguage().getName());
            query += "AND languageName LIKE ? ";
        }

        query += "GROUP BY books.ISBN, title, languageName, formatName " +
                 "ORDER BY books.title, idNameSurnameAuthors ASC ";

        db.DBOpenConnection();
        db.executeSQLQuery(query, queryParameters);
        books = resultSetToArrayListBook(db.getResultSet());

        return books;
    }

    @Override
    public ArrayList<Book> getAllBooks(ChartFilter filter) {
        boolean isFirstInQuery = true;
        ArrayList<Book> books;
        ArrayList<Object> queryParameters = new ArrayList<>();

        String query = "SELECT books.ISBN,GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, " +
                "books.description, books.formatName, books.genreName, books.imagePath,books.languageName, " +
                "books.maxQuantity, books.pages, books.points, books.price, books.publicationYear, " +
                "books.publishingHouseName, books.title " +
                "FROM books " +
                "JOIN write ON books.ISBN = write.ISBN " +
                "JOIN authors ON write.idAuthor = authors.idAuthor " +
                "WHERE isValid = true ";

        if (filter.isGenreSetted()) {
            queryParameters.add(filter.getGenre().getName());
            query += "AND genreName LIKE ? ";
            isFirstInQuery = false;
        }


        query += "GROUP BY books.ISBN, title, languageName, formatName " +
                "ORDER BY books.title, idNameSurnameAuthors ASC ";

        db.DBOpenConnection();
        db.executeSQLQuery(query, queryParameters);
        books = resultSetToArrayListBook(db.getResultSet());

        return books;
    }

    @Override
    public Book getSpecificBook(String isbn)
    {
        ArrayList<Book> b;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT books.ISBN, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, " +
                "books.description, books.formatName, books.genreName, books.imagePath,books.languageName, " +
                "books.maxQuantity, books.pages, books.points, books.price, books.publicationYear, " +
                "books.publishingHouseName, books.title " +
                "FROM books " +
                "JOIN write ON books.ISBN = write.ISBN " +
                "JOIN authors ON write.idAuthor = authors.idAuthor " +
                "WHERE books.ISBN LIKE ? " +
                "GROUP BY books.ISBN, title, languageName, formatName " +
                "ORDER By books.title, idNameSurnameAuthors ASC", List.of(isbn));
        b = resultSetToArrayListBook(db.getResultSet());


        return b.get(0);
    }



    private ArrayList<Book> resultSetToArrayListBook(ResultSet rs) {
        ModelAuthor authors = new ModelDatabaseAuthor();
        ArrayList<Book> books = new ArrayList<>();
        Book singleBook;

        try {
            while (rs.next()) {
                singleBook = new Book();

                singleBook.setISBN(db.getSQLString(rs, "ISBN"));
                singleBook.setTitle(db.getSQLString(rs, "title"));
                singleBook.setAuthors(authors.createArrayListAuthors(db.getSQLStringList(rs, "idNameSurnameAuthors")));
                //singleBook.setAuthors(db.getSQLStringArrayList(rs, "nameSurnameAuthors"));
                singleBook.setDescription(db.getSQLString(rs, "description"));
                singleBook.setPoints(db.getSQLInt(rs, "points"));
                singleBook.setPrice(db.getSQLNumeric(rs, "price"));
                singleBook.setPublicationYear(db.getSQLInt(rs, "publicationYear"));
                singleBook.setPublishingHouse(new PublishingHouse(db.getSQLString(rs, "publishingHouseName")));
                singleBook.setGenre(new Genre(db.getSQLString(rs, "genreName")));
                singleBook.setLanguage(new Language(db.getSQLString(rs, "languageName")));
                singleBook.setMaxQuantity(db.getSQLInt(rs, "maxQuantity"));
                singleBook.setPages(db.getSQLInt(rs, "pages"));
                singleBook.setFormat(new Format(db.getSQLString(rs, "formatName")));
                singleBook.setImagePath(db.getSQLString(rs, "imagePath"));

                books.add(singleBook);
            }

            return books;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }


    @Override
    public void addNewBook(Book book)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO books(ISBN, title, description, points, publicationYear, price, " +
                                    "publishingHouseName, genreName, maxQuantity, pages, languageName, formatName, " +
                                    "imagePath) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",List.of(book.getISBN(), book.getTitle(), book.getDescription(),
                book.getPoints(), book.getPublicationYear(), book.getPrice(), book.getPublishingHouse().getName(), book.getGenre().getName(),
                book.getMaxQuantity(), book.getPages(), book.getLanguage().getName(), book.getFormat().getName(), book.getImagePath()));

    }

    @Override
    public void updateBook(Book book)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate("UPDATE books " +
                "SET title = ?, description = ?, points = ? , publicationYear = ?, price = ?, publishingHouseName = ?," +
                "genreName = ?, maxQuantity = ?, pages = ?, languageName = ?, formatName = ?, imagePath= ? " +
                "WHERE books.ISBN LIKE ?",List.of(book.getTitle(), book.getDescription(), book.getPoints(), book.getPublicationYear(),
                book.getPrice(),book.getPublishingHouse().getName(),book.getGenre().getName(), book.getMaxQuantity(),
                book.getPages(),book.getLanguage().getName(),book.getFormat().getName(),book.getImagePath(), book.getISBN()));

    }

    @Override
    public void updateAvailableQuantityBook(int quantity, String isbn)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate(" UPDATE books " +
                "SET maxQuantity = ? " +
                "WHERE books.ISBN LIKE ? ", List.of(quantity, isbn));

    }

    public void flagBookAsNotValid(String ISBN)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate(  "UPDATE books " +
                                    "SET isValid = 0 " +
                                    "WHERE ISBN LIKE ?", List.of(ISBN));

    }

    @Override
    public boolean doesISBNAlreadyExists(String ISBN)
    {
        boolean result = false;

        db.DBOpenConnection();

        db.executeSQLQuery(   "SELECT ISBN " +
                            "FROM books " +
                            "WHERE ISBN LIKE ?", List.of(ISBN));

        try
        {
            // If the ISBN does not exist in the books table
            // return false if there are no rows, true if there are some rows
            result = db.getResultSet().isBeforeFirst();
        }
        catch (SQLException e) {
            result = false;
        }



        return result;
    }
}
