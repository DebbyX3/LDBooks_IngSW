package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerUserPage
{
    @FXML
    private Button catalogButton;

    @FXML
    private Button chartsButton;

    @FXML
    private Button myOrdersButton;

    @FXML
    private Button viewProfileButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button myLibroCardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Hyperlink nameSurnameHyperlink;

    @FXML
    private ImageView cartImageView;





    @FXML
    private void initialize()
    {

        checkUser();
        catalogButton.setOnAction(this::handleCatalogButton); //setto il listener
        chartsButton.setOnAction(this::handleChartsButton);
        myOrdersButton.setOnAction(this::handleMyOrdersButton);
        viewProfileButton.setOnAction(this::handleViewProfileButton);
        editProfileButton.setOnAction(this::handleEditProfileButton);
        myLibroCardButton.setOnAction(this::handleMyLibroCardButton);
        logoutButton.setOnAction(this::handleLogOutButton);
        nameSurnameHyperlink.setOnAction(this::handleNameSurnameHyperlink);
        cartImageView.setOnMouseClicked(this::handleCartImageView);
    }

    private void checkUser() {
    }

    private void handleLogOutButton(ActionEvent actionEvent)
    {

    }

    private void handleCartImageView(MouseEvent mouseEvent)
    {

    }

    private void handleNameSurnameHyperlink(ActionEvent actionEvent)
    {

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

}
