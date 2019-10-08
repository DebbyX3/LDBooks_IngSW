package it.univr.library;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DatabaseConnection
{
    private Connection connection;
    private Statement stmt;
    private ResultSet rs;

    public void DBOpenConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/inglese.db");
        }
        catch ( Exception e )
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void DBCloseConnection()
    {
        try
        {
            rs.close();
            stmt.close();
            connection.close();
        }
        catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void executeSQLQuery(String query)
    {
        try
        {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ResultSet getResultSet()
    {
        return rs;
    }

    public String getSQLString(ResultSet rs, String name)
    {
        try
        {
            return rs.getString(name);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public Integer getSQLInt(ResultSet rs, String name)
    {
        try
        {
            return rs.getInt(name);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public BigDecimal getSQLNumeric(ResultSet rs, String name)
    {
        try
        {
            return rs.getBigDecimal(name);
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    public List<String> getSQLStringArrayList(ResultSet rs, String name)
    {
        try
        {
            return Arrays.asList(rs.getString(name).split(","));
        }
        catch(SQLException e)
        {
            return null;
        }
    }
}
