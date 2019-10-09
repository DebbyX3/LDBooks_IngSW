package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerLoginSignUp {

    @FXML
    private Button catalogButton;

    @FXML
    private Button chartsButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswField;


    @FXML
    private void initialize()
    {
        //Setta listener bottoni
        catalogButton.setOnAction(this::handleCatalogButton); //setto il listener
        chartsButton.setOnAction(this::handleChartsButton);
        loginButton.setOnAction(this::handleLoginButton);
    }

    private void handleLoginButton(ActionEvent actionEvent)
    {

        Model DBLogin = new ModelDatabaseRegisteredUser();
        User user = fetchUser();

        if(DBLogin.getUser(user) == null)
            displayAllert("Invalid mail or password!");
        else
        {
            System.out.println("Schermata login");
        }


    }

    private void displayAllert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }

    private void handleCatalogButton(ActionEvent event)
    {
        StageManager catalogStage = new StageManager();
        catalogStage.setStageCatalog((Stage) catalogButton.getScene().getWindow());
    }

    private void handleChartsButton(ActionEvent event)
    {
        StageManager chartsStage = new StageManager();
        chartsStage.setStageCharts((Stage) chartsButton.getScene().getWindow(), "hello");
    }

    private User fetchUser()
    {
        User u = new User();
        u.setEmail(mailTextField.getText());
        u.setPassword(pswField.getText());

        return u;
    }

}
