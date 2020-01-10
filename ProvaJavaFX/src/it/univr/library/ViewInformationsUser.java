package it.univr.library;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;


public class ViewInformationsUser implements View
{
    @Override
    public void buildInformations(RegisteredClient registeredUser, Label nameLabel, Label surnameLabel, Label phoneLabel, Label emailLabel, VBox addressVbox)
    {
        nameLabel.setText(registeredUser.getName());
        surnameLabel.setText(registeredUser.getSurname());
        phoneLabel.setText(registeredUser.getPhoneNumber());
        emailLabel.setText(registeredUser.getEmail());

        String street;
        String houseNumber;
        String city;
        String postalCode;

        for (Address address: registeredUser.getAddresses())
        {
            street = address.getStreet();
            houseNumber = address.getHouseNumber();
            city = address.getCity();
            postalCode = address.getPostalCode();

            HBox streetHBox = new HBox();
            streetHBox.setAlignment(Pos.CENTER);
            streetHBox.setPadding(new Insets(10,0,10,0));

            Label labelStreet = new Label("labelStreet");
            labelStreet.setContentDisplay(ContentDisplay.CENTER);
            labelStreet.setPrefHeight(17.0);
            labelStreet.setPrefWidth(130.0);
            labelStreet.setText("Street:");
            labelStreet.setFont(new Font("System",12));

            Label streetLabel = new Label("street");
            streetLabel.setContentDisplay(ContentDisplay.CENTER);
            streetLabel.setPrefHeight(17.0);
            streetLabel.setPrefWidth(274.0);
            streetLabel.setText(street);
            streetLabel.setFont(new Font("System",12));

            streetHBox.getChildren().addAll(labelStreet,streetLabel);

            HBox houseNumberHBox = new HBox();
            houseNumberHBox.setAlignment(Pos.CENTER);
            houseNumberHBox.setPadding(new Insets(0,0,10,0));

            Label labelHouse = new Label("labelHouse");
            labelHouse.setContentDisplay(ContentDisplay.CENTER);
            labelHouse.setPrefHeight(17.0);
            labelHouse.setPrefWidth(130.0);
            labelHouse.setText("House Number:");
            labelHouse.setFont(new Font("System",12));

            Label houseLabel = new Label("houseLabel");
            houseLabel.setContentDisplay(ContentDisplay.CENTER);
            houseLabel.setPrefHeight(17.0);
            houseLabel.setPrefWidth(274.0);
            houseLabel.setText(houseNumber);
            houseLabel.setFont(new Font("System",12));
            houseNumberHBox.getChildren().addAll(labelHouse,houseLabel);

            HBox cityHBox = new HBox();
            cityHBox.setAlignment(Pos.CENTER);
            cityHBox.setPadding(new Insets(0,0,10,0));

            Label labelCity = new Label("labelCity");
            labelCity.setContentDisplay(ContentDisplay.CENTER);
            labelCity.setPrefHeight(17.0);
            labelCity.setPrefWidth(130.0);
            labelCity.setText("City:");
            labelCity.setFont(new Font("System",12));

            Label citylabel = new Label("citylabel");
            citylabel.setContentDisplay(ContentDisplay.CENTER);
            citylabel.setPrefHeight(17.0);
            citylabel.setPrefWidth(274.0);
            citylabel.setText(city);
            citylabel.setFont(new Font("System",12));
            cityHBox.getChildren().addAll(labelCity,citylabel);

            HBox postalHBox = new HBox();
            postalHBox.setAlignment(Pos.CENTER);
            postalHBox.setPadding(new Insets(0,0,10,0));

            Label labelPostal = new Label("labelPostal");
            labelPostal.setContentDisplay(ContentDisplay.CENTER);
            labelPostal.setPrefHeight(17.0);
            labelPostal.setPrefWidth(130.0);
            labelPostal.setText("Postal Code:");
            labelPostal.setFont(new Font("System",12));

            Label postalLabel = new Label("postalLabel");
            postalLabel.setContentDisplay(ContentDisplay.CENTER);
            postalLabel.setPrefHeight(17.0);
            postalLabel.setPrefWidth(274.0);
            postalLabel.setText(postalCode);
            postalLabel.setFont(new Font("System",12));
            postalHBox.getChildren().addAll(labelPostal,postalLabel);

            Separator separatorLine = new Separator();
            separatorLine.setPrefWidth(336);


            addressVbox.getChildren().addAll(streetHBox, houseNumberHBox, cityHBox, postalHBox, separatorLine);
        }

    }

