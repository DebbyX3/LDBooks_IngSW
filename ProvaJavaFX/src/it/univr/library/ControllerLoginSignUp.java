package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

    @FXML
    private void initialize()
    {
        //Setta listener bottoni

        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);

        loginButton.setOnAction(this::handleLoginButton);
        signUpButton.setOnAction(this::handleSignUpButton);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    private void handleLoginButton(ActionEvent actionEvent)
    {

        Model DBLogin = new ModelDatabaseRegisteredUser();
        User user = fetchUser();
        User realUser = DBLogin.getUser(user);

        if(realUser == null)
            displayAllert("Invalid mail or password!");
        else
        {
           //carico la nuova schermata
            StageManager loginStage = new StageManager();
            loginStage.setStageUserPage((Stage) loginButton.getScene().getWindow(), realUser);
        }
    }

    private void handleSignUpButton(ActionEvent actionEvent)
    {
        StageManager signUpStage = new StageManager();
        signUpStage.setStageSignUp((Stage) signUpButton.getScene().getWindow(), "hello");
    }

    private void displayAllert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }

    private User fetchUser()
    {
        User u = new User();
        u.setEmail(mailTextField.getText().toUpperCase());
        u.setPassword(pswField.getText());

        return u;
    }


}
