package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class ControllerEditBook {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button editBookButton;

    @FXML
    private Label isbnLabel;

    @FXML
    private TextField titleTextField;

    @FXML
    private ComboBox<Author> authorComboBox;
    private ObservableList<Author> authors = FXCollections.observableArrayList();

    @FXML
    private ListView<Author> authorListView;
    private ObservableList<Author> oldAuthors = FXCollections.observableArrayList();

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
    private Button deleteAuthorButton;

    @FXML
    private ComboBox<String> BookCombobox;
    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private TextField imagePathTextField;

    @FXML
    private Button filterButton;

    private User manager;
    private int numberOfAuthors = 0;
    private ArrayList<Author> authorsToLinkToBook = new ArrayList<>();
    private ArrayList<Author> authorsToDelete = new ArrayList<>();

    @FXML
    private void initialize()
    {
        populateNumbAuthorsComboBox();
        numberAuthorsComboBox.setItems(numberAuthors);
        // take the value from comboBox to iterate ad take the number of authors selected in the combobox
        numberAuthorsComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> numberOfAuthors = newValue );
        numberAuthorsComboBox.getSelectionModel().selectedItemProperty().addListener((v) -> authorComboBox.setDisable(false));

        populateAuthors();
        authorComboBox.setItems(authors);
        authorListView.setItems(oldAuthors);

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

        populateBooks();
        BookCombobox.setItems(bookIsbnAndTitle(books));
        BookCombobox.getSelectionModel().selectFirst();
        populateAllfield(books.get(0));

        filterButton.setOnAction(this::handleFilterButton);
        deleteAuthorButton.setOnAction(this::handleDeleteAuthorButton);
        selectAuthorButton.setOnAction(this::handleSelectAuthorButton);
        editBookButton.setOnAction(this::handleEditBookButton);

    }

    private void handleFilterButton(ActionEvent actionEvent)
    {
        Model DBSinglebook = new ModelDatabaseBooks();
        String[] isbn_Title = BookCombobox.getValue().split(" ");
        Book b = DBSinglebook.getSpecificBook(isbn_Title[0]);
        populateAllfield(b);
    }

    private void populateAllfield(Book selectedBook)
    {
        isbnLabel.setText(selectedBook.getISBN());
        titleTextField.setText(selectedBook.getTitle());
        authorListView.setItems(arrayListToObservableList(selectedBook.getAuthors()));
        descriptionTextArea.setText(selectedBook.getDescription());
        publicationYearTextField.setText(selectedBook.getPublicationYear().toString());
        publishingHouseComboBox.getSelectionModel().select(selectedBook.getPublishingHouse());
        genreComboBox.getSelectionModel().select(selectedBook.getGenre());
        formatComboBox.getSelectionModel().select(selectedBook.getFormat());
        languageComboBox.getSelectionModel().select(selectedBook.getLanguage());
        pagesTextField.setText(selectedBook.getPages().toString());
        priceTextField.setText(selectedBook.getPrice().toString());
        librocardPointsTextField.setText(selectedBook.getPoints().toString());
        imagePathTextField.setText(selectedBook.getImagePath());
    }

    private ObservableList<Author> arrayListToObservableList(List<Author> authors)
    {
        ObservableList<Author> a = FXCollections.observableArrayList();
        a.addAll(authors);
        return a;
    }

    private ObservableList<String> bookIsbnAndTitle(ObservableList<Book> books) {
        ObservableList<String> booksIsbnAndTitle = FXCollections.observableArrayList();
        String finalBook;
        for (Book b: books)
        {
            finalBook = b.getISBN() + " " + b.getTitle();
            booksIsbnAndTitle.add(finalBook);
        }
        return booksIsbnAndTitle;
    }

    private void populateBooks()
    {
        Model DBbooks = new ModelDatabaseBooks();
        books.addAll(DBbooks.getAllBooks());
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

    private void handleDeleteAuthorButton(ActionEvent actionEvent)
    {
        if(!authorListView.getItems().isEmpty())
        {
            Author authorToDelete = authorListView.getSelectionModel().getSelectedItem();
            authorsToDelete.add(authorToDelete);
            authorListView.getItems().remove(authorToDelete);
            displayAlertDeleteAuthor("Author successfully removed!");
            if(authorListView.getItems().isEmpty())
                deleteAuthorButton.setDisable(true);
        }
        else
        {
            displayAlert("Nobody author to delete!");
        }

    }


    private void handleEditBookButton(ActionEvent actionEvent)
    {

    }

    private void handleSelectAuthorButton(ActionEvent actionEvent)
    {
        numberAuthorsComboBox.setDisable(true);
        Author author = authorComboBox.getValue();

        if(author == null)
        {
            displayAlert("choose an Author!");
        }
        else
        {
            boolean exists = false;

            for (Author authorToCheck: authorsToLinkToBook) {
                if (author.equals(authorToCheck)) {
                    exists = true;
                    break;
                }
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
    }


    private void populateNumbAuthorsComboBox()
    {
        for(int i=1; i<=10;i++)
            numberAuthors.add(i);
    }

    private void populateAuthors()
    {
        Model DBauthors = new ModelDatabaseAuthor();
        authors.addAll((DBauthors.getAuthors()));
        oldAuthors.addAll(DBauthors.getAuthorsForSpecificBook(isbnLabel.getText()));
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

    private void displayAlertDeleteAuthor(String s)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully removed!");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }



}
