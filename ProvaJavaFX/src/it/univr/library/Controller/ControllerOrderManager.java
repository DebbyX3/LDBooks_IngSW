package it.univr.library.Controller;

import it.univr.library.Book;
import it.univr.library.Model.ModelDatabaseOrder;
import it.univr.library.Model.ModelOrder;
import it.univr.library.User;
import it.univr.library.View.ViewFXOrders;
import it.univr.library.View.ViewOrders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ControllerOrderManager {

    @FXML
    private VBox OrderVbox;

    @FXML
    private ComboBox<String> mailUsersCombobox;
    private ObservableList<String> mailUsersComboboxData = FXCollections.observableArrayList();

    @FXML
    private Button filterButton;

    @FXML
    private HBox headerHBox;

    @FXML
    private ScrollPane orderManagerScrollPane;

    private User manager;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        populateMailUserFilter();
        //Inizializza combobox Genre
        mailUsersCombobox.setItems(mailUsersComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        mailUsersCombobox.getSelectionModel().selectFirst();
        //setto il listener
        filterButton.setOnAction(this::handleFilterButton);

        populateOrderUsers();
    }

    public void setManager(User manager)
    {
        this.manager = manager;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox,cart);
    }

    private void handleFilterButton(ActionEvent actionEvent)
    {
        String mailFilter = mailUsersCombobox.getValue();

        if (mailFilter.contains(" "))
                mailFilter = mailUsersCombobox.getValue().substring(0, mailUsersCombobox.getValue().indexOf(" "));

        if(!mailFilter.equals("All"))
        {
            ModelOrder DBMailOrders = new ModelDatabaseOrder();
            ViewOrders viewMailOrder = new ViewFXOrders();

            OrderVbox.getChildren().clear();
            viewMailOrder.buildOrders(DBMailOrders.getSpecificMailOrders(mailFilter), OrderVbox, orderManagerScrollPane);
        }
        else
            populateOrderUsers();
    }

    public void populateMailUserFilter()
    {
        ModelOrder DBmailUsers = new ModelDatabaseOrder();
        mailUsersComboboxData.add("All");
        mailUsersComboboxData.addAll((DBmailUsers.getMailsOrders()));
    }

    public void populateOrderUsers()
    {
        ModelOrder DBAllOrders = new ModelDatabaseOrder();
        ViewOrders viewAllOrder = new ViewFXOrders();
        OrderVbox.getChildren().clear();
        viewAllOrder.buildOrders(DBAllOrders.getAllOrders(), OrderVbox, orderManagerScrollPane);
    }


}
