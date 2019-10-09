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
    private Button catalogButton;

    @FXML
    private Button chartsButton;

    @FXML
    private ComboBox genreCombobox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

    private Stage primaryStage;

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
        //Setta listener bottoni
        catalogButton.setOnAction(this::handleCatalogButton);
        chartsButton.setOnAction(this::handleChartsButton);

        //Inizializza combobox Genre
        genreCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();
    }

    private void handleCatalogButton(ActionEvent event)
    {
        StageManager catalogStage = new StageManager();
        catalogStage.setStageCatalog((Stage) chartsButton.getScene().getWindow());
    }

    private void handleChartsButton(ActionEvent event)
    {
        StageManager chartsStage = new StageManager();
        chartsStage.setStageCharts((Stage) chartsButton.getScene().getWindow(), "hello");
    }

    private void populateGenreFilter()
    {
        Model DBGenres = new ModelDatabaseGenres();
        genreComboboxData.addAll(DBGenres.getGenres());
    }
}
