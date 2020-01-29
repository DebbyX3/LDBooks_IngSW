package it.univr.library;

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

public class ControllerLibroCardsManager
{
    @FXML
    private VBox LibroCardVBox;

    @FXML
    private ComboBox<String> userMailCombobox;
    private ObservableList<String> mailUsersComboboxData = FXCollections.observableArrayList();

    @FXML
    private ScrollPane LibroCardScrollPane;
    @FXML
    private Button filterButton;

    @FXML
    private HBox headerHBox;

    private User manager;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        populateMailFilter();
        populateUsersLibroCard();
        userMailCombobox.setItems(mailUsersComboboxData);
        userMailCombobox.getSelectionModel().selectFirst();

        filterButton.setOnAction(this::handleFilterButton);
    }

    public void setUser(User manager)
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
        String mailFilter = userMailCombobox.getValue();

        if(!mailFilter.equals("All"))
        {
            Model DBMailLibroCard = new ModelDatabaseLibrocard();
            View viewMailLibroCard = new ViewLibrocard();

            LibroCardVBox.getChildren().clear();
            viewMailLibroCard.buildLibroCard(DBMailLibroCard.getSpecificLibroCard(mailFilter), LibroCardVBox, LibroCardScrollPane);
        }
        else
            populateUsersLibroCard();
    }

    public void populateUsersLibroCard()
    {
        Model DBAllLibroCards = new ModelDatabaseLibrocard();
        View viewAllLibroCards = new ViewLibrocard();
        LibroCardVBox.getChildren().clear();
        viewAllLibroCards.buildLibroCard(DBAllLibroCards.getAllLibroCards(), LibroCardVBox, LibroCardScrollPane);
    }

    private void populateMailFilter()
    {
        Model DBmailLibroCardUsers = new ModelDatabaseLibrocard();
        mailUsersComboboxData.add("All");
        mailUsersComboboxData.addAll((DBmailLibroCardUsers.getMailsLibroCards()));
    }


}
