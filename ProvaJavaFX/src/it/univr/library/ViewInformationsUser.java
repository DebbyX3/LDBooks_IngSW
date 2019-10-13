package it.univr.library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class ViewInformationsUser implements View {

    @Override
    public void buildInformations(RegisteredUser registeredUser, VBox informationVBox) {

        /***SETTING GRID PANE***/
        GridPane informationGridPane = new GridPane();
        informationGridPane.setAlignment(Pos.CENTER);
        informationGridPane.setPrefHeight(510.0);
        informationGridPane.setPrefWidth(883.0);

        /* **** COLUMNS **** */
        ColumnConstraints informationGridPaneColumn1 = new ColumnConstraints();
        informationGridPaneColumn1.setMaxWidth(282.20001220703125);
        informationGridPaneColumn1.setMinWidth(10.0);
        informationGridPaneColumn1.setPrefWidth(267.0);
        informationGridPaneColumn1.setHgrow(Priority.SOMETIMES);

        ColumnConstraints informationGridPaneColumn2 = new ColumnConstraints();
        informationGridPaneColumn2.setMaxWidth(352.39997558593745);
        informationGridPaneColumn2.setMinWidth(10.0);
        informationGridPaneColumn2.setPrefWidth(352.39997558593745);
        informationGridPaneColumn2.setHgrow(Priority.SOMETIMES);

        ColumnConstraints informationGridPaneColumn3 = new ColumnConstraints();
        informationGridPaneColumn3.setMaxWidth(271.4000244140625);
        informationGridPaneColumn3.setMinWidth(10.0);
        informationGridPaneColumn3.setPrefWidth(234.00002441406252);
        informationGridPaneColumn3.setHgrow(Priority.SOMETIMES);

        /* **** ROWS **** */
        RowConstraints infomationGridePaneRow1 = new RowConstraints();
        infomationGridePaneRow1.setMaxHeight(177.99996948242188);
        infomationGridePaneRow1.setMinHeight(10.0);
        infomationGridePaneRow1.setPrefHeight(68.00001449584961);
        infomationGridePaneRow1.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow2 = new RowConstraints();
        infomationGridePaneRow2.setMaxHeight(1.7976931348623157E308);
        infomationGridePaneRow2.setMinHeight(30.0);
        infomationGridePaneRow2.setPrefHeight(41.0);
        infomationGridePaneRow2.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow3 = new RowConstraints();
        infomationGridePaneRow3.setMaxHeight(280.19996643066406);
        infomationGridePaneRow3.setMinHeight(10.0);
        infomationGridePaneRow3.setPrefHeight(49.40000610351562);
        infomationGridePaneRow3.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow4 = new RowConstraints();
        infomationGridePaneRow4.setMaxHeight(283.3999786376953);
        infomationGridePaneRow4.setMinHeight(10.0);
        infomationGridePaneRow4.setPrefHeight(141.00001220703123);
        infomationGridePaneRow4.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow5 = new RowConstraints();
        infomationGridePaneRow5.setMaxHeight(307.4000244140625);
        infomationGridePaneRow5.setMinHeight(0.0);
        infomationGridePaneRow5.setPrefHeight(41.79998779296875);
        infomationGridePaneRow5.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow6 = new RowConstraints();
        infomationGridePaneRow6.setMaxHeight(360.39998168945306);
        infomationGridePaneRow6.setMinHeight(10.0);
        infomationGridePaneRow6.setPrefHeight(35.0);
        infomationGridePaneRow6.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow7 = new RowConstraints();
        infomationGridePaneRow7.setMaxHeight(360.39998168945306);
        infomationGridePaneRow7.setMinHeight(10.0);
        infomationGridePaneRow7.setPrefHeight(35.0);
        infomationGridePaneRow7.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow8 = new RowConstraints();
        infomationGridePaneRow8.setMaxHeight(360.39998168945306);
        infomationGridePaneRow8.setMinHeight(10.0);
        infomationGridePaneRow8.setPrefHeight(35.0);
        infomationGridePaneRow8.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow9 = new RowConstraints();
        infomationGridePaneRow9.setMaxHeight(360.39998168945306);
        infomationGridePaneRow9.setMinHeight(10.0);
        infomationGridePaneRow9.setPrefHeight(35.0);
        infomationGridePaneRow9.setVgrow(Priority.SOMETIMES);

        RowConstraints infomationGridePaneRow10 = new RowConstraints();
        infomationGridePaneRow10.setMaxHeight(360.39998168945306);
        infomationGridePaneRow10.setMinHeight(10.0);
        infomationGridePaneRow10.setPrefHeight(35.0);
        infomationGridePaneRow10.setVgrow(Priority.SOMETIMES);



        informationGridPane.getColumnConstraints().addAll(informationGridPaneColumn1, informationGridPaneColumn2, informationGridPaneColumn3);
        informationGridPane.getRowConstraints().addAll( infomationGridePaneRow1, infomationGridePaneRow2, infomationGridePaneRow3, infomationGridePaneRow4,
                                                        infomationGridePaneRow5, infomationGridePaneRow6, infomationGridePaneRow7, infomationGridePaneRow8,
                                                        infomationGridePaneRow9,infomationGridePaneRow10);

        Label name = new Label("name");
        name.setAlignment(Pos.CENTER_RIGHT);
        name.setLayoutX(210.0);
        name.setLayoutY(152.0);
        name.setPrefHeight(27.0);
        name.setPrefWidth(304.0);
        name.setPadding(new Insets(0, 10, 0,0));
        name.setTextAlignment(TextAlignment.CENTER);
        name.setContentDisplay(ContentDisplay.CENTER);
        name.setFont(new Font("System Bold", 14.0));
        name.setText("Name:");
        informationGridPane.setConstraints(name, 0, 1); // label in column 0, row 0

        Label n = new Label("n");
        n.setPrefHeight(17.0);
        n.setPrefWidth(384.0);
        n.setTextAlignment(TextAlignment.CENTER);
        n.setContentDisplay(ContentDisplay.CENTER);
        n.setFont(new Font("System", 12.0));
        n.setText(registeredUser.getName());
        informationGridPane.setConstraints(n, 1, 1); // label in column 0, row 0

        Label surname = new Label("surname");
        surname.setAlignment(Pos.CENTER_RIGHT);
        surname.setPadding(new Insets(0, 10, 0,0));
        surname.setLayoutX(190.0);
        surname.setLayoutY(107.0);
        surname.setPrefHeight(27.0);
        surname.setPrefWidth(279.0);
        surname.setTextAlignment(TextAlignment.CENTER);
        surname.setContentDisplay(ContentDisplay.CENTER);
        surname.setFont(new Font("System Bold", 14.0));
        surname.setText("Surname:");
        informationGridPane.setConstraints(surname, 0, 2); // label in column 0, row 0

        Label s = new Label("s");
        s.setPrefHeight(17.0);
        s.setPrefWidth(384.0);
        s.setTextAlignment(TextAlignment.CENTER);
        s.setContentDisplay(ContentDisplay.CENTER);
        s.setFont(new Font("System", 12.0));
        s.setText(registeredUser.getSurname());
        informationGridPane.setConstraints(s, 1, 2); // label in column 0, row 0



        Label address = new Label("address");
        address.setAlignment(Pos.CENTER_RIGHT);
        address.setPadding(new Insets(0, 10, 0,0));
        address.setLayoutX(210.0);
        address.setLayoutY(246.0);
        address.setPrefHeight(20.0);
        address.setPrefWidth(286.0);
        address.setTextAlignment(TextAlignment.CENTER);
        address.setContentDisplay(ContentDisplay.CENTER);
        address.setFont(new Font("System Bold", 14.0));
        address.setText("Address:");
        informationGridPane.setConstraints(address, 0, 3); // label in column 0, row 0


        /*** VBOX ON 3,1 ***/

        /*** ADDRESSES ***/
        VBox addressVBox = new VBox();
        addressVBox.setPrefWidth(358);
        addressVBox.setPrefHeight(143);
        addressVBox.setAlignment(Pos.CENTER);

        String street;
        String houseNumber;
        String city;
        String postalCode;

        ArrayList<Address> addresses = registeredUser.getAddresses();
        for (Address address1: addresses) {
            street = address1.getStreet();
            houseNumber = address1.getHouseNumber();
            city = address1.getCity();
            postalCode = address1.getPostalCode();

            HBox streetHBox = new HBox();
            streetHBox.setAlignment(Pos.CENTER);
            streetHBox.setPadding(new Insets(0,0,10,0));

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

            HBox HouseNumberHBox = new HBox();
            HouseNumberHBox.setAlignment(Pos.CENTER);
            HouseNumberHBox.setPadding(new Insets(0,0,10,0));

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
            HouseNumberHBox.getChildren().addAll(labelHouse,houseLabel);

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

            addressVBox.getChildren().addAll(streetHBox,HouseNumberHBox, cityHBox, postalHBox);

        }

        informationGridPane.setConstraints(addressVBox, 1, 3); // label in column 0, row 0

        /*** END ADDRESS ***/

        Label phoneNumber = new Label("phoneNumber");
        phoneNumber.setAlignment(Pos.CENTER_RIGHT);
        phoneNumber.setPadding(new Insets(0, 10, 0,0));
        phoneNumber.setLayoutX(176.0);
        phoneNumber.setLayoutY(264.0);
        phoneNumber.setPrefHeight(20.0);
        phoneNumber.setPrefWidth(282.0);
        phoneNumber.setTextAlignment(TextAlignment.CENTER);
        phoneNumber.setContentDisplay(ContentDisplay.CENTER);
        phoneNumber.setFont(new Font("System Bold", 14.0));
        phoneNumber.setText("Phone Number:");
        informationGridPane.setConstraints(phoneNumber, 0, 4); // label in column 0, row 0

        Label p = new Label("p");
        p.setLayoutX(292.0);
        p.setLayoutY(135.0);
        p.setPrefHeight(17.0);
        p.setPrefWidth(384.0);
        p.setTextAlignment(TextAlignment.CENTER);
        p.setContentDisplay(ContentDisplay.CENTER);
        p.setFont(new Font("System", 12.0));
        p.setText(registeredUser.getPhoneNumber());
        informationGridPane.setConstraints(p, 1, 4); // label in column 0, row 0

        Label mail = new Label("mail");
        mail.setAlignment(Pos.CENTER_RIGHT);
        mail.setPadding(new Insets(0, 10, 0,0));
        mail.setLayoutX(176.0);
        mail.setLayoutY(264.0);
        mail.setPrefHeight(20.0);
        mail.setPrefWidth(282.0);
        mail.setTextAlignment(TextAlignment.CENTER);
        mail.setContentDisplay(ContentDisplay.CENTER);
        mail.setFont(new Font("System Bold", 14.0));
        mail.setText("E-mail:");
        informationGridPane.setConstraints(mail, 0, 5); // label in column 0, row 0

        Label m = new Label("m");
        m.setLayoutX(292.0);
        m.setLayoutY(90.0);
        m.setPrefHeight(17.0);
        m.setPrefWidth(384.0);
        m.setTextAlignment(TextAlignment.CENTER);
        m.setContentDisplay(ContentDisplay.CENTER);
        m.setFont(new Font("System", 12.0));
        m.setText(registeredUser.getEmail());
        informationGridPane.setConstraints(m, 1, 5); // label in column 0, row 0

        informationGridPane.getChildren().addAll(name, n, surname, s, address, addressVBox, phoneNumber, p, mail, m);
        informationVBox.getChildren().addAll(informationGridPane);
    }
}
