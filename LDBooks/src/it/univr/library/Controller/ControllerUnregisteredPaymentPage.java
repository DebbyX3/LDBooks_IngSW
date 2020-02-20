package it.univr.library.Controller;

import it.univr.library.*;
import it.univr.library.Model.ModelDatabaseUserAddress;
import it.univr.library.Model.ModelDatabaseUserInfo;
import it.univr.library.Model.ModelUserAddress;
import it.univr.library.Model.ModelUserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerUnregisteredPaymentPage
{

    @FXML
    private HBox headerHBox;

    @FXML
    private Button continueAsUnregisteredButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField mailTextField;

    @FXML
    private ComboBox cityComboBox;
    private ObservableList<String> citiesAndPostalCodesComboboxData = FXCollections.observableArrayList();

    @FXML
    private TextField houseNumberTextField;


    private User user;
    private Map<Book, Integer> cart;
    //contains methods and attributes that help managing addresses
    private ControllerUserInfo controllerUserInfo = new ControllerUserInfo();

    @FXML
    private void initialize()
    {
        //populate comboBox of cities
        citiesAndPostalCodesComboboxData = controllerUserInfo.populateCitiesAndPostalCodesCombobox();

        cityComboBox.setItems(citiesAndPostalCodesComboboxData);
        cityComboBox.getSelectionModel().selectFirst();

        continueAsUnregisteredButton.setOnAction(this::handleContinueAsUnregisteredButton);
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader() {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox, cart);
    }

    private void handleContinueAsUnregisteredButton(ActionEvent actionEvent)
    {
        if(isValidFields())
        {
            // create a registered Client and add into db in order to have also the shipAddress in
            Client newClient = createNewClient();

            if(newClient != null) {
                StageManager paymentPage = new StageManager();
                paymentPage.setStagePaymentPage((Stage) continueAsUnregisteredButton.getScene().getWindow(), newClient, cart);
            }
        }
    }

    private boolean isValidFields()
    {
        StringBuilder error = new StringBuilder();

        if(nameTextField.getText().trim().isEmpty())
            error.append("- Name must be filled!\n");
        else
            if(isNumerical(nameTextField.getText().trim()))
                error.append("- Name must be alphabetical!\n");


        if(surnameTextField.getText().trim().isEmpty())
            error.append("- Surname must be filled!\n");
        else
            if(isNumerical(surnameTextField.getText().trim()))
                error.append("- Surname must be alphabetical!\n");

        if(streetTextField.getText().trim().isEmpty())
            error.append("- Street must be filled!\n");
        else
            if(isNumerical(streetTextField.getText().trim()))
                error.append("- Street must be alphabetical!\n");


        if(houseNumberTextField.getText().trim().isEmpty())
            error.append("- House Number must be filled!\n");

        if(houseNumberTextField.getText().trim().isEmpty())
            error.append("- Phone Number must be filled!\n");
        else
            if(!isNumerical(phoneNumberTextField.getText().trim()))
            error.append("- Phone number must be numerical!\n");

        if(mailTextField.getText().trim().isEmpty())
            error.append("- Mail must be filled!\n");
        else
            if(!isMailValid(mailTextField.getText()))
                error.append("- Mail is not in the right format (a@b.com)!\n");

        if(!error.toString().isEmpty())
            displayAlert(error.toString());

        return error.toString().isEmpty();

    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }

    private Client createNewClient()
    {
        Client clientToReturn = null;

        RegisteredClient newClient = new RegisteredClient();
        newClient.setName(nameTextField.getText());
        newClient.setSurname(surnameTextField.getText());
        newClient.setPhoneNumber(phoneNumberTextField.getText());
        newClient.setEmail(mailTextField.getText());

        Address shipAddress = new Address(streetTextField.getText(), houseNumberTextField.getText(),
                controllerUserInfo.getCityFromCombobox(cityComboBox),controllerUserInfo.getPostalCodeFromCombobox(cityComboBox));

        newClient.setSingleAddress(shipAddress);

        //check if mail is unique
        ModelUserInfo DBcheckUser = new ModelDatabaseUserInfo();
        ModelUserAddress DBAddressUser = new ModelDatabaseUserAddress();

        if(!DBcheckUser.doesMailUnregisteredAlreadyExist(newClient) && !DBcheckUser.doesMailAlreadyExist(newClient))
        {
            //call method to add user to db
            DBcheckUser.addUnregisteredUser(newClient, shipAddress);

            //call method to first check if address already exists then if doesn't exist, puts it into db.
            DBAddressUser.addAddress(newClient, shipAddress); //take the first and only address

            clientToReturn = new Client();
            clientToReturn.setName(nameTextField.getText());
            clientToReturn.setSurname(surnameTextField.getText());
            clientToReturn.setPhoneNumber(phoneNumberTextField.getText());
            clientToReturn.setEmail(mailTextField.getText());
        }
        else
            displayAlert("This e-mail is already linked to another user! Try another one :)");

        return clientToReturn;
    }


    private boolean isNumerical(String s) {
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
    }

    public boolean isMailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", Pattern.CASE_INSENSITIVE);


}
