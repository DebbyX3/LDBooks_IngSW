package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    User user;

    RegisteredUser regUser; //serve?

    public ControllerEditProfile()
    {}

    @FXML
    private void initialize()
    {
        editProfileButton.setOnAction(this::handleEditProfileButton);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }

    private void handleEditProfileButton(ActionEvent actionEvent)
    {
        //TODO first take all the values in the textfields and check if there're changes

    }

    public void populateUserInformations()
    {
        View viewInformationsUser = new ViewInformationsUser();
        viewInformationsUser.buildInformationsEdit(userToRegisteredUser(user), nameTextField, surnameTextField, phoneTextField, mailLabel, addressVbox);
    }

    private RegisteredUser userToRegisteredUser(User testuser)
    {
        Model DBInformations = new ModelDatabaseUserInformations();
        RegisteredUser regUser = DBInformations.getRegisteredUser(testuser);

        regUser.setEmail(testuser.getEmail());
        regUser.setName(testuser.getName());
        regUser.setSurname(testuser.getSurname());
        regUser.setPhoneNumber(testuser.getPhoneNumber());
        regUser.setPassword(testuser.getPassword());

        return regUser;
    }

}
