package it.univr.library.Controller;

import it.univr.library.Data.Book;
import it.univr.library.Model.ModelDatabasePublishingHouse;
import it.univr.library.Model.ModelPublishingHouse;
import it.univr.library.Data.PublishingHouse;
import it.univr.library.Utils.StageManager;
import it.univr.library.Data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerAddPublishingHouse {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button addNewPublishingHouseButton;

    @FXML
    private TextField newPublishingHouseTextFiled;

    @FXML
    private ListView<PublishingHouse> publishingHousesListView;
    private ObservableList<PublishingHouse> publishingHouses = FXCollections.observableArrayList();

    private User manager;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        populatePublishingHouse();
        publishingHousesListView.setItems(publishingHouses);

        addNewPublishingHouseButton.setOnAction(this::handleAddNewPublishingHouse);
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
        controllerHeader.createHeader(manager, headerHBox, cart);
    }

    private void populatePublishingHouse()
    {
        ModelPublishingHouse DBPhouses = new ModelDatabasePublishingHouse();
        publishingHouses.addAll(DBPhouses.getPublishingHouses());
    }

    private void handleAddNewPublishingHouse(ActionEvent actionEvent)
    {
        ControllerAlert alerts = new ControllerAlert();
        PublishingHouse newPublishingHouse = new PublishingHouse(newPublishingHouseTextFiled.getText().trim());

        if(!newPublishingHouse.getName().isEmpty())
        {
            boolean exist = false;
            for (PublishingHouse pHouse: publishingHouses)
            {
                if (newPublishingHouse.equals(pHouse)) {
                    exist = true;
                    break;
                }
            }

            if(!exist)
            {
                //if the authors doesn't already exists so insert into db
                ModelPublishingHouse DBinsertNewPublishingHouse = new ModelDatabasePublishingHouse();
                DBinsertNewPublishingHouse.addNewPublishingHouse(newPublishingHouse);

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewPublishingHouseButton.getScene().getWindow(), manager, cart);
            }
            else
            {
                alerts.displayAlert("Publishing House already exists!");
            }
        }
        else
        {
            alerts.displayAlert("Publishing house name must be filled!");
        }

    }
}

