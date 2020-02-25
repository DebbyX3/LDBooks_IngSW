package it.univr.library.Controller;

import it.univr.library.*;
import it.univr.library.Model.*;
import it.univr.library.View.ViewBooks;
import it.univr.library.View.ViewFXBooks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

    @FXML
    private VBox catalogVBox;

    @FXML
    private HBox headerHBox;

    @FXML
    private ScrollPane catalogScrollPane;


    private User user;
    private Map<Book,Integer> cart;

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
        genreCombobox.setItems(genreComboboxData);    // setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();

        // Initialize language ComboBox
        languageCombobox.setItems(languageComboboxData);    // setto il combobox del genere con i dati messi in languagecomboboxdata
        languageCombobox.getSelectionModel().selectFirst();

        populateCatalog();
    }



    public void setUser(User user)
    {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
    }

    public void changeSceneToSpecificBook(BookGroup bookList)
    {
        StageManager specificBookScene = new StageManager();
        specificBookScene.setStageSpecificBook((Stage) catalogScrollPane.getScene().getWindow(), user, bookList, cart);
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

        if(!genre.equals(new Genre("All"))) // if the genre is not "all", add the genre to the filter
            filter.setGenre(genre);
        if(!language.equals(new Language("All"))) // if the lang is not "all", add the lang to the filter
            filter.setLanguage(language);

        //Filter filter = new Filter((Genre) genreCombobox.getValue(), (Language) languageCombobox.getValue());

        catalogVBox.getChildren().clear();
        populateCatalog(filter);
    }



    private void populateCatalog(Filter filter)
    {
        ModelBooks DBBooks = new ModelDatabaseBooks();
        buildCatalog(DBBooks.getAllBooks(filter));
    }

    private void populateCatalog()
    {
        ModelBooks DBBooks = new ModelDatabaseBooks();
        buildCatalog(DBBooks.getAllBooks());
    }

    private void populateGenreFilter()
    {
        ModelGenres DBGenres = new ModelDatabaseGenres();
        genreComboboxData.add(new Genre("All"));
        genreComboboxData.addAll((DBGenres.getGenres()));
    }

    private void populateLanguageFilter()
    {
        ModelLanguage DBLanguage = new ModelDatabaseLanguage();
        languageComboboxData.add(new Language("All"));
        languageComboboxData.addAll(DBLanguage.getLanguages());
    }

    private void buildCatalog(ArrayList<Book> books)
    {
        Book originalBook = null;
        BookGroup bookGroup = null;
        List<BookGroup> bookGroups = new ArrayList<>();

        ViewBooks viewBooks = new ViewFXBooks();

        // Bring up the ScrollPane
        catalogScrollPane.setVvalue(catalogScrollPane.getVmin());

        if(!books.isEmpty()) // Books to show
        {
            originalBook = books.get(0);
            bookGroup = new BookGroup();

            for (Book currentBook : books)
            {
                // If the book has NOT the same title, NOT a common author, NOT the same genre and NOT the same lang
                // then, the book is NOT the same, it's a new book.
                // If so, add the current group of books to the list of group books,
                // then create a new Group of Books (bookGroup)
                if(!    (currentBook.getTitle().equals(originalBook.getTitle()) &&
                        !Collections.disjoint(currentBook.getAuthors(), originalBook.getAuthors()) && //true if list1 contains at least one element from list2
                        currentBook.getLanguage().equals(originalBook.getLanguage()) &&
                        currentBook.getGenre().equals(originalBook.getGenre()))
                ) {

                    bookGroups.add(bookGroup);
                    bookGroup = new BookGroup();

                    originalBook = currentBook;
                }

                // add the current book to the list of books contained in a book group
                bookGroup.addBook(currentBook);
            }

            bookGroups.add(bookGroup);
            viewBooks.buildBookForCatalog(catalogVBox, bookGroups, this);
        }
        else // No books to show
        {
            Label messageNoBooksFound = new Label("No books found!");
            catalogVBox.setMargin(messageNoBooksFound, new Insets(50)); //Insets(double top, double right, double bottom, double left)
            catalogVBox.getChildren().add(messageNoBooksFound);
        }
    }


}
