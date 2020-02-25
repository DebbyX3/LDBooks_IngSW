package it.univr.library.Controller;

import it.univr.library.Data.Book;
import it.univr.library.Utils.BookGroup;
import it.univr.library.Data.User;
import it.univr.library.View.ViewBooks;
import it.univr.library.View.ViewFXBooks;
import javafx.fxml.FXML;
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
        ViewBooks buildSpecificBook = new ViewFXBooks();
        buildSpecificBook.buildBookForSpecificBook(bookInfoVBox, titleLabel, languageLabel, bookGroup, this, user);
    }

    public void handleAddBookToCart(Book book, Integer quantity, Button cartButton)
    {
        ControllerAlert alerts = new ControllerAlert();

        if(quantity == 0 && book.getMaxQuantity() == 0)
            alerts.displayAlert("This book is not available!");
        else
        {
            cart.put(book, quantity);
            alerts.displayInformation("Book successfully add to cart!");
        }

    }
}
