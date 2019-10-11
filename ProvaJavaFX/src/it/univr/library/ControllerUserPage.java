package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerUserPage
{
    @FXML
    private Button myOrdersButton;

    @FXML
    private Button viewProfileButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button myLibroCardButton;

    @FXML
    private HBox headerHBox;

    private User user;


    @FXML
    private void initialize()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);

        //setto il listener
        myOrdersButton.setOnAction(this::handleMyOrdersButton);
        viewProfileButton.setOnAction(this::handleViewProfileButton);
        editProfileButton.setOnAction(this::handleEditProfileButton);
        myLibroCardButton.setOnAction(this::handleMyLibroCardButton);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    private void handleMyLibroCardButton(ActionEvent actionEvent)
    {

    }

    private void handleViewProfileButton(ActionEvent actionEvent)
    {

    }

    private void handleEditProfileButton(ActionEvent actionEvent)
    {
        StageManager orderUserStage = new StageManager();
        orderUserStage.setStageOrderUser((Stage) myOrdersButton.getScene().getWindow(), "hello");
    }

    private void handleMyOrdersButton(ActionEvent actionEvent)
    {
        StageManager orderUserStage = new StageManager();
        orderUserStage.setStageOrderUser((Stage) myOrdersButton.getScene().getWindow(), "hello");
    }
}
