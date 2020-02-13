package it.univr.library.View;

import it.univr.library.Book;
import it.univr.library.Controller.ControllerHeader;
import it.univr.library.Manager;
import it.univr.library.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Map;

public class ViewFXHeader implements ViewHeader
{
    @Override
    public void createLogo(HBox headerHBox)
    {
        ImageView logoImageView = new ImageView();

        try {
            logoImageView.setImage(new Image("/images/Logo.png"));
        }
        catch (NullPointerException | IllegalArgumentException e){
            logoImageView.setImage(null);
        }

        logoImageView.setFitWidth(92.0);
        logoImageView.setFitHeight(87.0);
        logoImageView.setPickOnBounds(true);
        logoImageView.setPreserveRatio(true);

        headerHBox.getChildren().add(logoImageView);
    }

    @Override
    public void createCatalogButton(HBox headerHBox, User user, Map<Book,Integer> cart)
    {
        Button catalogButton = new Button();
        HBox imageTextHBox = new HBox();
        ImageView homeImageView = new ImageView();
        Label catalogLabel = new Label();

        // *** setting button
        catalogButton.setId("catalogButton");
        catalogButton.setAlignment(Pos.CENTER);
        catalogButton.setContentDisplay(ContentDisplay.CENTER);
        catalogButton.setPrefHeight(32.0);
        catalogButton.setPrefWidth(98.0);
        catalogButton.setStyle("-fx-background-color: #ffa939;");
        catalogButton.setTextAlignment(TextAlignment.CENTER);
        catalogButton.setFont(new Font(13.0));
        catalogButton.setCursor(Cursor.HAND);

        // setting hbox
        imageTextHBox.setAlignment(Pos.CENTER);

        try {
            homeImageView.setImage(new Image("/images/home.png"));
        }
        catch (NullPointerException | IllegalArgumentException e){
            homeImageView.setImage(null);
        }

        // *** setting home image
        homeImageView.setFitWidth(20.0);
        homeImageView.setFitHeight(28.0);
        homeImageView.setPickOnBounds(true);
        homeImageView.setPreserveRatio(true);

        // *** setting label
        catalogLabel.setText("Catalog");
        catalogLabel.setFont(new Font(13.0));
        imageTextHBox.setMargin(catalogLabel, new Insets(0, 0, 0, 5.0));  //top, right, bottom, left

        // *** adding children to imageTextHBox (button)
        imageTextHBox.getChildren().addAll(homeImageView, catalogLabel);

        // *** adding imageTextHBox in the button (catalogButton)
        catalogButton.setGraphic(imageTextHBox);

        // *** setting button handler
        catalogButton.setOnAction(e -> new ControllerHeader().handleCatalogButton(catalogButton, user,cart));

        // *** adding button to headerHBox
        headerHBox.getChildren().add(catalogButton);
    }

    @Override
    public void createChartsButton(HBox headerHBox, User user, Map<Book,Integer> cart)
    {
        Button chartsButton = new Button();

        chartsButton.setId("loginSignUpButton");
        chartsButton.setAlignment(Pos.CENTER);
        chartsButton.setContentDisplay(ContentDisplay.CENTER);
        chartsButton.setPrefHeight(32.0);
        chartsButton.setPrefWidth(98.0);
        chartsButton.setStyle("-fx-background-color: #ffa939");
        chartsButton.setText("Charts");
        chartsButton.setTextAlignment(TextAlignment.CENTER);
        chartsButton.setFont(new Font(13));
        chartsButton.setCursor(Cursor.HAND);
        headerHBox.setMargin(chartsButton, new Insets(0, 0, 0, 10.0));  //top, right, bottom, left

        chartsButton.setOnAction(e -> new ControllerHeader().handleChartsButton(chartsButton, user, cart));

        headerHBox.getChildren().add(chartsButton);
    }

    @Override
    public HBox createRightHeaderHBox(HBox headerHBox)
    {
        HBox rightHeaderHBox = new HBox();

        rightHeaderHBox.setAlignment(Pos.CENTER_RIGHT);
        rightHeaderHBox.setPrefHeight(87.0);
        rightHeaderHBox.setPrefWidth(604.0);

        return rightHeaderHBox;
    }

    @Override
    public void createLoginSignupButton(HBox rightHeaderHbox, User user, Map<Book,Integer> cart)
    {
        Button loginSignUpButton = new Button();

        loginSignUpButton.setId("loginSignUpButton");
        loginSignUpButton.setAlignment(Pos.CENTER);
        loginSignUpButton.setContentDisplay(ContentDisplay.CENTER);
        loginSignUpButton.setPrefHeight(32.0);
        loginSignUpButton.setPrefWidth(124.0);
        loginSignUpButton.setStyle("-fx-background-color: #ffa939");
        loginSignUpButton.setText("Login - Sign Up");
        loginSignUpButton.setTextAlignment(TextAlignment.CENTER);
        loginSignUpButton.setWrapText(true);
        loginSignUpButton.setFont(new Font(13));
        loginSignUpButton.setCursor(Cursor.HAND);
        rightHeaderHbox.setMargin(loginSignUpButton, new Insets(0, 10.0, 0, 0));  //top, right, bottom, left

        loginSignUpButton.setOnAction(e -> new ControllerHeader().handleLoginSignUpButton(loginSignUpButton, user, cart));

        rightHeaderHbox.getChildren().add(loginSignUpButton);
    }

