package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerSignUp {

    @FXML
    private ImageView cartImageView;

    @FXML
    private Button signUpButton;

    @FXML
    private HBox headerHBox;

    private User user;

    @FXML
    private void initialize()
    {

    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }
}
