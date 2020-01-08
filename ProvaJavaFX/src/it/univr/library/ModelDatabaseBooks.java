package it.univr.library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        String query = "SELECT books.ISBN, title, price, languageName, formatName, imagePath,  GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors " +
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
                "ORDER By books.title, idNameSurnameAuthors ASC ";

        db.DBOpenConnection();
        db.executeSQLQuery(query, queryParameters);
        books = resultSetToArrayListBook(db.getResultSet());
        db.DBCloseConnection();

        System.out.println(books);
        return books;
    }

    private ArrayList<Book> resultSetToArrayListBook(ResultSet rs) {
        Model authors = new ModelDatabaseAuthor();
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





}
