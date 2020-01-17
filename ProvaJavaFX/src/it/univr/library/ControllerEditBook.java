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
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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

        populateBooks();
        BookCombobox.setItems(bookIsbnAndTitle(books));
        BookCombobox.getSelectionModel().selectFirst();

        populateAllfield(books.get(0));

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
        authorsToDelete.clear();
        authors.clear();
        authorComboBox.cancelEdit();
        numberAuthorsComboBox.getSelectionModel().selectFirst();
        populateAllfield(b);
    }

    private void populateAllfield(Book selectedBook)
    {
        authorsToDelete.clear();
        authorsToLinkToBook.clear();
        oldAuthors.clear();
        authorListView.setItems(oldAuthors);
        numberAuthorsComboBox.setDisable(false);
        deleteAuthorButton.setDisable(true);
        authorComboBox.setDisable(true);
        numberOfAuthors = 0;

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
        availableQuantityComboBox.getSelectionModel().select(selectedBook.getMaxQuantity());

        populateNumbAuthorsComboBox();
        numberAuthorsComboBox.setItems(numberAuthors);
        // take the value from comboBox to iterate ad take the number of authors selected in the combobox
        numberAuthorsComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> numberOfAuthors = newValue );
        numberAuthorsComboBox.getSelectionModel().selectedItemProperty().addListener((v) -> authorComboBox.setDisable(false));
        numberAuthorsComboBox.getSelectionModel().selectedItemProperty().addListener((v) -> numberAuthorsComboBox.setDisable(true));

        populateAuthors();
        authorComboBox.setItems(authors);
        authorListView.setItems(oldAuthors);
        authorListView.getSelectionModel().selectedItemProperty().addListener((v) -> deleteAuthorButton.setDisable(false));

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
            //todo check if some author is selected
            if(authorToDelete != null)
            {
                authorsToDelete.add(authorToDelete);
                authorListView.getItems().remove(authorToDelete);
                displayAlertDeleteAuthor("Author successfully removed!");
                if(authorListView.getItems().isEmpty())
                    deleteAuthorButton.setDisable(true);
            }
            else
            {
                displayAlert("Select an author to delete!");
            }
        }
        else
        {
            displayAlert("No author to delete!");
        }

    }


    private void handleEditBookButton(ActionEvent actionEvent)
    {
        //fetch all fields and create a new book to update the information of the existing book on db
        if(!isAnyFieldEmptyorNotValid())
        {
            Book book = fetchBookInformation();

            //update db on writers, remove the authors take from authorsToDelete arrayList<>
            Model DBdeleteAuthors = new ModelDatabaseAuthor();

            for (Author authorTodelete: authorsToDelete) {
                DBdeleteAuthors.deleteLinkBookToAuthors(authorTodelete.getId(),book.getISBN());
            }

            //make query to update book
            Model DBupdateBook = new ModelDatabaseBooks();
            DBupdateBook.updateBook(book);

            //make query to link newAuthors
            Model DBupdateLinkTobook = new ModelDatabaseAuthor();
            for (Author authorToLink: book.getAuthors())
                DBupdateLinkTobook.linkBookToAuthors(authorToLink.getId(), book.getISBN());

            //change scene
            StageManager addEditBooks = new StageManager();
            addEditBooks.setStageAddEditBooks((Stage) editBookButton.getScene().getWindow(), manager);
        }
    }

    private boolean isAnyFieldEmptyorNotValid()
    {
        StringBuilder error = new StringBuilder();

        if(titleTextField.getText().trim().isEmpty())
            error.append("- Title must be filled!\n");

        if(descriptionTextArea.getText().trim().isEmpty())
            error.append("- Description must be filled!\n");
        if(isNumerical(descriptionTextArea.getText().trim()))
            error.append("- Description must be at least alphabetic\n");

        if(publicationYearTextField.getText().trim().isEmpty())
            error.append("- Publication year must be filled\n");

        if(!isNumerical(publicationYearTextField.getText().trim()))
            error.append("- Publication year must be numerical\n");

        int year = Calendar.getInstance().get(Calendar.YEAR);
        if(Integer.parseInt(publicationYearTextField.getText().trim()) < 1800 || Integer.parseInt(publicationYearTextField.getText().trim()) > year)
            error.append(String.format("- Publication year must be between 1800 and %d\n",year));

        if(pagesTextField.getText().trim().isEmpty())
            error.append("- Number of pages must be filled!\n");
        if(!isNumerical(pagesTextField.getText().trim()))
            error.append("- Number of pages must be numerical!\n");


        if(librocardPointsTextField.getText().trim().isEmpty())
            error.append("- LibroCard Points must be filled!\n");
        if(!isNumerical(librocardPointsTextField.getText().trim()))
            error.append("- Librocard Points must be numerical!\n");

        if(imagePathTextField.getText().trim().isEmpty())
            error.append("- No imagePath specified!\n");

        //check if I've at least an author for the book
        if(authorListView.getItems().isEmpty() && authorsToLinkToBook.isEmpty())
            error.append("- Book needs at least one author!");

        if(priceTextField.getText().trim().isEmpty())
            error.append("- Price must be filled!\n");
        if(!isNumerical(priceTextField.getText().trim()))
            error.append("- Price must be numerical!\n");

        Optional<ButtonType> result;
        if(Float.parseFloat(priceTextField.getText().trim()) > 1000)
        {
            result = displayConfirmation().showAndWait();
            if(result.get() != ButtonType.OK)
            {
                displayAlert("Ok select a new price!");
                return true;
            }
        }

        //displayConfirmation();
        if(!error.toString().isEmpty())
            displayAlert(error.toString());

        return !error.toString().isEmpty();

    }

    private Book fetchBookInformation() {
        Book book = new Book();

        book.setAuthors(authorsToLinkToBook);
        book.setDescription(descriptionTextArea.getText().trim());
        book.setFormat(formatComboBox.getValue());
        book.setGenre(genreComboBox.getValue());
        book.setImagePath(imagePathTextField.getText().trim());
        book.setISBN(isbnLabel.getText());
        book.setLanguage(languageComboBox.getValue());
        book.setMaxQuantity(availableQuantityComboBox.getValue());
        book.setPages(Integer.parseInt(pagesTextField.getText().trim()));
        book.setPoints(Integer.parseInt(librocardPointsTextField.getText().trim()));
        book.setTitle(titleTextField.getText().trim());
        book.setPrice(new BigDecimal(priceTextField.getText().trim()));
        book.setPublicationYear(Integer.parseInt(publicationYearTextField.getText().trim()));
        book.setPublishingHouse(publishingHouseComboBox.getValue());

        return book;
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
            boolean alreadyAuthor = false;

            for (Author authorToCheck: authorsToLinkToBook)
            {
                if (author.equals(authorToCheck))
                {
                    exists = true;
                    break;
                }
            }

            for (Author authorToCheck: authorListView.getItems())
            {
                if (author.equals(authorToCheck))
                {
                    alreadyAuthor = true;
                    break;
                }
            }

            if(!alreadyAuthor)
            {
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
            else
            {
                displayAlert("The author selected is already an author for this book, choose another one!");
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

    private void populateFormats()
    {
        Model DBformats = new ModelDatabaseFormat();
        formats.addAll(DBformats.getFormats());
    }

    private void populateLanguages()
    {
        Model DBlanguages = new ModelDatabaseLanguage();
        languages.addAll((DBlanguages.getLanguages()));
    }

    private void populateAvailableQuantityComboBox()
    {
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

    private Alert displayConfirmation()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Check Book price!");
        alert.setContentText("The price that you insert is greater than 1000€, are you sure?\n");

        return alert;
    }


    private void displayAlertDeleteAuthor(String s)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully removed!");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }

    private boolean isNumerical(String s) {
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
    }


}
