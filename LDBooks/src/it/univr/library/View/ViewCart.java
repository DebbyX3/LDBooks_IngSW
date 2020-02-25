package it.univr.library.View;

import it.univr.library.Data.Book;
import it.univr.library.Controller.ControllerCart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.Map;

public interface ViewCart
{
    public void buildCart(Map<Book, Integer> cart, VBox cartVBox, ScrollPane cartScrollPane, ControllerCart controllerCart, Label subTotalLabel, Label shippingCostLabel, Label TotalPriceLabel, Label libroCardPointsLabel);
}
