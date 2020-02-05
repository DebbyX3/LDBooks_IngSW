package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Iterator;
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
    private TextField phoneNumberTextField;

    @FXML
    private Label mailLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button addAddressButton;

    private ControllerUserInfo controllerUserInfo = new ControllerUserInfo();

    User user;

    RegisteredClient regUser;

    private Map<Book, Integer> cart;

    public ControllerEditProfile() {
    }

    @FXML
    private void initialize() {
        addAddressButton.setOnAction(this::handleAddAddressButton);
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

    public void populateUserInformation() {
        View viewInformationUser = new ViewInformationUser();
        regUser = userToRegisteredUser((Client) user);

        viewInformationUser.buildEditProfileInformation(regUser, nameTextField, surnameTextField, phoneNumberTextField, passwordTextField, mailLabel, addressVbox);

        //bisognerebbe fare l'handler del bottone nella view ma è un bottone fatto da fxml e non ha molto senso
        editProfileButton.setOnAction(event -> handleEditProfileButton(viewInformationUser));
    }

    private void handleAddAddressButton(ActionEvent actionEvent) {
        /*Address newAddress = new Address();
        newAddress.setStreet("");
        newAddress.setHouseNumber("");
        newAddress.setPostalCode("");
        newAddress.setCity("");

        regUser.getAddresses().add(newAddress);

        View viewInformationUser = new ViewInformationUser();*/
    }

    private void handleEditProfileButton(View viewInformationUser)
    {
        List<Address> addresses = new ArrayList<>();

        for (AddressFields af : viewInformationUser.getAddressFieldsList())
            addresses.add(af.toAddress());

        checkInputs(addresses);
    }

    private void checkInputs(List<Address> newAddresses)
    {
        StringBuilder error = new StringBuilder();

        //check if textfields are not empty or null, if the list is valid
        if(!isNameSurnamePhoneNumberPasswordNullOrEmpty())
        {
            if(areAddressesLegit(newAddresses))
            {
                if (areAddressesValid(newAddresses))
                {
                    RegisteredClient newRegUser = controllerUserInfo.fetchUser(nameTextField.getText(), surnameTextField.getText(),
                                            phoneNumberTextField.getText(), mailLabel.getText(),
                                            passwordTextField.getText(), newAddresses);


                    if(areValidFields(newRegUser, error))
                    {
                        manageAddresses(regUser.getAddresses(), newAddresses, newRegUser);

                    }
                }
                //|| passwordTextField == null || passwordTextField.getText().isEmpty();
                else
                    error.append("- The fields of the addresses should be all filled or all empty\n");
            }
            else
                error.append("- At least a full address should be filled\n");
        }
        else {
            error.append("- Name, surname, phone number and password must be filled\n");
            displayAlert(error.toString());//da sistemare gli alert
        }
    }

    private boolean isNameSurnamePhoneNumberPasswordNullOrEmpty()
    {
        return controllerUserInfo.isTextFieldNullOrEmpty(nameTextField, surnameTextField, phoneNumberTextField, passwordTextField);
    }

    /**
     * Check if all the addresses are empty.
     * @param newAddresses is the list of addresses to check.
     * @return true if the addresses are legit, that means that there are empty addresses but not all of them.
     *         false if all the addresses are empty.
     */
    private boolean areAddressesLegit(List<Address> newAddresses)
    {
        boolean allEmpty = true;

        // if all the addresses are empty, then they are not valid
        for (Address address : newAddresses)
            allEmpty = allEmpty && address.isEmpty();

        // If they are all empty, then the addresses are not legit
        return !allEmpty;
    }

    /**
     * Check if the list of addresses has empty or null fields.
     * NOTE: we allow entirely empty addresses (as it means that we want to delete it), but we don't allow
     *       only one field being empty.
     * @param newAddresses is the list of addresses to check.
     * @return  true if all the addresses are completely empty or completely filled.
     *          false if at least an address is only partially empty.
     */
    private boolean areAddressesValid(List<Address> newAddresses)
    {
        /*
            NOTE: we allow entirely empty addresses (as it means that we want to delete it), but we don't allow
            only one field being empty.
        * */
        for (Address address : newAddresses)
        {
            // if it is partially empty, then it is not ok and we return false
            if(address.isPartiallyEmpty()) {
                return false;
            }
        }

        // if they are all valid addresess, then return true.
        // that means that they are completely empty or completely false.
        return true;
    }

    private RegisteredClient userToRegisteredUser(Client user) {
        Model DBInformation = new ModelDatabaseUserInformation();
        RegisteredClient regUser =
                new RegisteredClient(user.getName(), user.getSurname(), user.getEmail(),
                        user.getPassword(), user.getPhoneNumber(), DBInformation.getAddressesRegisteredUser(user));

        return regUser;
    }

    // Forse da spostare intermente in ControllerUserInfo e unirlo con lo stesso metodo nel controllerSignUp?
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

        return valid;
    }

    private void manageAddresses(List<Address> oldAddresses, List<Address> newAddresses, RegisteredClient newRegUser)
    {
        Model DBcheckUser = new ModelDatabaseUserInfo();

        Iterator<Address> oldAddrIterator = oldAddresses.iterator();
        Iterator<Address> newAddrIterator = newAddresses.iterator();

        Address currentOldAddr;
        Address currentNewAddr;

        // Iterate on the new addresses because they can increase in number
        while (newAddrIterator.hasNext())
        {
            currentOldAddr = oldAddrIterator.next();
            currentNewAddr = newAddrIterator.next();

            if(currentNewAddr.isEmpty())    // se è vuoto, togli il collegamento di quello vecchio
            {
                DBcheckUser.unlinkAddressFromUser(newRegUser, currentOldAddr);
            }
            else if(!currentOldAddr.equals(currentNewAddr)) // se non sono uguali, allora aggiornalo
            {
                DBcheckUser.addAddress(newRegUser, currentNewAddr); //linka/aggiungi quello nuovo
                DBcheckUser.unlinkAddressFromUser(newRegUser, currentOldAddr); //togli il link a quello vecchio
            }
        }
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
