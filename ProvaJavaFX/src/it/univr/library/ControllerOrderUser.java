package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControllerOrderUser {

    @FXML
    private VBox orderVBox;

    @FXML
    private HBox headerHBox;

    private User user;

    @FXML
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

    private void handleLogOutButton(ActionEvent actionEvent)
    {

    }

    private void handleCartImageView(MouseEvent mouseEvent)
    {

    }

    private void handleNameSurnameHyperlink(ActionEvent actionEvent)
    {

    }

    public void populateOrderUser()
    {
        /*
        *TODO 1) chiama model per query
        * 2)la query va a pescare tutti gli ordini di user e li mette dentro un'arraylist<Order> -->crea classe e metodo resultSetToArrayList
        * 3)ora ho un array list di orders, devo mettere sulla view ad ogni stampa del singolo ordine un handle
        */

    }
}
