package it.univr.library.Controller;

import it.univr.library.Book;
import it.univr.library.StageManager;
import it.univr.library.User;
import it.univr.library.View.View;
import it.univr.library.View.ViewCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

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
        checkOutButton.setOnAction(this::handleCheckOutButton);
    }

    private void handleCheckOutButton(ActionEvent actionEvent)
    {
        if(cart.isEmpty())
            displayAlert("The cart is empty! Add books to cart :)");
        else
        {
            if(user==null)
            {
                StageManager logInSignUpPage = new StageManager();
                logInSignUpPage.setStageLoginAfterCheckOut((Stage) checkOutButton.getScene().getWindow(), user, cart);
            }
            else
            {
                StageManager paymentPage = new StageManager();
                paymentPage.setStagePaymentPage((Stage) checkOutButton.getScene().getWindow(), user, cart);
            }
        }

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

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Check your input!");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }


}
