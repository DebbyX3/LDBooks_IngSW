package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    public void populateUserInformation()
    {
        View viewInformationUser = new ViewInformationUser();
        regUser = clientToRegisteredClient((Client) user);

        viewInformationUser.buildEditProfileInformation(regUser, nameTextField, surnameTextField, phoneNumberTextField, passwordTextField, mailLabel, addressVbox);

        //bisognerebbe fare l'handler del bottone nella view ma è un bottone fatto da fxml e non ha molto senso
        editProfileButton.setOnAction(event -> handleEditProfileButton(viewInformationUser));
        addAddressButton.setOnAction(event -> handleAddAddressButton(viewInformationUser));
    }

    private void handleAddAddressButton(View viewInformationUser)
    {
        viewInformationUser.addEmptyAddressEditProfile(addressVbox);
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
        ModelUserInfo DBCheckUser = new ModelDatabaseUserInfo();
        ModelUserAddress DBFetchAddress = new ModelDatabaseUserAddress();

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
                        // call method to deal with the addresses
                        manageAddresses(regUser.getAddresses(), newAddresses, newRegUser);

                        // call method to update user info
                        DBCheckUser.updateUser(newRegUser);

                        // fetch the refreshed addresses and set them to the newRegUser
                        newRegUser.setAddresses(DBFetchAddress.getAddressesRegisteredUser(newRegUser));

                        // if everything's ok change scene
                        StageManager viewProfileStage = new StageManager();
                        viewProfileStage.setStageViewProfile((Stage) editProfileButton.getScene().getWindow(), newRegUser, cart);
                    }
                }
                else
                    error.append("- The fields of the addresses should be all filled or all empty\n");
            }
            else
                error.append("- At least a full address should be filled\n");
        }
        else {
            error.append("- Name, surname, phone number and password must be filled\n");

        }
        if (!error.toString().isEmpty())
            displayAlert(error.toString());
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

    private RegisteredClient clientToRegisteredClient(Client user) {
        ModelUserAddress DBInformation = new ModelDatabaseUserAddress();
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
        ModelUserInfo DBcheckUser = new ModelDatabaseUserInfo();

        Iterator<Address> oldAddrIterator = oldAddresses.iterator();
        Iterator<Address> newAddrIterator = newAddresses.iterator();

        Address currentOldAddr;
        Address currentNewAddr;

        // Iterate on the old addresses because we are sure that the old and new addresses go in pairs
        while (oldAddrIterator.hasNext())
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

        // At this point, the new addresses outnumber the old ones, so simply add the new ones
        while (newAddrIterator.hasNext())
        {
            currentNewAddr = newAddrIterator.next();

            // If it is empty, then do nothing because it's supposed to be a new address, and there's nothing to delete
            if(!currentNewAddr.isEmpty())
                DBcheckUser.addAddress(newRegUser, currentNewAddr); // Just add the new address
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
