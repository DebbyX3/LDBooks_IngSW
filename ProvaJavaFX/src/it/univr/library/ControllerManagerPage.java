package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

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
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        //setto il listener
        orderStatusButton.setOnAction(this::handleOrderStatusButton);
        addBooksButton.setOnAction(this::handleAddEditBooksButton);
        updateChartsButton.setOnAction(this::handleUpdateChartsButton);
        usersLibroCardsButton.setOnAction(this::handleUsersLibroCardsButton);
    }

    public void setManager(User manager)
    {
        this.manager = manager;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox,cart);
    }

    private void handleOrderStatusButton(ActionEvent actionEvent)
    {
        StageManager statusOrderUsers = new StageManager();
        statusOrderUsers.setStageOrderManager((Stage) orderStatusButton.getScene().getWindow(), manager, cart);
    }

    private void handleUsersLibroCardsButton(ActionEvent actionEvent)
    {
        StageManager libroCardManager = new StageManager();
        libroCardManager.setStageLibroCardManager((Stage) usersLibroCardsButton.getScene().getWindow(), manager, cart);
    }

    private void handleUpdateChartsButton(ActionEvent actionEvent)
    {
        StageManager updateCharts = new StageManager();
        updateCharts.setStageUpdateCharts((Stage) updateChartsButton.getScene().getWindow(), manager, cart);
    }

    private void handleAddEditBooksButton(ActionEvent actionEvent)
    {
        StageManager addEditBooks = new StageManager();
        addEditBooks.setStageAddEditBooks((Stage) addBooksButton.getScene().getWindow(), manager, cart);
    }


}
