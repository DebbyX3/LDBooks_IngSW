package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        buildSpecificBook.buildBookForSpecificBook(bookInfoVBox, titleLabel, languageLabel, bookGroup, this);
    }


    public void handleAddBookToCart(Book book, Integer quantity, Button cartButton) {
        cart.put(book, quantity);
        StageManager catalogView = new StageManager();
        catalogView.setStageCatalog((Stage) cartButton.getScene().getWindow(), user, cart);
    }



}
