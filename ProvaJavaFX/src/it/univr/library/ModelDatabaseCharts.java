package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseCharts implements Model {

    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Charts> getCharts(Filter filter) {
        ArrayList<Charts> chart;

        db.DBOpenConnection();

        db.executeSQLQuery("SELECT idChart, rank, weeksIn, books.ISBN, books.title, GROUP_CONCAT(authors.idAuthor || '&' || name || '$' || surname) AS idNameSurnameAuthors, books.genreName " +
                "FROM charts " +
                "JOIN books ON books.ISBN = charts.ISBN " +
                "JOIN write ON write.ISBN = books.ISBN " +
                "JOIN authors ON authors.idAuthor = write.idAuthor " +
                "WHERE genreName LIKE ? " +
                "GROUP BY books.ISBN " +
                "ORDER BY rank", List.of(filter.getGenre().getName()));

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
                "SET rank = ?, weeksIn = ?, ISBN = ? " +
                "WHERE idChart LIKE ?", List.of(bookToUpdateInCharts.getRank(), bookToUpdateInCharts.getWeeksIn(),
                bookToUpdateInCharts.getISBN(), bookToUpdateInCharts.getId()));
    }

    @Override
    public void insertBookOnTheCharts(Charts bookToInsertOnTheCharts)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "INSERT INTO charts(rank, weeksIn, ISBN) " +
                "VALUES(?, ?, ?)", List.of(bookToInsertOnTheCharts.getRank(), bookToInsertOnTheCharts.getWeeksIn(),bookToInsertOnTheCharts.getISBN()));
    }

    @Override
    public void deleteBookFromCharts(String isbn)
    {
        db.DBOpenConnection();
        db.executeSQLUpdate( "DELETE from charts " +
                                    "WHERE ISBN LIKE ?",List.of(isbn));
    }

}
