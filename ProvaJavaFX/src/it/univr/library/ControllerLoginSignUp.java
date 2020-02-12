package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerLoginSignUp {

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswField;

    @FXML
    private HBox headerHBox;

    private User user;

    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        // handle to press enter for login
        pswField.setOnKeyReleased(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                loginButton.fire();
        });

        loginButton.setOnAction(this::handleLoginButton);
        signUpButton.setOnAction(this::handleSignUpButton);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
    }

    private void handleLoginButton(ActionEvent actionEvent)
    {

        Model DBLogin = new ModelDatabaseRegisteredUser();
        User user = fetchUser();
        RegisteredClient realUser = DBLogin.getRegisteredClient(user);
        Manager manager = DBLogin.getManager(user);

        if(realUser == null && manager == null || realUser != null && manager != null)
            displayAlert("Invalid mail or password!");
        else
        {
            if(realUser != null) //user page
            {
                StageManager loginStage = new StageManager();
                loginStage.setStageUserPage((Stage) loginButton.getScene().getWindow(), clientToRegisteredClient(realUser), cart);
            }
            else //manager page
            {
                StageManager loginStage = new StageManager();
                loginStage.setStageManagerPage((Stage) loginButton.getScene().getWindow(), manager, cart);
            }

        }
    }

    private void handleSignUpButton(ActionEvent actionEvent)
    {
        StageManager signUpStage = new StageManager();
        signUpStage.setStageSignUp((Stage) signUpButton.getScene().getWindow(), user, cart);
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }

    private User fetchUser()
    {
        Client u = new Client();
        u.setEmail(mailTextField.getText().toUpperCase());
        u.setPassword(pswField.getText());

        return u;
    }

    private RegisteredClient clientToRegisteredClient(Client user) {
        Model DBInformation = new ModelDatabaseUserAddress();
        RegisteredClient regUser =
                new RegisteredClient(user.getName(), user.getSurname(), user.getEmail(),
                        user.getPassword(), user.getPhoneNumber(), DBInformation.getAddressesRegisteredUser(user));

        return regUser;
    }
}
