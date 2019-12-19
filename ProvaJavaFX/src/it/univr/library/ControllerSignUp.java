package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerSignUp {

    @FXML
    private ImageView cartImageView;

    @FXML
    private Button signUpButton;

    @FXML
    private ComboBox citiesAndCapsComboBox;
    private ObservableList<String> citiesAndCapsComboboxData = FXCollections.observableArrayList();

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
    private PasswordField pswPasswordField;

    private User regUser;

    private ArrayList<String> postalCodes;

    private ArrayList<String> cities;

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", Pattern.CASE_INSENSITIVE);

    @FXML
    private void initialize()
    {
        //populate comboBox of cities
        populateCitiesAndCAPsCombobox();

        citiesAndCapsComboBox.setItems(citiesAndCapsComboboxData);
        citiesAndCapsComboBox.getSelectionModel().selectFirst();

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

    private void populateCitiesAndCAPsCombobox()
    {
        ArrayList<String> citiesAndCAPs = new ArrayList<>();

        Model DBSignUp = new ModelDatabaseSignUp();

        cities = DBSignUp.getCities();
        postalCodes = DBSignUp.getCAPs();

        Iterator<String> citiesIterator = cities.iterator();
        Iterator<String> CAPsIterator = postalCodes.iterator();

        while(citiesIterator.hasNext() && CAPsIterator.hasNext())
        {
            citiesAndCAPs.add(citiesIterator.next() + " - " + CAPsIterator.next());
        }
        
        citiesAndCapsComboboxData.addAll(citiesAndCAPs);
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
                    getPostalCodeFromCombobox(), getCityFromCombobox(), phoneNumberTextField.getText(), mailTextField.getText(), pswPasswordField.getText());

            //normalize all the TextFields
            normalizeUser(testUser);

            //check if it could be a valid user, first check if the format of fields is fine
            if(areValidFields(testUser, error))
            {
                // check if mail is unique
                if(!DBcheckUser.doesMailAlreadyExist(testUser))
                {
                    //call method to add user to db
                    DBcheckUser.addUser(testUser);

                    //call method to first check if address already exists then if doesn't exist, puts it into db.
                    DBcheckUser.addAddress(testUser);

                    //create associate librcard
                    DBcheckUser.createLibroCard(testUser);

                    //if everything's ok change scene
                    StageManager loginStage = new StageManager();
                    loginStage.setStageUserPage((Stage) signUpButton.getScene().getWindow(), testUser);
                }
                else
                    displayAlert("Mail already exists");
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

    private String getCityFromCombobox()
    {
        return cities.get(citiesAndCapsComboBox.getSelectionModel().getSelectedIndex());
    }

    private String getPostalCodeFromCombobox()
    {
        return postalCodes.get(citiesAndCapsComboBox.getSelectionModel().getSelectedIndex());
    }

    private boolean areValidFields(RegisteredUser testUser, StringBuilder error)
    {
        if(!isAlpha(testUser.getName()))
            error.append("- Name must be only alphabetical\n");
        if(!isAlpha(testUser.getSurname()))
            error.append("- Surname must be only alphabetical\n");
        if(!isNumerical(testUser.getPhoneNumber()))
            error.append("- Phone Number must only contain numbers\n");
        if(!isMailValid(testUser.getEmail()))
            error.append("- Mail is not in the right format (abc@mail.com)\n");

        return error.toString().isEmpty();
    }

    private void normalizeUser(RegisteredUser testUser)
    {
        testUser.setName(testUser.getName().trim());
        testUser.setSurname(testUser.getSurname().trim());

        //check if name, surname, street have apostrophe
        testUser.setName(testUser.getName().replaceAll("'","''"));
        testUser.setSurname(testUser.getSurname().replaceAll("'","''"));

        for (Address address: testUser.getAddresses()) {
            address.setStreet(address.getStreet().trim());
            address.setCity(address.getCity().trim());
            address.setHouseNumber(address.getHouseNumber().trim());
            address.setPostalCode(address.getPostalCode().trim());

            address.setStreet(address.getStreet().replaceAll("'", "''"));
        }

        testUser.setPhoneNumber(testUser.getPhoneNumber().trim());
        testUser.setEmail(testUser.getEmail().trim());
    }

    private boolean isAnyFieldNullOrEmpty()
    {
        return     nameTextField == null || nameTextField.getText().isEmpty()
                || surnameTextField == null || surnameTextField.getText().isEmpty()
                || streetTextField == null || streetTextField.getText().isEmpty()
                || houseNumberTextField == null || houseNumberTextField.getText().isEmpty()
                || citiesAndCapsComboBox == null
                || phoneNumberTextField == null || phoneNumberTextField.getText().isEmpty()
                || mailTextField == null || mailTextField.getText().isEmpty()
                || pswPasswordField == null || pswPasswordField.getText().isEmpty();
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
        return s.matches("[A-zÀ-ú ']+");

    }

    private boolean isNumerical(String s) {
        return s.matches("[0-9]+");

    }

    private boolean isMailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
