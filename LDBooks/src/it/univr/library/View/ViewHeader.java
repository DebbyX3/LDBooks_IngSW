package it.univr.library.View;

import it.univr.library.Data.Book;
import it.univr.library.Data.User;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public interface ViewHeader
{
    public void createLogo(HBox headerHBox);
    public void createCatalogButton(HBox headerHBox, User user, Map<Book,Integer> cart);
    public void createChartsButton(HBox headerHBox, User user, Map<Book,Integer> cart);
    public HBox createRightHeaderHBox(HBox headerHBox);
    public void createLoginSignupButton(HBox rightHeaderHbox, User user, Map<Book,Integer> cart);
    public void createOrderStatusButton(HBox rightHeaderHbox, User user, Map<Book,Integer> cart);
    public VBox createUserHyperlink(User user, HBox rightHeaderHBox, Map<Book,Integer> cart);
    public void createLogOutButton(VBox userInfoVBox, HBox rightHeaderHBox, User user, Map<Book,Integer> cart);
    public void createCartImageView(HBox rightHeaderHBox, User user, Map<Book,Integer> cart);

}
