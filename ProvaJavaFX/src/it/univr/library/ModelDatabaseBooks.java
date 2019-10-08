package it.univr.library;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ModelDatabaseBooks implements Model
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Book> getBooks()
    {
        ArrayList<Book> books;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT books.ISBN, title, price, languageName, formatName, imagePath,  GROUP_CONCAT(name || ' ' || surname) AS nameSurnameAuthors " +
                            "FROM books " +
                            "JOIN write ON books.ISBN = write.ISBN " +
                            "JOIN authors ON write.idAuthor = authors.idAuthor " +
                            "GROUP BY books.ISBN, title, languageName, formatName " +
                            "ORDER By books.title, nameSurnameAuthors ASC ");
        books = resultSetToArrayListBook(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(books);
        return books;
    }

    private ArrayList<Book> resultSetToArrayListBook(ResultSet rs)
    {
        ArrayList<Book> books = new ArrayList<>();
        Book singleBook;

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
            {
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
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }
}
