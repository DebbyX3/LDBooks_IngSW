package it.univr.library;

import javafx.scene.chart.Chart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseCharts implements Model {

    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Charts> getCharts(){return getCharts(new ChartFilter());}

    @Override
    public ArrayList<Charts> getCharts(ChartFilter filter) {
        boolean isFirstInQuery = true;
        ArrayList<Charts> chart;
        ArrayList<Object> queryParameters = new ArrayList<>();


        String query = "SELECT idChart, rank, weeksIn,Category, books.ISBN, books.title, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, books.genreName " +
                "FROM charts " +
                "JOIN books ON books.ISBN = charts.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN " +
                "JOIN authors ON authors.idAuthor = write.idAuthor ";

        if (filter.isGenreSetted()) {
            queryParameters.add(filter.getGenre().getName());
            query += "WHERE genreName LIKE ? ";
            isFirstInQuery = false;
        }
        if (filter.isCategorySetted()) {
            queryParameters.add(filter.getCategory());
            query += isFirstInQuery ? "WHERE " : "AND ";
            query += "Category LIKE ? ";
            isFirstInQuery = false;
        }

        query += "GROUP BY books.ISBN " +
                "ORDER BY rank";

        db.DBOpenConnection();
        db.executeSQLQuery(query, queryParameters);

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
                chartRecord.setCategory(db.getSQLString(rs,"Category"));
                chartRecord.setTitle(db.getSQLString(rs, "title"));
                chartRecord.setGenre(new Genre(db.getSQLString(rs, "genreName")));
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
        db.executeSQLUpdate("UPDATE charts " +
                "SET rank = ?, weeksIn = ?, ISBN = ?" +
                "WHERE idChart LIKE ?", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(),
                bookToUpdateInCharts.getISBN(), bookToUpdateInCharts.getId()));
    }

    @Override
    public void insertBookOnTheCharts(Charts bookToInsertOnTheCharts)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN, category) " +
                "VALUES(?, ?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN(), bookToInsertOnTheCharts.getCategory()));
    }

    @Override
    public void deleteBookFromCharts(String isbn)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "DELETE from charts " +
                                    "WHERE ISBN LIKE ?",List.of(isbn));
    }


    @Override
    public ArrayList<String> getCategory() {
        ArrayList<String> categories;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT DISTINCT charts.Category " +
                "FROM charts " +
                "WHERE Category IS NOT NULL");

        categories = resultSetToArrayListCategories(db.getResultSet());
        db.DBCloseConnection();

        return categories;
    }

    private ArrayList<String> resultSetToArrayListCategories(ResultSet rs) {
        ArrayList<String> categories = new ArrayList<>();

        try {
            while (rs.next()) {

               categories.add(  db.getSQLString(rs,"Category"));
            }

            return categories;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }

    public ArrayList<Charts> getChartsForCategory(String category){
        ArrayList<Charts> chart;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT idChart, rank, weeksIn, Category, books.ISBN, books.title, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, books.genreName " +
                "FROM charts " +
                "JOIN books ON books.ISBN = charts.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN " +
                "JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE Category LIKE ? " +
                "GROUP BY books.ISBN " +
                "ORDER BY rank", List.of(category));

        chart = resultSetToArrayListChartsCategory(db.getResultSet());
        db.DBCloseConnection();

        return chart;
    }

    private ArrayList<Charts> resultSetToArrayListChartsCategory(ResultSet rs) {
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
                chartRecord.setCategory(db.getSQLString(rs,"Category"));
                chartRecord.setTitle(db.getSQLString(rs, "title"));
                chartRecord.setGenre(new Genre(db.getSQLString(rs, "genreName")));
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




}
