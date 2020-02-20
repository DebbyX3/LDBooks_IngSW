package it.univr.library;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class DatabaseConnection
{
    private static DatabaseConnection uniqueInstance;

    private Connection connection;
    private ResultSet rs;
    private int updateRow;

    private DatabaseConnection()
    {}

    public static DatabaseConnection getInstance()
    {
        if(uniqueInstance == null)
            uniqueInstance = new DatabaseConnection();

        return uniqueInstance;
    }

    public void DBOpenConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/LDBooksDatabaseNew.db");
        }
        catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void DBCloseConnection()
    {
        try
        {
            if(rs != null) // rs is null when an executeSQLUpdate is performed
                rs.close();
            connection.close();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void executeSQLQuery(String query)
    {
        executeSQLQuery(query, List.of());
    }

    public void executeSQLQuery(String query, List<Object> args)
    {
        try
        {
            final PreparedStatement preparedStmt = prepareStatement(query, args);
            rs = preparedStmt.executeQuery();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void executeSQLUpdate(String query)
    {
        executeSQLUpdate(query, List.of());
    }

    public void executeSQLUpdate(String query, List<Object> args)
    {
        try
        {
            final PreparedStatement preparedStmt = prepareStatement(query, args);
            updateRow = preparedStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private PreparedStatement prepareStatement(String query, List<Object> args)
    {
        Object objectClass;

        try
        {
            final PreparedStatement preparedStmt = connection.prepareStatement(query);

            for (int i = 0; i < args.size(); i++) {
                objectClass = args.get(i);

                if (objectClass instanceof String)
                    preparedStmt.setString(i + 1, (String) objectClass);
                else if (objectClass instanceof BigDecimal)
                    preparedStmt.setBigDecimal(i + 1, (BigDecimal) objectClass);
                else if (objectClass instanceof Boolean)
                    preparedStmt.setBoolean(i + 1, (Boolean) objectClass);
                else if (objectClass instanceof Date)
                    preparedStmt.setDate(i + 1, (Date) objectClass);
                else if (objectClass instanceof Double)
                    preparedStmt.setDouble(i + 1, (Double) objectClass);
                else if (objectClass instanceof Float)
                    preparedStmt.setFloat(i + 1, (Float) objectClass);
                else if (objectClass instanceof Integer)
                    preparedStmt.setInt(i + 1, (Integer) objectClass);
                else if (objectClass instanceof Long)
                    preparedStmt.setLong(i + 1, (Long) objectClass);
                else if (objectClass == null)
                    preparedStmt.setNull(i + 1, Types.NULL);
            }

            return preparedStmt;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return null;
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

    public Long getSQLLong(ResultSet rs, String name)
    {
        try
        {
            return rs.getLong(name);
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

    public List<String> getSQLStringList(ResultSet rs, String name)
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
