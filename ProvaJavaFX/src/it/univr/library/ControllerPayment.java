package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
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

    private void handlePaymentButton(ActionEvent actionEvent)
    {
        // fetch all the information and create the order, update db with the new order and update quantity available for the books in cart




    }

    public void populatePaymentLabel()
    {
        double totalPrice = 0;
        double pointsLibrocard = 0;

        double shippingCost;
        boolean onlyAudioBook = true;

        for (Book books: cart.keySet())
        {
            if (!books.getFormat().getName().equals("Digital Edition") && !books.getFormat().getName().equals("Audiobook"))
            {
                onlyAudioBook = false;
                break;
            }
        }

        if(onlyAudioBook)
            shippingCost = 0;
        else
            shippingCost = 5.99;

        for (Book bookInCart: cart.keySet())
        {
            totalPrice += bookInCart.getPrice().doubleValue();
            pointsLibrocard += bookInCart.getPoints().doubleValue();
        }

        totalPrice += shippingCost;

        priceLabel.setText(String.format("%.2f €", totalPrice));
        pointsLabel.setText(String.format("%.2f Points", pointsLibrocard));

        int numberOfShipAddresses = user.getAddresses().size();

        switch (numberOfShipAddresses)
        {
            case 1:
                defaultAddressLabel.setText(user.getAddresses().get(0).toString());
                break;
            case 2:
                defaultAddressLabel.setText(user.getAddresses().get(0).toString());
                secondAddressLabel.setText(user.getAddresses().get(1).toString());
                break;
            default:
                defaultAddressLabel.setText(user.getAddresses().get(0).toString());
                secondAddressLabel.setText(user.getAddresses().get(1).toString());
                thirdAddressLabel.setText(user.getAddresses().get(2).toString());
                break;
        }
    }
}
