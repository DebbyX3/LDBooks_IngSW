package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

    private User manager;

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

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox);
    }

    private void handleFilterButton(ActionEvent actionEvent)
    {

        String mailFilter = mailUsersCombobox.getValue();
        if (mailFilter.contains(" "))
                mailFilter = mailUsersCombobox.getValue().substring(0,mailUsersCombobox.getValue().indexOf(" "));

        if(!mailFilter.equals("All"))
        {
            Model DBMailOrders = new ModelDatabaseOrder();
            View viewMailOrder = new ViewOrders();

            OrderVbox.getChildren().clear();
            viewMailOrder.buildOrders(DBMailOrders.getSpecificMailOrders(mailFilter), OrderVbox);
        }
        else
            populateOrderUsers();

    }


    public void populateMailUserFilter()
    {
        Model DBmailUsers = new ModelDatabaseOrder();
        mailUsersComboboxData.add("All");
        mailUsersComboboxData.addAll((DBmailUsers.getMailsOrders()));
    }

    public void populateOrderUsers()
    {
        Model DBAllOrders = new ModelDatabaseOrder();
        View viewAllOrder = new ViewOrders();
        OrderVbox.getChildren().clear();
        viewAllOrder.buildOrders(DBAllOrders.getAllOrders(), OrderVbox);
    }
}
