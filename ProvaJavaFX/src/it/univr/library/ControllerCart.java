package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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
    private ArrayList<Book> books = fetchBooks();


    @FXML
    private void initialize() {
        populateCart(books);
    }

    private void populateCart(ArrayList<Book> books) {
        View viewCartUser = new ViewCart();
        cartVBox.getChildren().clear();
        viewCartUser.buildCart(books, cartVBox, cartScrollPane, this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHeader() {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }

    private ArrayList<Book> fetchBooks() {
        ArrayList<Book> all;
        ArrayList<Book> ret = new ArrayList<>();
        Model fetch = new ModelDatabaseBooks();
        all = fetch.getAllBooks();
        for (Book b : all) {
            ret.add(b);
            break;
        }
        return all;
    }

    public void handleRemoveBookFromCart(Book book)
    {
        books.remove(book);
        populateCart(books);
    }
}
