package it.univr.library;

import javafx.scene.chart.Chart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseCharts implements Model {

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
        Model authors = new ModelDatabaseAuthor();
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
    public void updateCharts(Charts bookToUpdateInCharts)
    {
        db.DBOpenConnection();


        if(!bookToUpdateInCharts.getCategory().equals("All"))
        {
            db.executeSQLUpdate("UPDATE charts " +
                    "SET rank = ?, weeksIn = ?, ISBN = ?, Category = ?" +
                    "WHERE idChart LIKE ?", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(),
                    bookToUpdateInCharts.getISBN(), bookToUpdateInCharts.getCategory(), bookToUpdateInCharts.getId()));
        }
        else
        {
            db.executeSQLUpdate("UPDATE charts " +
                    "SET rank = ?, weeksIn = ?, ISBN = ? " +
                    "WHERE idChart LIKE ?", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(),
                    bookToUpdateInCharts.getISBN(), bookToUpdateInCharts.getId()));
        }


    }

    @Override
    public void insertBookOnTheCharts(Charts bookToInsertOnTheCharts)
    {
        db.DBOpenConnection();


        if(!bookToInsertOnTheCharts.getCategory().equals("All"))
        {
            db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN, category) " +
                    "VALUES(?, ?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN(), bookToInsertOnTheCharts.getCategory()));
        }
        else
        {
            db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN) " +
                    "VALUES(?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN()));
        }



    }

    @Override
    public void deleteBookFromCharts(String isbn, String category)
    {
        db.DBOpenConnection();
        if(category.equals("All"))
        {
            db.executeSQLUpdate( "DELETE from charts " +
                    "WHERE ISBN LIKE ? ",List.of(isbn));
        }
        else
        {
            db.executeSQLUpdate( "DELETE from charts " +
                    "WHERE ISBN LIKE ? AND Category LIKE ?",List.of(isbn,category));
        }

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
