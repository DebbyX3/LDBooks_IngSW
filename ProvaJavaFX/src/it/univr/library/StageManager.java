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

    public void setStageLogin(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));

            root = fxmlLoader.load();
            ControllerLoginSignUp controllerLoginSignUp = fxmlLoader.getController();

            controllerLoginSignUp.setUser(user);

            primaryStage.setTitle("Login or Sign Up - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageUserPage(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/userPage.fxml"));

            root = fxmlLoader.load();
            ControllerUserPage controllerUserPage = fxmlLoader.getController();


            primaryStage.setTitle("UserPage - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageOrderUser(Stage primaryStage, String message)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderUser.fxml"));

            root = fxmlLoader.load();
            ControllerOrderUser controllerOrderUser = fxmlLoader.getController();


            primaryStage.setTitle("OrderUser - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
