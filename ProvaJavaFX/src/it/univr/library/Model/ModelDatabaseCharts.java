package it.univr.library.Model;

import it.univr.library.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseCharts implements ModelCharts
{
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Charts> getGeneralCharts()
    {
        ArrayList<Charts> chart;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT idChart, rank, weeksIn, Category, Genre, books.ISBN, books.title, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, books.genreName " +
                "FROM charts " +
                "JOIN books ON books.ISBN = charts.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN " +
                "JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE Category ISNULL AND Genre ISNULL " +
                "GROUP BY books.ISBN " +
                "ORDER BY rank " );

        chart = resultSetToArrayListCharts(db.getResultSet());
        db.DBCloseConnection();

        return chart;
    }

    @Override
    public ArrayList<Charts> getChartsForGenre(ChartFilter filter)
    {
        ArrayList<Charts> chart;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT idChart, rank, weeksIn, Category, Genre, books.ISBN, books.title, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, books.genreName " +
                "FROM charts " +
                "JOIN books ON books.ISBN = charts.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN " +
                "JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE Category ISNULL AND Genre LIKE ? " +
                "GROUP BY books.ISBN " +
                "ORDER BY rank ", List.of(filter.getGenre().getName()));

        chart = resultSetToArrayListCharts(db.getResultSet());
        db.DBCloseConnection();

        return chart;
    }

    @Override
    public ArrayList<Charts> getChartsForCategory(ChartFilter filter){
        ArrayList<Charts> chart;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT idChart, rank, weeksIn, Category, Genre, books.ISBN, books.title, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, books.genreName " +
                "FROM charts " +
                "JOIN books ON books.ISBN = charts.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN " +
                "JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE Category LIKE ? AND Genre ISNULL " +
                "GROUP BY books.ISBN " +
                "ORDER BY rank", List.of(filter.getCategory().getName()));

        chart = resultSetToArrayListCharts(db.getResultSet());
        db.DBCloseConnection();

        return chart;
    }

    @Override
    public ArrayList<Charts> getChartsForCategoryAndGenre(ChartFilter filter){
        ArrayList<Charts> chart;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT idChart, rank, weeksIn, Category, Genre, books.ISBN, books.title, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, books.genreName " +
                "FROM charts " +
                "JOIN books ON books.ISBN = charts.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN " +
                "JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE Category LIKE ? AND Genre LIKE ? " +
                "GROUP BY books.ISBN " +
                "ORDER BY rank ", List.of(filter.getCategory().getName(), filter.getGenre().getName()));

        chart = resultSetToArrayListCharts(db.getResultSet());
        db.DBCloseConnection();

        return chart;
    }

    private ArrayList<Charts> resultSetToArrayListCharts(ResultSet rs) {
        ArrayList<Charts> chart = new ArrayList<>();
        ModelAuthor authors = new ModelDatabaseAuthor();
        Charts chartRecord;

        try {
            while (rs.next()) {
                chartRecord = new Charts();

                chartRecord.setId(db.getSQLInt(rs,"idChart"));
                chartRecord.setRank(db.getSQLInt(rs, "rank"));
                chartRecord.setWeeksIn(db.getSQLInt(rs, "weeksIn"));
                chartRecord.setISBN(db.getSQLString(rs, "ISBN"));
                chartRecord.setCategory(new Category(db.getSQLString(rs,"Category")));
                chartRecord.setTitle(db.getSQLString(rs, "title"));
                chartRecord.setGenreChart(db.getSQLString(rs, "Genre"));
                chartRecord.setGenre(new Genre(db.getSQLString(rs,"genreName")));
                chartRecord.setAuthors(authors.createArrayListAuthors(db.getSQLStringList(rs, "idNameSurnameAuthors")));

                chart.add(chartRecord);
            }

            return chart;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    @Override
    public void updateChartsGenreAllCategoryAll(Charts bookToUpdateInCharts)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate("UPDATE charts " +
                "SET rank = ? , weeksIn = ? " +
                "WHERE idChart LIKE ? AND Category ISNULL AND Genre ISNULL", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(), bookToUpdateInCharts.getId()));
    }

    @Override
    public void updateChartsGenreSelectedCategoryAll(Charts bookToUpdateInCharts)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate(" UPDATE charts " +
                "    SET rank = ? , weeksIn = ? " +
                "    WHERE idChart LIKE ? AND Category ISNULL AND Genre LIKE ? ", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(), bookToUpdateInCharts.getId(), bookToUpdateInCharts.getGenreChart()));
    }

    @Override
    public void updateChartsGenreAllCategorySelected(Charts bookToUpdateInCharts)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate(" UPDATE charts " +
                "    SET rank = ? , weeksIn = ? " +
                "    WHERE idChart LIKE ? AND Category LIKE ? AND Genre ISNULL ", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(), bookToUpdateInCharts.getId(), bookToUpdateInCharts.getCategory().getName()));
    }

    @Override
    public void updateChartsGenreSelectedCategorySelected(Charts bookToUpdateInCharts)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate(" UPDATE charts " +
                "    SET rank = ? , weeksIn = ? " +
                "    WHERE idChart LIKE ? AND Category LIKE ? AND Genre LIKE ? ", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(), bookToUpdateInCharts.getId(), bookToUpdateInCharts.getCategory().getName(), bookToUpdateInCharts.getGenreChart()));
    }

    @Override
    public void insertBookOnTheChartsAllGenreAllCategory(Charts bookToInsertOnTheCharts)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN) " +
                "VALUES(?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN()));
    }

    @Override
    public void insertBookOnTheChartsSelectedGenreAllCategory(Charts bookToInsertOnTheCharts)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN, Genre) " +
                "VALUES(?, ?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN(), bookToInsertOnTheCharts.getGenreChart()));
    }

    @Override
    public void insertBookOnTheChartsAllGenreSelectedCategory(Charts bookToInsertOnTheCharts)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN, Category) " +
                "VALUES(?, ?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN(), bookToInsertOnTheCharts.getCategory().getName()));
    }

    @Override
    public void insertBookOnTheChartsSelectedGenreSelectedCategory(Charts bookToInsertOnTheCharts)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN, Category, Genre) " +
                "VALUES(?, ?, ?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN(), bookToInsertOnTheCharts.getCategory().getName(), bookToInsertOnTheCharts.getGenreChart()));
    }

    @Override
    public void deleteBookFromChartsAllGenreAllCategory(String isbn)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "DELETE from charts " +
                "WHERE ISBN LIKE ? AND Genre ISNULL AND Category ISNULL",List.of(isbn));

    }

    @Override
    public void deleteBookFromChartsSelectedGenreAllCategory(String isbn, Genre genre)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "DELETE from charts " +
                "WHERE ISBN LIKE ? AND Genre LIKE ? AND Category ISNULL",List.of(isbn, genre.getName()));
    }

    @Override
    public void deleteBookFromChartsAllGenreSelectedCategory(String isbn, Category category)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "DELETE from charts " +
                "WHERE ISBN LIKE ? AND Genre ISNULL AND Category LIKE ?",List.of(isbn, category.getName()));
    }

    @Override
    public void deleteBookFromChartsSelectedGenreSelectedCategory(String isbn, Genre genre, Category category)
    {
        db.DBOpenConnection();

        db.executeSQLUpdate( "DELETE from charts " +
                "WHERE ISBN LIKE ? AND Genre LIKE ? AND Category LIKE ?",List.of(isbn, genre.getName(), category.getName()));
    }

    @Override
    public ArrayList<Category> getCategory() {
        ArrayList<Category> categories;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT name " +
                "FROM Category " );

        categories = resultSetToArrayListCategories(db.getResultSet());
        db.DBCloseConnection();

        return categories;
    }

    private ArrayList<Category> resultSetToArrayListCategories(ResultSet rs)
    {
        ArrayList<Category> categories = new ArrayList<>();
        Category c;

        try
        {
            while (rs.next()) {

                c = new Category(db.getSQLString(rs,"name"));
               categories.add(c);
            }

            return categories;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}
