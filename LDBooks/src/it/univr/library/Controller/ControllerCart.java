package it.univr.library.Controller;

import it.univr.library.Data.Book;
import it.univr.library.Utils.StageManager;
import it.univr.library.Data.User;
import it.univr.library.View.ViewCart;
import it.univr.library.View.ViewFXCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        ControllerAlert alerts = new ControllerAlert();

        if(cart.isEmpty())
            alerts.displayAlert("The cart is empty! Add books to cart :)");
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
        ViewCart viewCartUser = new ViewFXCart();
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
