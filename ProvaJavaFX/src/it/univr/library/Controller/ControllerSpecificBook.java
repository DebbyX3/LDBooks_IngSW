package it.univr.library.Controller;

import it.univr.library.Book;
import it.univr.library.BookGroup;
import it.univr.library.User;
import it.univr.library.View.View;
import it.univr.library.View.ViewBooks;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ControllerSpecificBook
{
    @FXML
    private VBox bookInfoVBox;

    @FXML
    private HBox headerHBox;

    @FXML
    private Label languageLabel;

    @FXML
    private Label titleLabel;

    User user;
    BookGroup bookGroup;
    private Map<Book, Integer> cart;

    private void initialize()
    {
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
    }

    public void setGroupBook(BookGroup bookGroup)
    {
        this.bookGroup = bookGroup;
    }

    public void populateBookInfo()
    {
        View buildSpecificBook = new ViewBooks();
        buildSpecificBook.buildBookForSpecificBook(bookInfoVBox, titleLabel, languageLabel, bookGroup, this, user);
    }


    public void handleAddBookToCart(Book book, Integer quantity, Button cartButton)
    {
        if(quantity == 0 && book.getMaxQuantity() == 0)
            displayAlert("This book is not available!");
        else
        {
            cart.put(book, quantity);
            displayConfirmation();
        }

    }

    private Alert displayConfirmation()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cart Information");
        alert.setHeaderText("Book successfully add to cart!");


        alert.showAndWait();

        return alert;
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Check your input!");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }


}
