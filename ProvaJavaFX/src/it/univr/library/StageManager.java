package it.univr.library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StageManager
{
    public void setStageHomepage(Stage primaryStage) {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/homePage.fxml"));

            root = fxmlLoader.load();
            ControllerHomepage controllerHomepage = fxmlLoader.getController();

            primaryStage.setTitle("Homepage - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageCharts(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/bookCharts.fxml"));

            root = fxmlLoader.load();
            ControllerCharts controllerCharts = fxmlLoader.getController();

            controllerCharts.setUser(user);
            controllerCharts.setHeader();

            primaryStage.setTitle("Charts - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageCatalog(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/catalog.fxml"));

            root = fxmlLoader.load();
            ControllerCatalog controllerCatalog = fxmlLoader.getController();
            controllerCatalog.setUser(user);
            controllerCatalog.setHeader();

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
            controllerLoginSignUp.setHeader();


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
            controllerUserPage.setUser(user);
            controllerUserPage.setHeader();

            primaryStage.setTitle("UserPage - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageManagerPage(Stage primaryStage, User manager)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/managerPage.fxml"));

            root = fxmlLoader.load();
            ControllerManagerPage controllerManagerPage = fxmlLoader.getController();
            controllerManagerPage.setManager(manager);
            controllerManagerPage.setHeader();

            primaryStage.setTitle("ManagerPage - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageOrderUser(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderUser.fxml"));

            root = fxmlLoader.load();
            ControllerOrderUser controllerOrderUser = fxmlLoader.getController();
            controllerOrderUser.setUser(user);
            controllerOrderUser.populateOrderUser();
            controllerOrderUser.setHeader();

            primaryStage.setTitle("OrderUser - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageSignUp(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/signUp.fxml"));

            root = fxmlLoader.load();
            ControllerSignUp controllerSignUp = fxmlLoader.getController();
            controllerSignUp.setUser(user);
            controllerSignUp.setHeader();


            primaryStage.setTitle("SignUp - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStageViewProfile(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/viewProfileOld.fxml"));

            root = fxmlLoader.load();
            ControllerViewProfile controllerViewProfile = fxmlLoader.getController();
            controllerViewProfile.setUser(user);
            controllerViewProfile.populateUserInformations();
            controllerViewProfile.setHeader();

            primaryStage.setTitle("ViewProfile - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStageEditProfile(Stage primaryStage, User user) {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/EditProfile.fxml"));

            root = fxmlLoader.load();
            ControllerEditProfile controllerEditProfile = fxmlLoader.getController();
            controllerEditProfile.setUser(user);
            controllerEditProfile.populateUserInformations();
            controllerEditProfile.setHeader();



            primaryStage.setTitle("Edit Profile - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStageViewLibroCard(Stage primaryStage, User user) {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/librocardUser.fxml"));

            root = fxmlLoader.load();
            ControllerLibroCardUser controllerLibroCardUser = fxmlLoader.getController();
            controllerLibroCardUser.setUser(user);
            controllerLibroCardUser.populateUserLibroCard();
            controllerLibroCardUser.setHeader();

            primaryStage.setTitle("My LibroCard - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void setStageOrderUnregUser(Stage primaryStage, User user)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderStatusUnregistered.fxml"));

            root = fxmlLoader.load();
            ControllerOrderUnregisteredUser controllerOrderUnregisteredUser = fxmlLoader.getController();
            controllerOrderUnregisteredUser.setUser(user);
            controllerOrderUnregisteredUser.setHeader();

            primaryStage.setTitle("Order Status Unregistered User - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageUnregOrderUserView(Stage primaryStage, User user, ArrayList<Order> order)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderUser.fxml"));

            root = fxmlLoader.load();
            ControllerOrderUser controllerOrderUser = fxmlLoader.getController();
            controllerOrderUser.setUser(user);
            controllerOrderUser.populateOrderUnregisteredUser(order);
            controllerOrderUser.setHeader();

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
