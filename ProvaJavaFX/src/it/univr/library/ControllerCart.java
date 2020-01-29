package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ControllerCart {

    @FXML
    private VBox cartVBox;

    @FXML
    private HBox headerHBox;

    @FXML
    private Label subTotalLabel;

    @FXML
    private Label shippingCostLabel;

    @FXML
    private Label TotalPriceLabel;

    @FXML
    private Label libroCardPointsLabel;

    @FXML
    private ScrollPane cartScrollPane;

    @FXML
    private Button checkOutButton;

    private User user;
    private Map<Book, Integer> cart;


    @FXML
    private void initialize()
    {
    }

    public void populateCart(Map<Book,Integer> cart) {
        View viewCartUser = new ViewCart();
        cartVBox.getChildren().clear();
        viewCartUser.buildCart(cart, cartVBox, cartScrollPane, this,subTotalLabel, shippingCostLabel, TotalPriceLabel, libroCardPointsLabel);
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



    public void handleRemoveBookFromCart(Book book, Button removeBookButton)
    {
        cart.keySet().remove(book);
        StageManager newCartView = new StageManager();
        newCartView.setStageUserCart((Stage) removeBookButton.getScene().getWindow(), user, cart);
    }


}
