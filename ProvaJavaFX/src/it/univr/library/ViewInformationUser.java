package it.univr.library;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public class ViewInformationUser implements View
{
    private List<AddressFields> addressFieldsList = new ArrayList<>();

    @Override
    public void buildViewProfileInformation(RegisteredClient registeredUser, Label nameLabel, Label surnameLabel, Label phoneLabel, Label emailLabel, VBox addressVbox)
    {
        HBox streetHBox;
        Label streetLabel;

        HBox houseNumberHBox;
        Label houseNumberLabel;

        HBox cityHBox;
        Label citylabel;

        HBox postalCodeHBox;
        Label postalCodeLabel;

        String street;
        String houseNumber;
        String city;
        String postalCode;

        /* **** SETTING NAME, SURNAME, PHONE NUMBER AND EMAIL LABELS **** */
        nameLabel.setText(registeredUser.getName());
        surnameLabel.setText(registeredUser.getSurname());
        phoneLabel.setText(registeredUser.getPhoneNumber());
        emailLabel.setText(registeredUser.getEmail());

        for (Address address: registeredUser.getAddresses())
        {
            street = address.getStreet();
            houseNumber = address.getHouseNumber();
            city = address.getCity();
            postalCode = address.getPostalCode();

            /* **** SETTING STREET LABEL & HBOX **** */
            streetHBox = new HBox();
            streetHBox.setAlignment(Pos.CENTER_LEFT);
            streetHBox.setPadding(new Insets(0,0,2,0));

            streetLabel = new Label("streetLabel");
            streetLabel.setContentDisplay(ContentDisplay.LEFT);
            streetLabel.setPrefHeight(17.0);
            streetLabel.setPrefWidth(274.0);
            streetLabel.setText("Street: " + street);
            streetLabel.setFont(new Font("System",12));

            streetHBox.getChildren().add(streetLabel);

            /* **** SETTING HOUSE NUMBER LABEL & HBOX **** */
            houseNumberHBox = new HBox();
            houseNumberHBox.setPadding(new Insets(0,0,2,0));

            houseNumberLabel = new Label("houseNumberLabel");
            houseNumberLabel.setPrefHeight(17.0);
            //houseNumberLabel.setPrefWidth(274.0);
            houseNumberLabel.setText("House Number: " + houseNumber);
            houseNumberLabel.setFont(new Font("System",12));

            houseNumberHBox.getChildren().add(houseNumberLabel);

            /* **** SETTING CITY LABEL & HBOX **** */
            cityHBox = new HBox();
            cityHBox.setPadding(new Insets(0,0, 2,0));

            citylabel = new Label("citylabel");
            citylabel.setPrefHeight(17.0);
            //citylabel.setPrefWidth(274.0);
            citylabel.setText("City: " + city);
            citylabel.setFont(new Font("System",12));

            cityHBox.getChildren().add(citylabel);

            /* **** SETTING POSTAL CODE LABEL & HBOX **** */
            postalCodeHBox = new HBox();
            postalCodeHBox.setPadding(new Insets(0,0, 2,0));

            postalCodeLabel = new Label("postalCodeLabel");
            postalCodeLabel.setPrefHeight(17.0);
            //postalCodeLabel.setPrefWidth(274.0);
            postalCodeLabel.setText("Postal Code: " + postalCode);
            postalCodeLabel.setFont(new Font("System",12));

            postalCodeHBox.getChildren().add(postalCodeLabel);

            /* **** SETTING SEPARATOR **** */
            Separator separatorLine = new Separator();
            separatorLine.setPrefWidth(395);

            addressVbox.getChildren().addAll(streetHBox, houseNumberHBox, cityHBox, postalCodeHBox, separatorLine);
        }
    }

    @Override
    public void buildEditProfileInformation(RegisteredClient registeredUser, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField passwordTextField, Label mailLabel, VBox addressVBox)
    {
        /* **** SETTING NAME, SURNAME, PHONE NUMBER AND EMAIL TEXTFIELDS **** */
        nameTextField.setText(registeredUser.getName());
        surnameTextField.setText(registeredUser.getSurname());
        phoneTextField.setText(registeredUser.getPhoneNumber());
        mailLabel.setText(registeredUser.getEmail());
        passwordTextField.setText(registeredUser.getPassword());

        for (Address address: registeredUser.getAddresses())
        {
            addressFieldsList.add(createAddressField(address, addressVBox));
        }
    }

    private AddressFields createAddressField(Address address, VBox addressVBox)
    {
        AddressFields addressFields = new AddressFields(address);
        displayAddress(addressFields, addressVBox);
        return addressFields;
    }

    private void displayAddress (AddressFields addressFields, VBox addressVBox)
    {
        HBox streetHBox;
        Label streetLabel;
        TextField streetTextField;

        HBox houseNumberHBox;
        Label houseNumberLabel;
        TextField houseNumberTextField;

        HBox citiesPostalCodesHBox;
        Label citiesPostalCodesLabel;
        ComboBox citiesPostalCodesComboBox;

        /* **** SETTING STREET LABEL, TEXTFIELD & HBOX **** */
        streetHBox = new HBox();
        streetHBox.setAlignment(Pos.CENTER);
        streetHBox.setPadding(new Insets(10,0,10,0));

        streetLabel = new Label("streetLabel");
        streetLabel.setContentDisplay(ContentDisplay.CENTER);
        streetLabel.setPrefHeight(17.0);
        //streetLabel.setPrefWidth(130.0);
        streetLabel.setText("Street: ");
        streetLabel.setFont(new Font("System",12));

        streetTextField = addressFields.getStreetTextField();
        streetTextField.setPrefHeight(17.0);
        //streetTextField.setPrefWidth(274.0);

        streetHBox.getChildren().addAll(streetLabel, streetTextField);

        /* **** SETTING HOUSE NUMBER LABEL, TEXTFIELD & HBOX **** */
        houseNumberHBox = new HBox();
        houseNumberHBox.setAlignment(Pos.CENTER);
        houseNumberHBox.setPadding(new Insets(0,0,10,0));

        houseNumberLabel = new Label("houseNumberLabel");
        houseNumberLabel.setContentDisplay(ContentDisplay.CENTER);
        houseNumberLabel.setPrefHeight(17.0);
        //houseNumberLabel.setPrefWidth(130.0);
        houseNumberLabel.setText("House Number: ");
        houseNumberLabel.setFont(new Font("System",12));

        houseNumberTextField = addressFields.getHouseNumberTextField();
        houseNumberTextField.setPrefHeight(17.0);
        houseNumberTextField.setPrefWidth(Region.USE_COMPUTED_SIZE);

        houseNumberHBox.getChildren().addAll(houseNumberLabel, houseNumberTextField);

        /* **** SETTING CITIES AND POSTAL CODES LABEL, TEXTFIELD & HBOX **** */
        citiesPostalCodesHBox = new HBox();
        citiesPostalCodesHBox.setAlignment(Pos.CENTER);
        citiesPostalCodesHBox.setPadding(new Insets(0,0,10,0));

        citiesPostalCodesLabel = new Label("citiesPostalCodesLabel");
        citiesPostalCodesLabel.setContentDisplay(ContentDisplay.CENTER);
        citiesPostalCodesLabel.setPrefHeight(17.0);
        //citiesPostalCodesLabel.setPrefWidth(130.0);
        citiesPostalCodesLabel.setText("City and Postal Code: ");
        citiesPostalCodesLabel.setFont(new Font("System",12));

        citiesPostalCodesComboBox = addressFields.getCitiesAndPostalCodesComboBox();
        citiesPostalCodesHBox.getChildren().addAll(citiesPostalCodesLabel, citiesPostalCodesComboBox);

        /* **** SETTING SEPARATOR **** */
        Separator separatorLine = new Separator();
        separatorLine.setPrefWidth(336);

        addressVBox.getChildren().addAll(streetHBox, houseNumberHBox, citiesPostalCodesHBox, separatorLine);
    }

    @Override
    public List<AddressFields> getAddressFieldsList() {
        return addressFieldsList;
    }
}




