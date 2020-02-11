package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerSignUp {

    @FXML
    private ImageView cartImageView;

    @FXML
    private Button signUpButton;

    @FXML
    private ComboBox citiesAndPostalCodesComboBox;
    private ObservableList<String> citiesAndPostalCodesComboboxData = FXCollections.observableArrayList();

    @FXML
    private HBox headerHBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField passwordTextField;

    private User regUser;

    //contains methods and attributes that help managing addresses
    private ControllerUserInfo controllerUserInfo = new ControllerUserInfo();

    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        //populate comboBox of cities
        citiesAndPostalCodesComboboxData = controllerUserInfo.populateCitiesAndPostalCodesCombobox();

        citiesAndPostalCodesComboBox.setItems(citiesAndPostalCodesComboboxData);
        citiesAndPostalCodesComboBox.getSelectionModel().selectFirst();

        signUpButton.setOnAction(this::handleSignUpButton);
    }

    public void setUser(User user)
    {
        this.regUser = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(regUser, headerHBox,cart);
    }

    private void handleSignUpButton(ActionEvent actionEvent)
    {
        Model DBcheckUser = new ModelDatabaseUserInfo();
        StringBuilder error = new StringBuilder();

        //check if textFields are null
        if(!isAnyFieldNullOrEmpty())
        {
            //fetch user from the text field and create the object
            RegisteredClient newRegUser = controllerUserInfo.fetchUser(nameTextField.getText(), surnameTextField.getText(),
                                                      streetTextField.getText(), houseNumberTextField.getText(),
                                                      controllerUserInfo.getPostalCodeFromCombobox(citiesAndPostalCodesComboBox),
                                                      controllerUserInfo.getCityFromCombobox(citiesAndPostalCodesComboBox),
                                                      phoneNumberTextField.getText(),
                                                      mailTextField.getText(), passwordTextField.getText());

            //check if it could be a valid user, first check if the format of fields is fine
            if(areValidFields(newRegUser, error))
            {
                // check if mail is unique
                if(!DBcheckUser.doesMailAlreadyExist(newRegUser) && !DBcheckUser.doesMailUnregisteredAlreadyExist(newRegUser))
                {
                    //call method to add user to db
                    DBcheckUser.addUser(newRegUser);

                    //call method to first check if address already exists then if doesn't exist, puts it into db.
                    DBcheckUser.addAddress(newRegUser, newRegUser.getAddresses().get(0)); //take the first and only address

                    //create associate librcard
                    DBcheckUser.createLibroCard(newRegUser);

                    //if everything's ok change scene
                    StageManager loginStage = new StageManager();
                    loginStage.setStageUserPage((Stage) signUpButton.getScene().getWindow(), newRegUser, cart);
                }
                else
                    displayAlert("This e-mail is already linked to another user! Try another one :)");
            }
            else
                displayAlert(error.toString());
        }
        else
        {
            error.append("All text field must be filled!\n");
            displayAlert(error.toString());
        }
    }

    private boolean isAnyFieldNullOrEmpty()
    {
        boolean res = false;

        res = res || controllerUserInfo.isTextFieldNullOrEmpty(nameTextField, surnameTextField, streetTextField,
                            houseNumberTextField, phoneNumberTextField, mailTextField, passwordTextField);

        res = res || controllerUserInfo.isComboBoxNull(citiesAndPostalCodesComboBox);

        return res;
    }

    private boolean areValidFields(RegisteredClient testUser, StringBuilder error)
    {
        boolean valid = true;

        if(!controllerUserInfo.isAlpha(testUser.getName())) {
            error.append("- Name must be only alphabetical\n");
            valid = false;
        }
        if(!controllerUserInfo.isAlpha(testUser.getSurname())) {
            error.append("- Surname must be only alphabetical\n");
            valid = false;
        }
        if(!controllerUserInfo.isNumerical(testUser.getPhoneNumber())) {
            error.append("- Phone Number must only contain numbers\n");
            valid = false;
        }
        if(!controllerUserInfo.isMailValid(testUser.getEmail())) {
            error.append("- Mail is not in the right format (abc@mail.com)\n");
            valid = false;
        }

        return valid;
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
