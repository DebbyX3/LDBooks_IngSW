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
    private ListView<Format> formatsListView;
    private ObservableList<Format> formats = FXCollections.observableArrayList();

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
        Model DBformats = new ModelDatabaseFormat();
        formats.addAll(DBformats.getFormats());
    }

    private void handleAddNewFormat(ActionEvent actionEvent)
    {

        Format format =  new Format(newFormatTextField.getText());
        boolean exist = false;

        for (Format f: formats)
        {
            if (f.equals(format))
                exist = true;
        }

        if(!exist)
        {
            //if the authors doesn't already exists so insert into db
            Model DBinsertNewFormat = new ModelDatabaseFormat();
            DBinsertNewFormat.addNewFormat(format);

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
