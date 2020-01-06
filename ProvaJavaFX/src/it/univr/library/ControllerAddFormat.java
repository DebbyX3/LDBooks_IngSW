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

public class ControllerAddFormat {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button addNewFormatButton;

    @FXML
    private TextField newFormatTextField;

    @FXML
    private ListView<String> formatsListView;
    private ObservableList<String> formats = FXCollections.observableArrayList();

    private User manager;

    @FXML
    private void initialize()
    {
        populateFormats();
        formatsListView.setItems(formats);

        addNewFormatButton.setOnAction(this::handleAddNewFormat);
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

    private void populateFormats()
    {
        Model DBformats = new ModelDatabaseBooks();
        formats.addAll(DBformats.getFormats());
    }

    private void handleAddNewFormat(ActionEvent actionEvent)
    {

        String newFormat = newFormatTextField.getText();
        boolean exist = false;

        for (String format: formats)
        {
            if (newFormat.toUpperCase().equals(format.toUpperCase()))
                exist = true;
        }

        if(!exist)
        {
            //if the authors doesn't already exists so insert into db
            Model DBinsertNewFormat = new ModelDatabaseBooks();
            DBinsertNewFormat.addNewFormat(newFormat);

            //change scene
            StageManager addEditBooks = new StageManager();
            addEditBooks.setStageAddEditBooks((Stage) addNewFormatButton.getScene().getWindow(), manager);
        }
        else
        {
            displayAlert("Format already exists!");
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
