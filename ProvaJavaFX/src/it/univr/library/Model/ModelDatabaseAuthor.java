package it.univr.library.Model;

import it.univr.library.Author;
import it.univr.library.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseAuthor implements ModelAuthor {
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Author> getAuthors() {
        ArrayList<Author> authors;

        db.DBOpenConnection();
        db.executeSQLQuery("SELECT idAuthor, name, surname " +
                "FROM authors " +
                "ORDER BY name, surname ASC");

        authors = resultSetToAuthors(db.getResultSet());
        db.DBCloseConnection();

        return authors;
    }

    @Override
    public ArrayList<Author> getAuthorsForSpecificBook(String isbn) {
        ArrayList<Author> authorsForSpecificBook;

        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT authors.idAuthor, authors.name, authors.surname " +
                            "FROM books JOIN write ON books.ISBN = write.ISBN " +
                            "JOIN authors ON write.idAuthor = authors.idAuthor " +
                            "WHERE write.ISBN LIKE ?",List.of(isbn));

        authorsForSpecificBook = resultSetToAuthors(db.getResultSet());
        db.DBCloseConnection();

        return authorsForSpecificBook;
    }

    private ArrayList<Author> resultSetToAuthors(ResultSet rs) {

        ArrayList<Author> authors = new ArrayList<>();
        Author author;

        try
        {
            while (rs.next())
            {
                author = new Author(db.getSQLInt(rs, "idAuthor"), db.getSQLString(rs,"name"), db.getSQLString(rs, "surname"));
                authors.add(author);
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

    @Override
    public void addNewAuthor(String newNameAuthor, String newSurnameAuthor)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO authors(name,surname) " +
                "VALUES (?, ?)", List.of(newNameAuthor, newSurnameAuthor));
    }

    // forse non serve più questo
    @Override
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

    @Override
    public void linkBookToAuthors(int idAuthor, String isbn)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO write(idAuthor, ISBN) " +
                "VALUES (?, ?)", List.of(idAuthor, isbn));
    }

    @Override
    public void deleteLinkBookToAuthors(int idAuthor, String isbn)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "DELETE FROM write " +
                "WHERE write.ISBN LIKE ? AND write.idAuthor LIKE ? ",List.of(isbn,idAuthor));
    }

    @Override
    public ArrayList<Author> createArrayListAuthors(List<String> idNameSurnameAuthors) //1 luca mario$marzari,2 deb$pintani,3 culo$a;1 $marzari
    {
        ArrayList<Author> authors = new ArrayList<>();
        Author author;

        for (String tempAuthor: idNameSurnameAuthors)
        {
            Integer id;
            String name = "";
            String surname = "";

            String[] idName_SurnameSplit = tempAuthor.split("\\$");         //contains "id&name1 name2" "surname1 surname2" separate
            String[] id_NameSplit = idName_SurnameSplit[0].split("&");      //contains "id" and "name1 name2" separate

            id = Integer.parseInt(id_NameSplit[0]); //always contains only id

            if(id_NameSplit.length > 1)
                name = id_NameSplit[1]; //contains only "name1 name2" (only name)

            if(idName_SurnameSplit.length > 1)
                surname = idName_SurnameSplit[1]; //contains only "surname1 surname2" (only surname)

            System.out.println(id + "\n" + name + "\n" + surname + "\n");

            author = new Author(id, name, surname);
            authors.add(author);
        }
        return authors;
    }

}
