package it.univr.library;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ModelDatabaseLanguage implements Model {
    private DatabaseConnection db = new DatabaseConnection();

    @Override
    public ArrayList<Language> getLanguages()
    {
        ArrayList<Language> languages;
        db.DBOpenConnection();
        db.executeSQLQuery( "SELECT name FROM languages " +
                            "ORDER BY name ASC");
        languages = resultSetToArrayListLanguages(db.getResultSet());
        db.DBCloseConnection();

        return languages;
    }

    private ArrayList<Language> resultSetToArrayListLanguages(ResultSet rs) {
        ArrayList<Language> languages = new ArrayList<>();
        Language language;

        try
        {
            while (rs.next())   //bisogna per forza gestire l'eccezione per tutti i campi del DB
            {
                language = new Language(db.getSQLString(rs, "name"));

                languages.add(language);
            }

            return languages;
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

}
