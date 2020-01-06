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
    private ListView<String> publishingHousesListView;
    private ObservableList<String> publishingHouses = FXCollections.observableArrayList();

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
        Model DBPhouses = new ModelDatabaseBooks();
        publishingHouses.addAll(DBPhouses.getPublishingHouses());
    }

    private void handleAddNewPublishingHouse(ActionEvent actionEvent)
    {
        String newPublishingHouse = newPublishingHouseTextFiled.getText();


        boolean exist = false;
        for (String pHouse: publishingHouses)
        {
            if (newPublishingHouse.toUpperCase().equals(pHouse.toUpperCase()))
                exist = true;
        }

        if(!exist)
        {
            //if the authors doesn't already exists so insert into db
            Model DBinsertNewPublishingHouse = new ModelDatabaseBooks();
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

    private void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}

