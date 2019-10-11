package it.univr.library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ViewHeader implements View
{
    public void createLogo(HBox headerHBox)
    {
        ImageView logoImageView = new ImageView("/images/Logo.png");

        logoImageView.setFitWidth(92.0);
        logoImageView.setFitHeight(87.0);
        logoImageView.setPickOnBounds(true);
        logoImageView.setPreserveRatio(true);

        headerHBox.getChildren().add(logoImageView);
    }

    public void createCatalogButton(HBox headerHBox) {
        Button catalogButton = new Button();
        HBox imageTextHBox = new HBox();
        ImageView homeImageView = new ImageView("/images/home.png");
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

        // *** setting hbox
        imageTextHBox.setAlignment(Pos.CENTER);

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
        catalogButton.setOnAction(e -> new ControllerHeader().handleCatalogButton(catalogButton));

        // *** adding button to headerHBox
        headerHBox.getChildren().add(catalogButton);
    }

    public void createChartsButton(HBox headerHBox)
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

        chartsButton.setOnAction(e -> new ControllerHeader().handleChartsButton(chartsButton));

        headerHBox.getChildren().add(chartsButton);
    }

    public HBox createRightHeaderHBox(HBox headerHBox)
    {
        HBox rightHeaderHBox = new HBox();

        rightHeaderHBox.setAlignment(Pos.CENTER_RIGHT);
        rightHeaderHBox.setPrefHeight(87.0);
        rightHeaderHBox.setPrefWidth(604.0);

        return rightHeaderHBox;
    }

    public void createLoginSignupButton(HBox rightHeaderHbox)
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

        loginSignUpButton.setOnAction(e -> new ControllerHeader().handleLoginSignUpButton(loginSignUpButton));

        rightHeaderHbox.getChildren().add(loginSignUpButton);
    }

    // TODO: 11/10/2019 manca il settaggio dell'handler
    public void createOrderStatusButton(HBox rightHeaderHbox)
    {
        Button createOrderStatusButton = new Button();

        createOrderStatusButton.setId("orderStatusForUnregisteredUserButton");
        createOrderStatusButton.setAlignment(Pos.CENTER);
        createOrderStatusButton.setContentDisplay(ContentDisplay.CENTER);
        createOrderStatusButton.setPrefHeight(49.0);
        createOrderStatusButton.setPrefWidth(124.0);
        createOrderStatusButton.setStyle("-fx-background-color: #ffa939;");
        createOrderStatusButton.setText("Order Status for unregistered user");
        createOrderStatusButton.setTextAlignment(TextAlignment.CENTER);
        createOrderStatusButton.setWrapText(true);
        createOrderStatusButton.setFont(new Font(13.0));
        createOrderStatusButton.setCursor(Cursor.HAND);
        rightHeaderHbox.setMargin(createOrderStatusButton, new Insets(0, 15.0, 0, 0));  //top, right, bottom, left

        rightHeaderHbox.getChildren().add(createOrderStatusButton);
    }

    public VBox createUserHyperlink(User user)
    {
        VBox userInfoVBox = new VBox();
        Hyperlink nameSurnameHyperlink = new Hyperlink();

        userInfoVBox.setMargin(userInfoVBox, new Insets(0, 10.0, 0, 0)); //top, right, bottom, left
        userInfoVBox.setAlignment(Pos.CENTER);

        nameSurnameHyperlink.setId("nameSurnameHyperlink");
        nameSurnameHyperlink.setText("Hello " + user.getName() + " " + user.getSurname() + ",");
        nameSurnameHyperlink.setFont(new Font("System Italic", 14.0));

        userInfoVBox.getChildren().add(nameSurnameHyperlink);

        return userInfoVBox;
    }

    public void createLogOutButton(VBox userInfoVBox, HBox rightHeaderHBox)
    {
        Button logoutButton = new Button();

        logoutButton.setId("logoutButton");
        logoutButton.setStyle("-fx-background-color: #ffa939");
        logoutButton.setText("Log Out");

        userInfoVBox.getChildren().add(logoutButton);

        rightHeaderHBox.getChildren().add(userInfoVBox);
    }

    // TODO: 11/10/2019 manca il settaggio dell'handler
    public void createCartImageView(HBox rightHeaderHBox)
    {
        ImageView cartImageView = new ImageView("/images/cart.png");

        cartImageView.setFitWidth(64.0);
        cartImageView.setFitHeight(43.0);
        cartImageView.setPickOnBounds(true);
        cartImageView.setPreserveRatio(true);

        rightHeaderHBox.setMargin(cartImageView, new Insets(0, 10.0, 0, 0));  //top, right, bottom, left
        cartImageView.setCursor(Cursor.HAND);

        rightHeaderHBox.getChildren().add(cartImageView);
    }
}
