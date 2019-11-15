package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerSignUp {

    @FXML
    private ImageView cartImageView;

    @FXML
    private Button signUpButton;

    @FXML
    private ComboBox citiesComboBox;
    private ObservableList<String> citiesComboboxData = FXCollections.observableArrayList();

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
    private TextField postalCodeTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswPasswordField;

    private User regUser;

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", Pattern.CASE_INSENSITIVE);

    public ControllerSignUp()
    {
        //populate comboBox of cities
        populateCitiesField();
    }

    @FXML
    private void initialize()
    {
        citiesComboBox.setItems(citiesComboboxData);

        signUpButton.setOnAction(this::handleSignUpButton);
    }

    public void setUser(User user)
    {
        this.regUser = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(regUser, headerHBox);
    }

    private void populateCitiesField()
    {
        Model DBSignUp = new ModelDatabaseSignUp();
        citiesComboboxData.addAll(DBSignUp.getCities());
    }

    private void handleSignUpButton(ActionEvent actionEvent)
    {
        Model DBcheckUser = new ModelDatabaseSignUp();
        StringBuilder error = new StringBuilder();

        //check if some textField is null
        if(!isAnyFieldNullOrEmpty())
        {
            //fetch user from the text field and create the object
            RegisteredUser testUser = fetchUser(nameTextField.getText(), surnameTextField.getText(), streetTextField.getText(), houseNumberTextField.getText(),
                    postalCodeTextField.getText(), (String)citiesComboBox.getValue(), phoneNumberTextField.getText(), mailTextField.getText(), pswPasswordField.getText());

            //normalize all the TextFields
            normalizeUser(testUser);

            //check if it could be a valid user, first check if the format of fields is fine
            if(areValidFields(testUser, error))
            {
                // check if mail is unique
                if(DBcheckUser.checkMail(testUser))
                {

                }
            }
            else
            {
                displayAlert(error.toString());
            }

        }
        else
        {
            error.append("All text field must be filled!\n");
            displayAlert(error.toString());
        }
    }

    private boolean areValidFields(RegisteredUser testUser, StringBuilder error)
    {
        if(!isAlpha(testUser.getName()))
            error.append("- Name must be only alphabetical\n");
        if(!isAlpha(testUser.getSurname()))
            error.append("- Surname must be only alphabetical\n");
        if(!isNumerical(testUser.getPhoneNumber()))
            error.append("- Phone Number must only contains numbers\n");
        if(!isMailValid(testUser.getEmail()))
            error.append("- Mail is not in the right format (abc@mail.com)\n");

        return error.toString().isEmpty();
    }

    private void normalizeUser(RegisteredUser testUser)
    {
        testUser.setName(testUser.getName().trim());
        testUser.setSurname(testUser.getSurname().trim());

        for (Address address: testUser.getAddresses()) {
            address.setStreet(address.getStreet().trim());
            address.setCity(address.getCity().trim());
            address.setHouseNumber(address.getHouseNumber().trim());
            address.setPostalCode(address.getPostalCode().trim());
        }

        testUser.setPhoneNumber(testUser.getPhoneNumber().trim());
        testUser.setEmail(testUser.getEmail().trim());
    }

    private boolean isAnyFieldNullOrEmpty()
    {
        boolean r;

        r = nameTextField == null || nameTextField.getText().isEmpty();
        r = surnameTextField == null || surnameTextField.getText().isEmpty();
        r = streetTextField == null || streetTextField.getText().isEmpty();
        r = houseNumberTextField == null || houseNumberTextField.getText().isEmpty();
        r = postalCodeTextField == null || postalCodeTextField.getText().isEmpty();
        r = phoneNumberTextField == null || phoneNumberTextField.getText().isEmpty();
        r = mailTextField == null || mailTextField.getText().isEmpty();
        r = pswPasswordField == null || pswPasswordField.getText().isEmpty();

        return r;
    }

    private RegisteredUser fetchUser(String name, String surname, String street, String houseNumber, String postalCode, String city, String phoneNumber, String mail, String psw)
    {
        Address address = new Address();
        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        address.setPostalCode(postalCode);
        address.setCity(city);
        RegisteredUser test = new RegisteredUser(address);
        test.setName(name);
        test.setSurname(surname);
        test.setPhoneNumber(phoneNumber);
        test.setEmail(mail);
        test.setPassword(psw);

        return test;
    }

    private boolean isAlpha(String s) {
        return s.matches("[a-zA-Z]+");

    }

    private boolean isNumerical(String s) {
        return s.matches("[0-9]+");

    }

    private boolean isMailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