    @Override
    public void createOrderStatusButton(HBox rightHeaderHbox, User user, Map<Book,Integer> cart)
    {
        Button orderStatusUnregisteredUserButton = new Button();

        orderStatusUnregisteredUserButton.setId("orderStatusForUnregisteredUserButton");
        orderStatusUnregisteredUserButton.setAlignment(Pos.CENTER);
        orderStatusUnregisteredUserButton.setContentDisplay(ContentDisplay.CENTER);
        orderStatusUnregisteredUserButton.setPrefHeight(49.0);
        orderStatusUnregisteredUserButton.setPrefWidth(124.0);
        orderStatusUnregisteredUserButton.setStyle("-fx-background-color: #ffa939;");
        orderStatusUnregisteredUserButton.setText("Order Status for unregistered user");
        orderStatusUnregisteredUserButton.setTextAlignment(TextAlignment.CENTER);
        orderStatusUnregisteredUserButton.setWrapText(true);
        orderStatusUnregisteredUserButton.setFont(new Font(13.0));
        orderStatusUnregisteredUserButton.setCursor(Cursor.HAND);
        rightHeaderHbox.setMargin(orderStatusUnregisteredUserButton, new Insets(0, 15.0, 0, 0));  //top, right, bottom, left

        orderStatusUnregisteredUserButton.setOnAction(e -> new ControllerHeader().handleOrderStatusUnregisteredUser(orderStatusUnregisteredUserButton, user, cart));

        rightHeaderHbox.getChildren().add(orderStatusUnregisteredUserButton);
    }

    @Override
    public VBox createUserHyperlink(User user, HBox rightHeaderHBox, Map<Book,Integer> cart)
    {
        VBox userInfoVBox = new VBox();
        Hyperlink nameSurnameHyperlink = new Hyperlink();

        rightHeaderHBox.setMargin(userInfoVBox, new Insets(0, 30.0, 0, 0)); //top, right, bottom, left
        userInfoVBox.setAlignment(Pos.CENTER);

        nameSurnameHyperlink.setId("nameSurnameHyperlink");
        nameSurnameHyperlink.setText("Hello " + user.getName() + " " + user.getSurname() + ",");
        nameSurnameHyperlink.setFont(new Font("System Italic", 14.0));

        nameSurnameHyperlink.setOnAction(e -> new ControllerHeader().handlerUserPageHyperlink(nameSurnameHyperlink, user, cart));
        userInfoVBox.getChildren().add(nameSurnameHyperlink);

        return userInfoVBox;
    }

    @Override
    public void createLogOutButton(VBox userInfoVBox, HBox rightHeaderHBox, User user, Map<Book,Integer> cart)
    {
        Button logoutButton = new Button();

        logoutButton.setId("logoutButton");
        logoutButton.setStyle("-fx-background-color: #ffa939");
        logoutButton.setText("Log Out");
        logoutButton.setCursor(Cursor.HAND);

        logoutButton.setOnAction(e -> new ControllerHeader().handleLogOutButton(logoutButton,cart));
        userInfoVBox.getChildren().add(logoutButton);

        rightHeaderHBox.getChildren().add(userInfoVBox);
    }

    @Override
    public void createCartImageView(HBox rightHeaderHBox, User user, Map<Book,Integer> cart)
    {
        ImageView cartImageView = new ImageView();

        try {
            cartImageView.setImage(new Image("/images/cart.png"));
        }
        catch (NullPointerException | IllegalArgumentException e){
            cartImageView.setImage(null);
        }

        cartImageView.setFitWidth(64.0);
        cartImageView.setFitHeight(43.0);
        cartImageView.setPickOnBounds(true);
        cartImageView.setPreserveRatio(true);

        rightHeaderHBox.setMargin(cartImageView, new Insets(0, 20.0, 0, 0));  //top, right, bottom, left
        cartImageView.setCursor(Cursor.HAND);

        if(user instanceof Manager)
        {
            cartImageView.setDisable(true);
            cartImageView.setOpacity(0.2);
        }

        cartImageView.setOnMouseClicked(e -> new ControllerHeader().handleCartClicked(cartImageView, user, cart));
        rightHeaderHBox.getChildren().add(cartImageView);
    }
}
