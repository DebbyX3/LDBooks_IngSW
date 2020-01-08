package it.univr.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDatabaseCharts implements Model{

    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Charts> getCharts(Filter filter)
    {
        ArrayList<Charts> chart;

        db.DBOpenConnection();

        db.executeSQLQuery( "SELECT rank, weeksIn, books.ISBN, books.title, GROUP_CONCAT(authors.name || ' ' || authors.surname) AS nameSurnameAuthors, books.genreName " +
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

    private ArrayList<Charts> resultSetToArrayListCharts(ResultSet rs)
    {
        ArrayList<Charts> chart = new ArrayList<>();
        Charts chartRecord;

        try
        {
            while (rs.next())
            {
                chartRecord = new Charts();

                chartRecord.setRank(db.getSQLInt(rs, "rank"));
                chartRecord.setWeeksIn(db.getSQLInt(rs, "weeksIn"));
                chartRecord.setISBN(db.getSQLString(rs, "ISBN"));
                chartRecord.setTitle(db.getSQLString(rs, "title"));
                chartRecord.setGenre(db.getSQLString(rs, "genreName"));
                chartRecord.setAuthors(db.getSQLStringList(rs, "nameSurnameAuthors"));

                chart.add(chartRecord);
            }

            return chart;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
    }
}


/*SELECT rank, weeksIn, books.ISBN, books.title, GROUP_CONCAT(authors.name || ' ' || authors.surname), books.genreName
FROM charts
JOIN books ON books.ISBN = charts.ISBN
JOIN write ON write.ISBN = books.ISBN
JOIN authors ON authors.idAuthor = write.idAuthor
WHERE genreName LIKE "Sci-Fi"
GROUP BY books.ISBN
ORDER BY rank*/