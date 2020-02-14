package it.univr.library.Controller;

import it.univr.library.Book;
import it.univr.library.Model.ModelDatabaseOrder;
import it.univr.library.Model.ModelOrder;
import it.univr.library.Order;
import it.univr.library.User;
import it.univr.library.View.ViewFXOrders;
import it.univr.library.View.ViewOrders;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

public class ControllerOrderUser {

    @FXML
    private VBox orderVBox;

    @FXML
    private HBox headerHBox;

    @FXML
    private ScrollPane orderUserScrollPane;

    private User user;
    private Map<Book, Integer> cart;

    @FXML
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

    private void handleCartImageView(MouseEvent mouseEvent)
    {

    }

    public void populateOrderUser()
    {
        ModelOrder DBorders = new ModelDatabaseOrder();
        ViewOrders viewOrders = new ViewFXOrders();

        viewOrders.buildOrders(DBorders.getOrders(user), orderVBox, orderUserScrollPane,user);
    }

    public void populateOrderUser(ArrayList<Order> order)
    {
        ViewOrders viewOrders = new ViewFXOrders();
        viewOrders.buildOrders(order, orderVBox, orderUserScrollPane, user);
    }


}
