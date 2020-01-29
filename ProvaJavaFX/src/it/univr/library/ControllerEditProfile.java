package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

public class ControllerEditProfile {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button editProfileButton;

    @FXML
    private VBox addressVbox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Label mailLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button addAddressButton;

    User user;

    RegisteredClient regUser; //serve?

    private ArrayList<String> postalCodes;

    private ArrayList<String> cities;
    private Map<Book, Integer> cart;

    public ControllerEditProfile()
    {}

    @FXML
    private void initialize()
    {
        Model DBSignUp = new ModelDatabaseSignUp();
        cities = DBSignUp.getCities();
        postalCodes = DBSignUp.getCAPs();

        editProfileButton.setOnAction(this::handleEditProfileButton);
        addAddressButton.setOnAction(this::handleAddAddressButton);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox, cart);
    }

    private void handleAddAddressButton(ActionEvent actionEvent)
    {
        Address newAddress = new Address();
        newAddress.setStreet("");
        newAddress.setHouseNumber("");
        newAddress.setPostalCode("");
        newAddress.setCity("");

        regUser.getAddresses().add(newAddress);
        View viewInformationsUser = new ViewInformationsUser();
        //TODO chiamare un metodo per pulire VBOX ADDRESS pre compilati dalla chiamata di populateUser... nello stageManager prima di creare i nuovi
        viewInformationsUser.buildInformationsEdit(regUser, nameTextField, surnameTextField, phoneTextField, mailLabel, addressVbox);
    }

    private void handleEditProfileButton(ActionEvent actionEvent)
    {
        //TODO first take all the values in the textfields and check if there're changes


    }

    public void populateUserInformations()
    {
        View viewInformationsUser = new ViewInformationsUser();
        regUser = userToRegisteredUser((Client) user);
        /*TODO creare lista di textfield e metterli come lista attributo, passare questa lista come
           oggetto alla view così il controller se la tiene per accedere ai campi successivamente
           e per creare un altro regUser di confronto
         */
        viewInformationsUser.buildInformationsEdit(regUser, nameTextField, surnameTextField, phoneTextField, mailLabel, addressVbox);
    }

    private RegisteredClient userToRegisteredUser(Client testuser)
    {
        Model DBInformations = new ModelDatabaseUserInformations();
        RegisteredClient regUser = DBInformations.getRegisteredUser(testuser);

        regUser.setEmail(testuser.getEmail());
        regUser.setName(testuser.getName());
        regUser.setSurname(testuser.getSurname());
        regUser.setPhoneNumber(testuser.getPhoneNumber());
        regUser.setPassword(testuser.getPassword());

        return regUser;
    }


}
