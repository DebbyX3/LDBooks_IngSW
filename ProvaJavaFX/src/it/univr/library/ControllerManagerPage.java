package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerManagerPage {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button orderStatusButton;

    @FXML
    private Button addBooksButton;

    @FXML
    private Button updateChartsButton;

    @FXML
    private Button usersLibroCardsButton;

    private User manager;

    @FXML
    private void initialize()
    {
        //setto il listener
        orderStatusButton.setOnAction(this::handleOrderStatusButton);
        addBooksButton.setOnAction(this::handleAddBooksButton);
        updateChartsButton.setOnAction(this::handleUpdateChartsButton);
        usersLibroCardsButton.setOnAction(this::handleUsersLibroCardsButton);
    }

    public void setManager(User manager)
    {
        this.manager = manager;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox);
    }

    private void handleOrderStatusButton(ActionEvent actionEvent)
    {
        StageManager statusOrderUsers = new StageManager();
        statusOrderUsers.setStageOrderManager((Stage) orderStatusButton.getScene().getWindow(), manager);
    }

    private void handleUsersLibroCardsButton(ActionEvent actionEvent)
    {

    }

    private void handleUpdateChartsButton(ActionEvent actionEvent)
    {

    }

    private void handleAddBooksButton(ActionEvent actionEvent)
    {

    }
}
