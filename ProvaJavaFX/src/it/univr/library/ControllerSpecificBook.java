package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    private void initialize()
    {
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
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



}
