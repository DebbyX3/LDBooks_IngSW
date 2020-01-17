package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox);
    }

    private void populatePublishingHouse()
    {
        Model DBPhouses = new ModelDatabasePublishingHouse();
        publishingHouses.addAll(DBPhouses.getPublishingHouses());
    }

    private void handleAddNewPublishingHouse(ActionEvent actionEvent)
    {
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
                Model DBinsertNewPublishingHouse = new ModelDatabasePublishingHouse();
                DBinsertNewPublishingHouse.addNewPublishingHouse(newPublishingHouse);

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewPublishingHouseButton.getScene().getWindow(), manager);
            }
            else
            {
                displayAlert("Publishing House already exists!");
            }
        }
        else
        {
            displayAlert("Publishing house name must be filled!");
        }

    }

    private void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}