    @Override
    public void buildInformationsEdit(RegisteredClient registeredUser, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, Label mailLabel, VBox addressVbox)
    {
        nameTextField.setText(registeredUser.getName());
        surnameTextField.setText(registeredUser.getSurname());
        phoneTextField.setText(registeredUser.getPhoneNumber());
        mailLabel.setText(registeredUser.getEmail());

        String street;
        String houseNumber;
        String city;
        String postalCode;

        for (Address address: registeredUser.getAddresses())
        {
            street = address.getStreet();
            houseNumber = address.getHouseNumber();
            city = address.getCity();
            postalCode = address.getPostalCode();

            HBox streetHBox = new HBox();
            streetHBox.setAlignment(Pos.CENTER);
            streetHBox.setPadding(new Insets(10,0,10,0));

            Label labelStreet = new Label("labelStreet");
            labelStreet.setContentDisplay(ContentDisplay.CENTER);
            labelStreet.setPrefHeight(17.0);
            labelStreet.setPrefWidth(130.0);
            labelStreet.setText("Street:");
            labelStreet.setFont(new Font("System",12));

            Label streetLabel = new Label("street");
            streetLabel.setContentDisplay(ContentDisplay.CENTER);
            streetLabel.setPrefHeight(17.0);
            streetLabel.setPrefWidth(274.0);
            streetLabel.setText(street);
            streetLabel.setFont(new Font("System",12));

            streetHBox.getChildren().addAll(labelStreet,streetLabel);

            HBox houseNumberHBox = new HBox();
            houseNumberHBox.setAlignment(Pos.CENTER);
            houseNumberHBox.setPadding(new Insets(0,0,10,0));

            Label labelHouse = new Label("labelHouse");
            labelHouse.setContentDisplay(ContentDisplay.CENTER);
            labelHouse.setPrefHeight(17.0);
            labelHouse.setPrefWidth(130.0);
            labelHouse.setText("House Number:");
            labelHouse.setFont(new Font("System",12));

            Label houseLabel = new Label("houseLabel");
            houseLabel.setContentDisplay(ContentDisplay.CENTER);
            houseLabel.setPrefHeight(17.0);
            houseLabel.setPrefWidth(274.0);
            houseLabel.setText(houseNumber);
            houseLabel.setFont(new Font("System",12));
            houseNumberHBox.getChildren().addAll(labelHouse,houseLabel);

            HBox cityHBox = new HBox();
            cityHBox.setAlignment(Pos.CENTER);
            cityHBox.setPadding(new Insets(0,0,10,0));

            Label labelCity = new Label("labelCity");
            labelCity.setContentDisplay(ContentDisplay.CENTER);
            labelCity.setPrefHeight(17.0);
            labelCity.setPrefWidth(130.0);
            labelCity.setText("City:");
            labelCity.setFont(new Font("System",12));

            Label citylabel = new Label("citylabel");
            citylabel.setContentDisplay(ContentDisplay.CENTER);
            citylabel.setPrefHeight(17.0);
            citylabel.setPrefWidth(274.0);
            citylabel.setText(city);
            citylabel.setFont(new Font("System",12));
            cityHBox.getChildren().addAll(labelCity,citylabel);

            HBox postalHBox = new HBox();
            postalHBox.setAlignment(Pos.CENTER);
            postalHBox.setPadding(new Insets(0,0,10,0));

            Label labelPostal = new Label("labelPostal");
            labelPostal.setContentDisplay(ContentDisplay.CENTER);
            labelPostal.setPrefHeight(17.0);
            labelPostal.setPrefWidth(130.0);
            labelPostal.setText("Postal Code:");
            labelPostal.setFont(new Font("System",12));

            Label postalLabel = new Label("postalLabel");
            postalLabel.setContentDisplay(ContentDisplay.CENTER);
            postalLabel.setPrefHeight(17.0);
            postalLabel.setPrefWidth(274.0);
            postalLabel.setText(postalCode);
            postalLabel.setFont(new Font("System",12));
            postalHBox.getChildren().addAll(labelPostal,postalLabel);

            Separator separatorLine = new Separator();
            separatorLine.setPrefWidth(336);


            addressVbox.getChildren().addAll(streetHBox, houseNumberHBox, cityHBox, postalHBox, separatorLine);
        }

    }

}




