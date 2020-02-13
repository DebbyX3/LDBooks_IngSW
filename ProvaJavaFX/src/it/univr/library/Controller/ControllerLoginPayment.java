package it.univr.library.Controller;

import it.univr.library.*;
import it.univr.library.Model.ModelDatabaseManagers;
import it.univr.library.Model.ModelDatabaseRegisteredUser;
import it.univr.library.Model.ModelManager;
import it.univr.library.Model.ModelRegisteredUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerLoginPayment {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswField;

    @FXML
    private Hyperlink unregisterHyperlink;

    private User user;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        loginButton.setOnAction(this::handleLoginButton);
        signUpButton.setOnAction(this::handleSignUpButton);
        unregisterHyperlink.setOnAction(this::handleContinueAsUnregistered);
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

    private void handleLoginButton(ActionEvent actionEvent)
    {
        ModelRegisteredUser DBLoginRegisteredUser = new ModelDatabaseRegisteredUser();
        ModelManager DBLoginManager = new ModelDatabaseManagers();

        User user = fetchUser();
        RegisteredClient realUser = DBLoginRegisteredUser.getRegisteredClient(user);
        Manager manager = DBLoginManager.getManager(user);

        if(realUser == null && manager == null || realUser != null && manager != null)
            displayAlert("Invalid mail or password!");
        else
        {
            if(realUser != null) //user page
            {
                StageManager paymentPage = new StageManager();
                paymentPage.setStagePaymentPage((Stage) loginButton.getScene().getWindow(), realUser, cart);
            }
        }
    }



    private void handleSignUpButton(ActionEvent actionEvent)
    {
        StageManager signUpPage = new StageManager();
        signUpPage.setStageSignUp((Stage) loginButton.getScene().getWindow(), user, cart);
    }

    private void handleContinueAsUnregistered(ActionEvent actionEvent)
    {
        StageManager unregisteredUser = new StageManager();
        unregisteredUser.setStageUnregisteredUser((Stage) loginButton.getScene().getWindow(), user, cart);
    }



    private User fetchUser()
    {
        Client u = new Client();
        u.setEmail(mailTextField.getText().toUpperCase());
        u.setPassword(pswField.getText());

        return u;
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }


}