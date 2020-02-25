package it.univr.library.View;

import it.univr.library.Data.Order;
import it.univr.library.Data.User;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public interface ViewOrders
{
    public void buildOrders(ArrayList<Order> orders, VBox orderVBox, ScrollPane orderScrollPane, User user);
}
