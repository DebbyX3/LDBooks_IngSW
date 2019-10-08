package it.univr.library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager
{
    public void setStageCharts(Stage primaryStage, String message)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/bookCharts.fxml"));

            root = fxmlLoader.load();
            ControllerCharts controllerCharts = fxmlLoader.getController();

            controllerCharts.setMessage(message);

            primaryStage.setTitle("Charts - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageCatalog(Stage primaryStage)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/catalog.fxml"));

            root = fxmlLoader.load();
            ControllerCatalog controllerCatalog = fxmlLoader.getController();

            primaryStage.setTitle("Catalog - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
