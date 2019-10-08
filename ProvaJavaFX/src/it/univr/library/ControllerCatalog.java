package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Collections;

/*------------------------ IMPORTANTE! ----------------------
In a few words: The constructor is called first, then any @FXML annotated fields are populated,
then initialize() is called. So the constructor does NOT have access to @FXML fields referring
to components defined in the .fxml file, while initialize() does have access to them.
*/

public class ControllerCatalog {

    @FXML
    private Button catalogButton;

    @FXML
    private Button chartsButton;

    @FXML
    private ComboBox genreCombobox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

    @FXML
    private ComboBox linguaCombobox;
    private ObservableList<Genre> languageComboboxData = FXCollections.observableArrayList();

    @FXML
    private HBox book1;

    @FXML
    private VBox catalogVBox;

    private Stage primaryStage;

    public ControllerCatalog()
    {
        // Riempio il genere

        /*
        Model DBBooks = new ModelDatabaseBooks();
        View viewBooks = new ViewBooks();

        viewBooks.buildCatalog(DBBooks.getBooks(), catalogVBox);
        */

        populateGenreFilter();

        // Riempio la lingua
        languageComboboxData.add(new Genre("Tutti"));
        languageComboboxData.add(new Genre("Inglese"));
        languageComboboxData.add(new Genre("Italiano"));
        languageComboboxData.add(new Genre("Spagnolo"));
    }

    @FXML
    private void initialize()
    {
        populateCatalog();

        //Setta listener bottoni
        catalogButton.setOnAction(this::handleCatalogButton); //setto il listener
        chartsButton.setOnAction(this::handleChartsButton);

        //Inizializza combobox Genre
        genreCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();

        //Inizializza combobox Language
        linguaCombobox.setItems(languageComboboxData);    //setto il combobox del genere con i dati messi in languagecomboboxdata
        linguaCombobox.getSelectionModel().selectFirst();

        //book1.setOnMouseClicked(this::bookClicked); // vedi annotazione sulla funzione
    }

    //@FXML
    /*  oppure se metto il tag @FXML posso usare direttamente il nome del metodo nell'fxml che si chiama, senza dover dichiarare
    *   l'oggetto sul quale usarlo (book1) e senza fare il book1.setOnMouseClicked(this::bookClicked). Ricorda però che nell'fxml
    *   si deve specificare come si chiama il metodo per l'azione che si vuole fare (onMouseClicked="#bookClicked" appunto)
    *   */
    private void bookClicked(MouseEvent mouseEvent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Libro cliccato");

        alert.showAndWait();
    }

    /*
    * private void bookClicked(MouseEvent mouseEvent)
    {
        new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Libro cliccato");

                alert.showAndWait();
            }
        };
    }*/

    private void handleCatalogButton(ActionEvent event)
    {
        //quando clicco il bottone, riportami alla scena del catalogo (questa)
    }

    private void handleChartsButton(ActionEvent event)
    {
        StageManager chartsStage = new StageManager();
        chartsStage.setStageCharts((Stage) chartsButton.getScene().getWindow(), "hello");
    }

    private void populateCatalog()
    {
        Model DBBooks = new ModelDatabaseBooks();
        View viewBooks = new ViewBooks();

        viewBooks.buildCatalog(DBBooks.getBooks(), catalogVBox);
    }

    private void populateGenreFilter()
    {
        Model DBGenres = new ModelDatabaseGenres();
        genreComboboxData.addAll(DBGenres.getGenres());
    }
}
