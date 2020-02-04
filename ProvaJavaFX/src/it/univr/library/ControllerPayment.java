package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Map;

public class ControllerPayment {

    @FXML
    private HBox headerHBox;

    @FXML
    private Label priceLabel;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label defaultAddressLabel;

    @FXML
    private Label secondAddressLabel;

    @FXML
    private Label thirdAddressLabel;

    @FXML
    private Button paymentButton;


    private User user;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        //TODO set the right price and points, maybe pass as parameter from the last scene?
        priceLabel.setText("43 €");
        pointsLabel.setText("100 points");
        paymentButton.setOnAction(this::handlePaymentButton);
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

    private void handlePaymentButton(ActionEvent actionEvent) {
    }


}
