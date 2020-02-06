package it.univr.library;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;

public class ControllerPayment {

    @FXML
    private HBox headerHBox;

    @FXML
    private Label priceLabel;

    @FXML
    private Label pointsLabel;

   @FXML
   private RadioButton firstAddressRadioButton;

    @FXML
    private RadioButton secondAddressRadioButton;

    @FXML
    private RadioButton thirdAddressRadioButton;

    @FXML
    private RadioButton creditCardRadioButton;

    @FXML
    private RadioButton paypalRadioButton;

    @FXML
    private RadioButton transferRadioButton;




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
        Model insertNewOrderIntoDb = new ModelDatabaseOrder();
        Order order = createOrder();

        // first put order into db orders
        if(user instanceof RegisteredClient)
            insertNewOrderIntoDb.addNewOrderRegisteredClient(order);
        else
            insertNewOrderIntoDb.addNewOrderNotRegisteredClient(order);

        // then link books with the order in makeUp table with quantity
        for (Book bookToLink: cart.keySet())
        {
            insertNewOrderIntoDb.linkBookToOrder(bookToLink, insertNewOrderIntoDb.getLastOrderCode(), cart.get(bookToLink));
        }

        //TODO update the quantity available for all the books in the cart


    }

    private String fetchPaymentType()
    {
        String paymentType;

        if(creditCardRadioButton != null)
            paymentType = creditCardRadioButton.getText();
        else if(paypalRadioButton != null)
            paymentType = paypalRadioButton.getText();
        else
            paymentType = transferRadioButton.getText();

        return paymentType;
    }

    private Address fetchAddress()
    {
       String address;

       if(firstAddressRadioButton != null)
           address = firstAddressRadioButton.getText();
       else if(secondAddressRadioButton != null)
           address = secondAddressRadioButton.getText();
       else
           address = thirdAddressRadioButton.getText();

       Address shipAddress = null;

        for (Address shipAdd: user.getAddresses()) {
            if(shipAdd.toString().equals(address))
                shipAddress = shipAdd;
        }
        
        return shipAddress;
    }

    public void populatePaymentLabel()
    {
        double totalPrice = 0;
        double pointsLibrocard = 0;

        double shippingCost = getShippingCost();


        for (Book bookInCart: cart.keySet())
        {
            totalPrice += bookInCart.getPrice().doubleValue();
            pointsLibrocard += bookInCart.getPoints().doubleValue();
        }

        totalPrice += shippingCost;

        priceLabel.setText(String.format("%.2f", totalPrice));
        pointsLabel.setText(String.format("%.2f", pointsLibrocard));

        int numberOfShipAddresses = user.getAddresses().size();

        switch (numberOfShipAddresses)
        {
            case 1:
                firstAddressRadioButton.setText(user.getAddresses().get(0).toString());
                break;
            case 2:
                firstAddressRadioButton.setText(user.getAddresses().get(0).toString());
                secondAddressRadioButton.setText(user.getAddresses().get(1).toString());
                break;
            default:
                firstAddressRadioButton.setText(user.getAddresses().get(0).toString());
                secondAddressRadioButton.setText(user.getAddresses().get(1).toString());
                thirdAddressRadioButton.setText(user.getAddresses().get(2).toString());
                break;
        }
    }

    private double getShippingCost()
    {
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

        return shippingCost;
    }

    private Order createOrder()
    {
        Order order = new Order();

        Address shipAddress = fetchAddress();
        order.setAddress(shipAddress);
        ArrayList<Book> booksInCart = new ArrayList<>();
        booksInCart.addAll(cart.keySet());
        order.setBooks(booksInCart);
        order.setDate(Instant.now().getEpochSecond());

        double totalPrice = 0;
        int pointsLibrocard = 0;

        double shippingCost = getShippingCost();


        for (Book bookInCart: cart.keySet())
        {
            totalPrice += bookInCart.getPrice().doubleValue();
            pointsLibrocard += bookInCart.getPoints().doubleValue();
        }

        totalPrice += shippingCost;

        order.setTotalPrice(new BigDecimal(totalPrice));
        order.setBalancePoints(pointsLibrocard);
        order.setPaymentType(fetchPaymentType());
        if(user instanceof RegisteredClient)
            order.setEmailRegisteredUser(user.getEmail());
        else
            order.setEmailNotRegisteredUser(user.getEmail());

        order.setShippingCost(new BigDecimal(getShippingCost()));
        order.setStatus("In progress");

        return order;
    }
}
