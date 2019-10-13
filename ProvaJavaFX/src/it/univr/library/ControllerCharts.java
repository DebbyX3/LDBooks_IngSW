package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControllerCharts {

    @FXML
    private ComboBox genreCombobox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

    @FXML
    private HBox headerHBox;

    private User user;

    //private Stage primaryStage;

    private String message;

    public ControllerCharts()
    {
        // Riempio il genere
        populateGenreFilter();
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @FXML
    private void initialize()
    {
        //Inizializza combobox Genre
        genreCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();
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

    private void populateGenreFilter()
    {
        Model DBGenres = new ModelDatabaseGenres();
        genreComboboxData.addAll(DBGenres.getGenres());
    }
}
