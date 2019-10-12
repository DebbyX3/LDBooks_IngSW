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

/*------------------------ IMPORTANTE! ----------------------
In a few words: The constructor is called first, then any @FXML annotated fields are populated,
then initialize() is called. So the constructor does NOT have access to @FXML fields referring
to components defined in the .fxml file, while initialize() does have access to them.
*/

public class ControllerCatalog {

    @FXML
    private Button filterButton;

    @FXML
    private ComboBox genreCombobox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

    @FXML
    private ComboBox languageCombobox;
    private ObservableList<Language> languageComboboxData = FXCollections.observableArrayList();

    @FXML
    private HBox book1;

    @FXML
    private VBox catalogVBox;

    @FXML
    private HBox headerHBox;

    private User user;

    private Stage primaryStage;

    public ControllerCatalog()
    {
        // Riempio il genere
        populateGenreFilter();

        //riempio la lingua
        populateLanguageFilter();
    }

    @FXML
    private void initialize()
    {

        populateCatalog();

        //Setta listener bottoni
        filterButton.setOnAction(this::handleFilterButton);

        //Inizializza combobox Genre
        genreCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();

        //Inizializza combobox Language
        languageCombobox.setItems(languageComboboxData);    //setto il combobox del genere con i dati messi in languagecomboboxdata
        languageCombobox.getSelectionModel().selectFirst();

        //book1.setOnMouseClicked(this::bookClicked); // vedi annotazione sulla funzione
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

    private void handleFilterButton(ActionEvent actionEvent) {
        Model DBBooks = new ModelDatabaseBooks();
        View viewBooks = new ViewBooks();
        Filter filter = new Filter((Genre) genreCombobox.getValue(), (Language) languageCombobox.getValue());

        catalogVBox.getChildren().clear();
        viewBooks.buildCatalog(DBBooks.getBooks(filter), catalogVBox);
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

    private void populateLanguageFilter()
    {
        Model DBLanguage = new ModelDatabaseLanguage();
        languageComboboxData.addAll(DBLanguage.getLanguages());
    }
}
