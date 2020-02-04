package it.univr.library;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
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

    private Map<Book, Integer> cart;

    public ControllerEditProfile()
    {}

    @FXML
    private void initialize()
    {
        //editProfileButton.setOnAction(this::handleEditProfileButton);
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

        View viewInformationUser = new ViewInformationUser();
        //TODO chiamare un metodo per pulire VBOX ADDRESS pre compilati dalla chiamata di populateUser...
        // nello stageManager prima di creare i nuovi
        //viewInformationUser.buildEditProfileInformation(regUser, nameTextField, surnameTextField, phoneTextField, mailLabel, addressVbox);
    }

    private void handleEditProfileButton(ActionEvent actionEvent)
    {
        //TODO first take all the values in the textfields and check if there're changes


    }

    public void populateUserInformation()
    {
        View viewInformationUser = new ViewInformationUser();
        regUser = userToRegisteredUser((Client) user);

        viewInformationUser.buildEditProfileInformation(regUser, nameTextField, surnameTextField, phoneTextField, mailLabel, addressVbox);

        //fa schifo, bisognerebbe fare l'handler del bottone nella view ma è un bottone fatto da fxml e non ha molto senso
        editProfileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<Address> addresses = new ArrayList<>();

                for (AddressFields af: viewInformationUser.getAddressFieldsList())
                    addresses.add(af.toAddress());

                checkAddresses(addresses);
            }
        });
    }

    private void checkAddresses(List<Address> newAddresses)
    {}

    private RegisteredClient userToRegisteredUser(Client user)
    {
        Model DBInformation = new ModelDatabaseUserInformation();
        RegisteredClient regUser =
                new RegisteredClient(user.getName(), user.getSurname(), user.getEmail(),
                        user.getPassword(), user.getPhoneNumber(), DBInformation.getAddressesRegisteredUser(user));

        return regUser;
    }


}
