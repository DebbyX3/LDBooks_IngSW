package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.*;

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

    //@FXML
    //private HBox book1;

    @FXML
    private VBox catalogVBox;

    @FXML
    private HBox headerHBox;

    @FXML
    private ScrollPane catalogScrollPane;

    private User user;

    private Stage primaryStage;

    public ControllerCatalog()
    {
    }

    @FXML
    private void initialize()
    {
        // Fill the genre ComboBox
        populateGenreFilter();

        // Fill the language ComboBox
        populateLanguageFilter();

        // Set listener for filter button
        filterButton.setOnAction(this::handleFilterButton);

        // Initialize genre ComboBox
        genreCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();

        // Initialize language ComboBox
        languageCombobox.setItems(languageComboboxData);    //setto il combobox del genere con i dati messi in languagecomboboxdata
        languageCombobox.getSelectionModel().selectFirst();

        populateCatalog();

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

    public void changeSceneToSpecificBook(List<String> ISBNList)
    {
        StageManager specificBookScene = new StageManager();
        specificBookScene.setStageSpecificBook((Stage) catalogScrollPane.getScene().getWindow(), user, ISBNList);
    }

    //@FXML
    /*  oppure se metto il tag @FXML posso usare direttamente il nome del metodo nell'fxml che si chiama, senza dover dichiarare
    *   l'oggetto sul quale usarlo (book1) e senza fare il book1.setOnMouseClicked(this::bookClicked). Ricorda però che nell'fxml
    *   si deve specificare come si chiama il metodo per l'azione che si vuole fare (onMouseClicked="#bookClicked" appunto)
    *   */

    private void handleFilterButton(ActionEvent actionEvent)
    {
        Filter filter = new Filter();

        Genre genre = (Genre) genreCombobox.getValue();
        Language language = (Language) languageCombobox.getValue();

        if(!genre.equals(new Genre("All"))) //if the genre is not "all", add the genre to the filter
            filter.setGenre(genre);
        if(!language.equals(new Language("All"))) //if the lang is not "all", add the lang to the filter
            filter.setLanguage(language);

        //Filter filter = new Filter((Genre) genreCombobox.getValue(), (Language) languageCombobox.getValue());

        catalogVBox.getChildren().clear();
        populateCatalog(filter);
    }

    private void populateCatalog(Filter filter)
    {
        Model DBBooks = new ModelDatabaseBooks();
        buildCatalog(DBBooks.getBooks(filter));
    }

    private void populateCatalog()
    {
        Model DBBooks = new ModelDatabaseBooks();
        buildCatalog(DBBooks.getBooks());
    }

    private void populateGenreFilter()
    {
        Model DBGenres = new ModelDatabaseGenres();
        genreComboboxData.add(new Genre("All"));
        genreComboboxData.addAll((DBGenres.getGenres()));
    }

    private void populateLanguageFilter()
    {
        Model DBLanguage = new ModelDatabaseLanguage();
        languageComboboxData.add(new Language("All"));
        languageComboboxData.addAll(DBLanguage.getLanguages());
    }

    private void buildCatalog(ArrayList<Book> books)
    {
        Book originalBook = null;
        BookGroup bookGroup = null;
        List<BookGroup> bookGroups = new ArrayList<>();

        //List<String> ISBNList = new ArrayList<>();

        View viewBooks = new ViewBooks();

        // Bring up the ScrollPane
        catalogScrollPane.setVvalue(catalogScrollPane.getVmin());

        if(!books.isEmpty()) // Books to show
        {
            originalBook = books.get(0);
            bookGroup = new BookGroup();

            for (Book currentBook : books)
            {
                // If the book has NOT the same title, NOT a common author and NOT the same lang
                // then, the book is NOT the same, it's a new book
                if(! (currentBook.getTitle().equals(originalBook.getTitle()) &&
                        !Collections.disjoint(currentBook.getAuthors(), originalBook.getAuthors()) && //true if list1 contains at least one element from list2
                        currentBook.getLanguage().equals(originalBook.getLanguage()))) {

                    bookGroups.add(bookGroup);
                    bookGroup = new BookGroup();

                    // Add listener to the Book HBox calling a method in ControllerCatalog and passing the ISBN List
                    // Note that we call a method from the same controllerCatalog object (passed as an argument)
                    //bookHBox.setOnMouseClicked(mouseEvent -> this.changeSceneToSpecificBook(ISBNList));

                    originalBook = currentBook;
                }

                bookGroup.addBook(currentBook);
            }

            viewBooks.buildBook(catalogVBox, bookGroups, this);

        }
        else // No books to show
        {
            Label messageNoBooksFound = new Label("No books found!");
            catalogVBox.setMargin(messageNoBooksFound, new Insets(50)); //Insets(double top, double right, double bottom, double left)
            catalogVBox.getChildren().add(messageNoBooksFound);
        }
    }
}
