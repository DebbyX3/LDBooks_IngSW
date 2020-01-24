package it.univr.library;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControllerHeader
{
    public void createHeader(User user, HBox headerHBox)
    {
        ViewHeader createHeader = new ViewHeader();
        HBox rightHeaderHBox;

        createHeader.createLogo(headerHBox);
        createHeader.createCatalogButton(headerHBox, user);
        createHeader.createChartsButton(headerHBox, user);

        rightHeaderHBox = createHeader.createRightHeaderHBox(headerHBox);
        checkHeader(user, rightHeaderHBox); //puts the right button in the rightHeaderHBox

        createHeader.createCartImageView(rightHeaderHBox, user);
        headerHBox.getChildren().add(rightHeaderHBox);
    }

    public void checkHeader(User user, HBox rightHeaderHbox)
    {
        ViewHeader createHeader = new ViewHeader();

        if(user == null)
        {
            createHeader.createLoginSignupButton(rightHeaderHbox, user);
            createHeader.createOrderStatusButton(rightHeaderHbox, user);
        }
        else
        {
            VBox userInfoVBox = createHeader.createUserHyperlink(user, rightHeaderHbox);
            createHeader.createLogOutButton(userInfoVBox, rightHeaderHbox,user);
        }
    }

    public void handleCatalogButton(Button catalogButton, User user)
    {
        StageManager catalogStage = new StageManager();
        catalogStage.setStageCatalog((Stage) catalogButton.getScene().getWindow(), user);
    }

    public void handleChartsButton(Button chartsButton, User user)
    {
        StageManager chartsStage = new StageManager();
        chartsStage.setStageCharts((Stage) chartsButton.getScene().getWindow(), user);
    }

    public void handleLoginSignUpButton(Button loginSignUpButton, User user) {
        StageManager loginStage = new StageManager();
        loginStage.setStageLogin((Stage) loginSignUpButton.getScene().getWindow(), user);
    }

    public void handlerUserPageHyperlink(Hyperlink nameSurnameHyperlink, User user)
    {
        StageManager userPageStage = new StageManager();

        if(user instanceof Manager)
            userPageStage.setStageManagerPage((Stage) nameSurnameHyperlink.getScene().getWindow(), user);
        else if (user instanceof Client || user instanceof RegisteredClient)
            userPageStage.setStageUserPage((Stage) nameSurnameHyperlink.getScene().getWindow(), user);
    }



    public void handleLogOutButton(Button logoutButton) {
        //todo svuotare il carrello
        StageManager catalogLogout = new StageManager();
        catalogLogout.setStageCatalog((Stage) logoutButton.getScene().getWindow(), null);

    }

    public void handleOrderStatusUnregisteredUser(Button orderStatusUnregisteredUser, User user)
    {
        StageManager orderStatusUnregUser = new StageManager();
        orderStatusUnregUser.setStageOrderUnregUser((Stage)orderStatusUnregisteredUser.getScene().getWindow(), user);

    }

    //TODO: pass ArrayList<Book> cart to handleCartClicked here?
    public void handleCartClicked(ImageView cartImageView, User user)
    {
        StageManager userCart = new StageManager();
        userCart.setStageUserCart((Stage)cartImageView.getScene().getWindow(), user);
    }
}
