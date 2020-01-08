package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ControllerAddBook {

    @FXML
    private HBox headerHBox;

    @FXML
    private TextField imagePathTextField;

    @FXML
    private TextField isbnTextField;

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
    private ComboBox<Integer> availableQuantityComboBox;
    private ObservableList<Integer> availableQuantity = FXCollections.observableArrayList();

    @FXML
    private Button selectAuthorButton;

    @FXML
    private ComboBox<Integer> numberAuthorsComboBox;
    private ObservableList<Integer> numberAuthors = FXCollections.observableArrayList();

    @FXML
    private Button addNewBookButton;

    private User manager;
    private int numberOfAuthors = 0;
    private ArrayList<Author> authorsToLinkToBook = new ArrayList<>();

    @FXML
    private void initialize()
    {
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

        populateAvailableQuantityComboBox();
        availableQuantityComboBox.setItems(availableQuantity);

        selectAuthorButton.setOnAction(this::handleSelectAuthorButton);
        addNewBookButton.setOnAction(this::handleAddNewBookButton);

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

    private void handleSelectAuthorButton(ActionEvent actionEvent)
    {
        numberAuthorsComboBox.setDisable(true);
        Author author = authorComboBox.getValue();
        boolean exists = false;

        for (Author authorToCheck: authorsToLinkToBook) {
            if(author.equals(authorToCheck))
                exists = true;
        }

        if(!exists)
        {
            authorsToLinkToBook.add(author);
            System.out.println(authorsToLinkToBook.toString());

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
           displayAlert("Author already selected, choose another one!");
        }

    }


    private void handleAddNewBookButton(ActionEvent actionEvent)
    {
        Book book = new Book();
        book.setAuthors(authorsToLinkToBook);
        book.setDescription(descriptionTextArea.getText());
        book.setFormat(formatComboBox.getValue().getName());
        book.setGenre(genreComboBox.getValue().getName());
        book.setImagePath(imagePathTextField.getText());
        book.setISBN(isbnTextField.getText());
        book.setLanguage(languageComboBox.getValue().getName());
        book.setMaxQuantity(availableQuantityComboBox.getValue());
        book.setPages(Integer.parseInt(pagesTextField.getText()));
        book.setPoints(Integer.parseInt(librocardPointsTextField.getText()));
        book.setTitle(titleTextField.getText());
        book.setPrice(new BigDecimal(priceTextField.getText()));
        book.setPublicationYear(Integer.parseInt(publicationYearTextField.getText()));
        book.setPublishingHouse(publishingHouseComboBox.getValue().getName());

        Model DBbook = new ModelDatabaseBooks();
        Model DBauthor = new ModelDatabaseAuthor();
        DBbook.addNewBookToDB(book);

        for (Author authorToLink: book.getAuthors())
            DBauthor.linkBookToAuthors(authorToLink.getId(), book.getISBN());


        //change scene
        StageManager addEditBooks = new StageManager();
        addEditBooks.setStageAddEditBooks((Stage) addNewBookButton.getScene().getWindow(), manager);

    }


    private void populateNumbAuthorsComboBox()
    {
        for(int i=1; i<=10;i++)
            numberAuthors.add(i);
    }

    private void popolateAuthors()
    {
        Model DBauthors = new ModelDatabaseAuthor();
        authors.addAll((DBauthors.getAuthors()));
    }

    private void populatePublishingHouses()
    {
        Model DBpublishingHouse = new ModelDatabasePublishingHouse();
        publishingHouses.addAll(DBpublishingHouse.getPublishingHouses());
    }

    private void populateGenres()
    {
        Model DBgenres = new ModelDatabaseGenres();
        genres.addAll(DBgenres.getGenres());
    }

    private void populateFormats() {
        Model DBformats = new ModelDatabaseFormat();
        formats.addAll(DBformats.getFormats());
    }

    private void populateLanguages()
    {
        Model DBlanguages = new ModelDatabaseLanguage();
        languages.addAll((DBlanguages.getLanguages()));
    }

    private void populateAvailableQuantityComboBox() {
        for(int i=1; i<=50;i++)
            availableQuantity.add(i);
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

    private void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
