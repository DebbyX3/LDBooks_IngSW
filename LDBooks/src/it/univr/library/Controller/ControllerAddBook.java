package it.univr.library.Controller;

import it.univr.library.Data.*;
import it.univr.library.Model.*;
import it.univr.library.Utils.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.*;

public class ControllerAddBook {

    @FXML
    private HBox headerHBox;

    @FXML
    private TextField imagePathTextField;

    @FXML
    private TextField ISBNTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private ComboBox<Author> authorComboBox;
    private ObservableList<Author> authors = FXCollections.observableArrayList();

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField publicationYearTextField;

    @FXML
    private ComboBox<PublishingHouse> publishingHouseComboBox;
    private ObservableList<PublishingHouse> publishingHouses = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Genre> genreComboBox;
    private ObservableList<Genre> genres = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Format> formatComboBox;
    private ObservableList<Format> formats = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Language> languageComboBox;
    private ObservableList<Language> languages = FXCollections.observableArrayList();

    @FXML
    private TextField pagesTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField librocardPointsTextField;

    @FXML
    private Spinner availableQuantitySpinner;


    @FXML
    private Button selectAuthorButton;

    @FXML
    private ComboBox<Integer> numberAuthorsComboBox;
    private ObservableList<Integer> numberAuthors = FXCollections.observableArrayList();

    @FXML
    private Button addNewBookButton;

    private User manager;
    private int numberOfAuthors = 0;
    private List<Author> authorsToLinkToBook = new ArrayList<>();
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        ControllerAlert alerts = new ControllerAlert();

        populateNumbAuthorsComboBox();
        numberAuthorsComboBox.setItems(numberAuthors);
        // take the value from comboBox to iterate ad take the number of authors selected in the combobox
        numberAuthorsComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> numberOfAuthors = newValue );
        numberAuthorsComboBox.getSelectionModel().selectedItemProperty().addListener((v) -> authorComboBox.setDisable(false));

        popolateAuthors();
        authorComboBox.setItems(authors);

        populatePublishingHouses();
        publishingHouseComboBox.setItems(publishingHouses);

        populateGenres();
        genreComboBox.setItems(genres);

        populateFormats();
        formatComboBox.setItems(formats);


        populateLanguages();
        languageComboBox.setItems(languages);

        availableQuantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 1));
        availableQuantitySpinner.setEditable(true);
        availableQuantitySpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
        try
        {
            Integer.parseInt(availableQuantitySpinner.getEditor().textProperty().get());
        }
        catch (IllegalArgumentException e)
        {
            availableQuantitySpinner.getEditor().textProperty().set("0");
            alerts.displayAlert("Available quantity must be numerical!\n");
        }
        });

        selectAuthorButton.setOnAction(this::handleSelectAuthorButton);
        addNewBookButton.setOnAction(this::handleAddNewBookButton);

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

    private void handleSelectAuthorButton(ActionEvent actionEvent)
    {
        ControllerAlert alerts = new ControllerAlert();

        numberAuthorsComboBox.setDisable(true);
        Author author = authorComboBox.getValue();
        if(author == null)
        {
            alerts.displayAlert("choose an Author!");
        }
        else
        {
            boolean exists = false;

            for (Author authorToCheck: authorsToLinkToBook) {
                if(author.equals(authorToCheck))
                    exists = true;
            }

            if(!exists)
            {
                authorsToLinkToBook.add(author);

                if(numberOfAuthors == 1)
                {
                    selectAuthorButton.setDisable(true);
                    authorComboBox.setDisable(true);
                }

                numberOfAuthors--;
                displayAlertAddAuthor(numberOfAuthors);
            }
            else
            {
                alerts.displayAlert("Author already selected, choose another one!");
            }
        }
    }

    private void handleAddNewBookButton(ActionEvent actionEvent)
    {
        ControllerBooks checkFields = new ControllerBooks();
        ControllerAlert alerts = new ControllerAlert();

        if(!checkFields.isAnyFieldEmptyOrNotValid(ISBNTextField.getText(), titleTextField.getText(), descriptionTextArea.getText(),
                publicationYearTextField.getText(), pagesTextField.getText(),
                librocardPointsTextField.getText(), availableQuantitySpinner.getEditor().textProperty().get(),
                authorsToLinkToBook, priceTextField.getText()))
        {
            if(!checkFields.doesISBNExists(ISBNTextField.getText()))
            {
                ModelBooks DBBook = new ModelDatabaseBooks();

                Book book = checkFields.fetchBookInformation(ISBNTextField.getText(), titleTextField.getText(), authorsToLinkToBook,
                        descriptionTextArea.getText(), formatComboBox.getValue(), genreComboBox.getValue(),
                        languageComboBox.getValue(), publishingHouseComboBox.getValue(),
                        pagesTextField.getText(), librocardPointsTextField.getText(),
                        availableQuantitySpinner.getEditor().textProperty().get(),priceTextField.getText(),
                        publicationYearTextField.getText(), imagePathTextField.getText());

                ModelAuthor DBAuthor = new ModelDatabaseAuthor();
                DBBook.addNewBook(book);

                for (Author authorToLink: book.getAuthors())
                    DBAuthor.linkBookToAuthors(authorToLink.getId(), book.getISBN());

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewBookButton.getScene().getWindow(), manager, cart);
            }
            else
            {
                alerts.displayAlert("A book with this ISBN already exists!");
            }
        }
        else
        {
            availableQuantitySpinner.getEditor().textProperty().set("0");
        }
    }

    private void populateNumbAuthorsComboBox()
    {
        for(int i=1; i<=10;i++)
            numberAuthors.add(i);
    }

    private void popolateAuthors()
    {
        ModelAuthor DBauthors = new ModelDatabaseAuthor();
        authors.addAll((DBauthors.getAuthors()));
    }

    private void populatePublishingHouses()
    {
        ModelPublishingHouse DBpublishingHouse = new ModelDatabasePublishingHouse();
        publishingHouses.addAll(DBpublishingHouse.getPublishingHouses());
    }

    private void populateGenres()
    {
        ModelGenres DBgenres = new ModelDatabaseGenres();
        genres.addAll(DBgenres.getGenres());
    }

    private void populateFormats() {
        ModelFormat DBformats = new ModelDatabaseFormat();
        formats.addAll(DBformats.getFormats());
    }

    private void populateLanguages()
    {
        ModelLanguage DBlanguages = new ModelDatabaseLanguage();
        languages.addAll((DBlanguages.getLanguages()));
    }

    private void displayAlertAddAuthor(int numberOfAuthors)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Added");
        alert.setHeaderText(null);
        if(numberOfAuthors > 0)
            alert.setContentText(String.format("Author successfully added, add another %d authors", numberOfAuthors));
        else
            alert.setContentText("Last author successfully added");

        alert.showAndWait();
    }
}
