package it.univr.library;

import it.univr.library.Utils.DatabaseConnection;
import it.univr.library.Utils.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage)
    {
        StageManager homePageStage = new StageManager();
        homePageStage.setStageHomepage(primaryStage);
    }

    public static void main(String[] args)
    {
        launch(args);

        // Since we use the singleton pattern, we close the database connection here
        DatabaseConnection db = DatabaseConnection.getInstance();

        if(db != null)
            db.DBCloseConnection();
    }
}