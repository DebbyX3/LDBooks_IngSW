package it.univr.library.View;

import it.univr.library.BookGroup;
import it.univr.library.Controller.ControllerCatalog;
import it.univr.library.Controller.ControllerSpecificBook;
import it.univr.library.User;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public interface ViewBooks
{
    public void buildBookForCatalog(VBox catalogVBox, List<BookGroup> bookGroups, ControllerCatalog controllerCatalog);
    public void buildBookForSpecificBook(VBox bookInfoVBox, Label titleLabel, Label languageLabel, BookGroup bookGroup, ControllerSpecificBook controllerSpecificBook, User user);
}
