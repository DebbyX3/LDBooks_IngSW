package it.univr.library.Controller;

import it.univr.library.*;
import it.univr.library.View.ViewHeader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerHeader
{
    public void createHeader(User user, HBox headerHBox, Map<Book,Integer> cart)
    {
        ViewHeader createHeader = new ViewHeader();
        HBox rightHeaderHBox;

        createHeader.createLogo(headerHBox);
        createHeader.createCatalogButton(headerHBox, user, cart);
        createHeader.createChartsButton(headerHBox, user,cart);

        rightHeaderHBox = createHeader.createRightHeaderHBox(headerHBox);
        checkHeader(user, rightHeaderHBox, cart); //puts the right button in the rightHeaderHBox

        createHeader.createCartImageView(rightHeaderHBox, user,cart);
        headerHBox.getChildren().add(rightHeaderHBox);
    }

    public void checkHeader(User user, HBox rightHeaderHbox, Map<Book,Integer> cart)
    {
        ViewHeader createHeader = new ViewHeader();

        if(user == null)
        {
            createHeader.createLoginSignupButton(rightHeaderHbox, user, cart);
            createHeader.createOrderStatusButton(rightHeaderHbox, user, cart);
        }
        else
        {
            VBox userInfoVBox = createHeader.createUserHyperlink(user, rightHeaderHbox,cart);
            createHeader.createLogOutButton(userInfoVBox, rightHeaderHbox,user,cart);
        }
    }

    public void handleCatalogButton(Button catalogButton, User user, Map<Book,Integer> cart)
    {
        StageManager catalogStage = new StageManager();
        catalogStage.setStageCatalog((Stage) catalogButton.getScene().getWindow(), user, cart);
    }

    public void handleChartsButton(Button chartsButton, User user, Map<Book,Integer> cart)
    {
        StageManager chartsStage = new StageManager();
        chartsStage.setStageCharts((Stage) chartsButton.getScene().getWindow(), user, cart);
    }

    public void handleLoginSignUpButton(Button loginSignUpButton, User user, Map<Book,Integer> cart) {
        StageManager loginStage = new StageManager();
        loginStage.setStageLogin((Stage) loginSignUpButton.getScene().getWindow(), user, cart);
    }

    public void handlerUserPageHyperlink(Hyperlink nameSurnameHyperlink, User user, Map<Book,Integer> cart)
    {
        StageManager userPageStage = new StageManager();

        if(user instanceof Manager)
            userPageStage.setStageManagerPage((Stage) nameSurnameHyperlink.getScene().getWindow(), user, cart);
        else if (user instanceof Client || user instanceof RegisteredClient)
            userPageStage.setStageUserPage((Stage) nameSurnameHyperlink.getScene().getWindow(), user, cart);
    }


    public void handleLogOutButton(Button logoutButton, Map<Book,Integer> cart) {

        cart.clear();
        StageManager catalogLogout = new StageManager();
        catalogLogout.setStageCatalog((Stage) logoutButton.getScene().getWindow(), null, cart);

    }

    public void handleOrderStatusUnregisteredUser(Button orderStatusUnregisteredUser, User user, Map<Book,Integer> cart)
    {
        StageManager orderStatusUnregUser = new StageManager();
        orderStatusUnregUser.setStageOrderUnregUser((Stage)orderStatusUnregisteredUser.getScene().getWindow(), user, cart);

    }

    public void handleCartClicked(ImageView cartImageView, User user, Map<Book,Integer> cart)
    {
        StageManager userCart = new StageManager();
        userCart.setStageUserCart((Stage)cartImageView.getScene().getWindow(), user, cart);
    }
}
