package it.univr.library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StageManager
{
    public void setStageHomepage(Stage primaryStage)
    {
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

    public void setStageSpecificBook(Stage primaryStage, User user, BookGroup bookGroup, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/specificBook.fxml"));

            root = fxmlLoader.load();
            ControllerSpecificBook controllerSpecificBook = fxmlLoader.getController();
            controllerSpecificBook.setUser(user);
            controllerSpecificBook.setCart(cart);
            controllerSpecificBook.setHeader();
            controllerSpecificBook.setGroupBook(bookGroup);
            controllerSpecificBook.populateBookInfo();

            primaryStage.setTitle("Book - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStageCharts(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/bookCharts.fxml"));

            root = fxmlLoader.load();
            ControllerCharts controllerCharts = fxmlLoader.getController();
            controllerCharts.setUser(user);
            controllerCharts.setCart(cart);
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

    public void setStageCatalog(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/catalog.fxml"));

            root = fxmlLoader.load();
            ControllerCatalog controllerCatalog = fxmlLoader.getController();
            controllerCatalog.setUser(user);
            controllerCatalog.setCart(cart);
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

    public void setStageLogin(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/login.fxml"));

            root = fxmlLoader.load();
            ControllerLoginSignUp controllerLoginSignUp = fxmlLoader.getController();
            controllerLoginSignUp.setUser(user);
            controllerLoginSignUp.setCart(cart);
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

    public void setStageUserPage(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/userPage.fxml"));

            root = fxmlLoader.load();
            ControllerUserPage controllerUserPage = fxmlLoader.getController();
            controllerUserPage.setUser(user);
            controllerUserPage.setCart(cart);
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

    public void setStageManagerPage(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/managerPage.fxml"));

            root = fxmlLoader.load();
            ControllerManagerPage controllerManagerPage = fxmlLoader.getController();
            controllerManagerPage.setManager(manager);
            controllerManagerPage.setCart(cart);
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

    public void setStageOrderUser(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderUser.fxml"));

            root = fxmlLoader.load();
            ControllerOrderUser controllerOrderUser = fxmlLoader.getController();
            controllerOrderUser.setUser(user);
            controllerOrderUser.setCart(cart);
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

    public void setStageOrderManager(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderManager.fxml"));

            root = fxmlLoader.load();
            ControllerOrderManager controllerOrderManager = fxmlLoader.getController();
            controllerOrderManager.setManager(manager);
            controllerOrderManager.setCart(cart);
            controllerOrderManager.setHeader();

            primaryStage.setTitle("OrderManager - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageAddEditBooks(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/editCatalog.fxml"));

            root = fxmlLoader.load();
            ControllerEditCatalog controllerEditCatalog = fxmlLoader.getController();
            controllerEditCatalog.setManager(manager);
            controllerEditCatalog.setCart(cart);
            controllerEditCatalog.setHeader();

            primaryStage.setTitle("Edit Catalog - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStageAddNewAuthor(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/addAuthor.fxml"));

            root = fxmlLoader.load();
            ControllerAddAuthor controllerAddAuthor = fxmlLoader.getController();
            controllerAddAuthor.setManager(manager);
            controllerAddAuthor.setCart(cart);
            controllerAddAuthor.setHeader();

            primaryStage.setTitle("Add new Author- LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStageAddNewLanguage(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/addLanguage.fxml"));

            root = fxmlLoader.load();
            ControllerAddLanguage controllerAddLanguage = fxmlLoader.getController();
            controllerAddLanguage.setManager(manager);
            controllerAddLanguage.setCart(cart);
            controllerAddLanguage.setHeader();

            primaryStage.setTitle("Add new Language - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setStageAddNewBook(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/addBook.fxml"));

            root = fxmlLoader.load();
            ControllerAddBook controllerAddBook = fxmlLoader.getController();
            controllerAddBook.setManager(manager);
            controllerAddBook.setCart(cart);
            controllerAddBook.setHeader();

            primaryStage.setTitle("Add new Book - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageLibroCardManager(Stage primaryStage, User manager, Map<Book,Integer> cart) {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/librocardManager.fxml"));

            root = fxmlLoader.load();
            ControllerLibroCardsManager controllerLibroCardsManager = fxmlLoader.getController();
            controllerLibroCardsManager.setUser(manager);
            controllerLibroCardsManager.setCart(cart);
            controllerLibroCardsManager.setHeader();

            primaryStage.setTitle("LibroCard Manager- LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void setStageSignUp(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/signUp.fxml"));

            root = fxmlLoader.load();
            ControllerSignUp controllerSignUp = fxmlLoader.getController();
            controllerSignUp.setUser(user);
            controllerSignUp.setCart(cart);
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

    public void setStageViewProfile(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/viewProfileOld.fxml"));

            root = fxmlLoader.load();
            ControllerViewProfile controllerViewProfile = fxmlLoader.getController();
            controllerViewProfile.setUser(user);
            controllerViewProfile.setCart(cart);
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

    public void setStageEditProfile(Stage primaryStage, User user, Map<Book,Integer> cart) {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/EditProfile.fxml"));

            root = fxmlLoader.load();
            ControllerEditProfile controllerEditProfile = fxmlLoader.getController();
            controllerEditProfile.setUser(user);
            controllerEditProfile.setCart(cart);
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

    public void setStageViewLibroCard(Stage primaryStage, User user, Map<Book,Integer> cart) {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/librocardUser.fxml"));

            root = fxmlLoader.load();
            ControllerLibroCardUser controllerLibroCardUser = fxmlLoader.getController();
            controllerLibroCardUser.setUser(user);
            controllerLibroCardUser.setCart(cart);
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


    public void setStageOrderUnregUser(Stage primaryStage, User user, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderStatusUnregistered.fxml"));

            root = fxmlLoader.load();
            ControllerOrderUnregisteredUser controllerOrderUnregisteredUser = fxmlLoader.getController();
            controllerOrderUnregisteredUser.setUser(user);
            controllerOrderUnregisteredUser.setCart(cart);
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

    public void setStageUnregOrderUserView(Stage primaryStage, User user, ArrayList<Order> order, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/orderUser.fxml"));

            root = fxmlLoader.load();
            ControllerOrderUser controllerOrderUser = fxmlLoader.getController();
            controllerOrderUser.setUser(user);
            controllerOrderUser.setCart(cart);
            controllerOrderUser.populateOrderUser(order);
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


    public void setStageAddNewPhouse(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/addPublishingHouse.fxml"));

            root = fxmlLoader.load();
            ControllerAddPublishingHouse controllerAddPublishingHouse = fxmlLoader.getController();
            controllerAddPublishingHouse.setManager(manager);
            controllerAddPublishingHouse.setCart(cart);
            controllerAddPublishingHouse.setHeader();

            primaryStage.setTitle("Add new Publishing House - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageAddFormat(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/addFormat.fxml"));

            root = fxmlLoader.load();
            ControllerAddFormat controllerAddFormat = fxmlLoader.getController();
            controllerAddFormat.setManager(manager);
            controllerAddFormat.setCart(cart);
            controllerAddFormat.setHeader();

            primaryStage.setTitle("Add new Format - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageAddGenre(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/addGenre.fxml"));

            root = fxmlLoader.load();
            ControllerAddGenre controllerAddGenre = fxmlLoader.getController();
            controllerAddGenre.setManager(manager);
            controllerAddGenre.setCart(cart);
            controllerAddGenre.setHeader();

            primaryStage.setTitle("Add new Genre - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageEditBook(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/editBook.fxml"));

            root = fxmlLoader.load();
            ControllerEditBook controllerEditBook = fxmlLoader.getController();
            controllerEditBook.setManager(manager);
            controllerEditBook.setCart(cart);
            controllerEditBook.setHeader();

            primaryStage.setTitle("EditBook - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageUpdateCharts(Stage primaryStage, User manager, Map<Book,Integer> cart)
    {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/bookChartsManager.fxml"));

            root = fxmlLoader.load();
            ControllerUpdateChartsManager controllerUpdateChartsManager = fxmlLoader.getController();
            controllerUpdateChartsManager.setManager(manager);
            controllerUpdateChartsManager.setCart(cart);
            controllerUpdateChartsManager.setHeader();

            primaryStage.setTitle("UpdateCharts - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setStageUserCart(Stage primaryStage, User user, Map<Book,Integer> cart) {
        Parent root;

        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/cart.fxml"));

            root = fxmlLoader.load();
            ControllerCart controllerCart = fxmlLoader.getController();
            controllerCart.setUser(user);
            controllerCart.setCart(cart);
            controllerCart.setHeader();

            primaryStage.setTitle("UserCart - LD Books");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
