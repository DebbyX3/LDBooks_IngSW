package it.univr.library.Controller;

import it.univr.library.Data.Book;
import it.univr.library.Data.Format;
import it.univr.library.Model.ModelDatabaseFormat;
import it.univr.library.Model.ModelFormat;
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
    private Map<Book, Integer> cart;

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

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox,cart);
    }

    private void populateFormats()
    {
        ModelFormat DBformats = new ModelDatabaseFormat();
        formats.addAll(DBformats.getFormats());
    }

    private void handleAddNewFormat(ActionEvent actionEvent)
    {
        ControllerAlert alerts = new ControllerAlert();
        Format format =  new Format(newFormatTextField.getText().trim());

        if(!format.getName().isEmpty())
        {
            boolean exist = false;

            for (Format f: formats)
            {
                if (f.equals(format)) {
                    exist = true;
                    break;
                }
                break;
            }

            if(!exist)
            {
                //if the authors doesn't already exists so insert into db
                ModelFormat DBinsertNewFormat = new ModelDatabaseFormat();
                DBinsertNewFormat.addNewFormat(format);

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewFormatButton.getScene().getWindow(), manager, cart);
            }
            else
            {
                alerts.displayAlert("Format already exists!");
            }
        }
        else
        {
            alerts.displayAlert("Format name must be filled!");
        }

    }
}
