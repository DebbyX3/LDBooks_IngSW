package it.univr.library.Controller;

import it.univr.library.*;
import it.univr.library.Model.*;
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
import java.util.Map;
import java.util.Optional;

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
    private ArrayList<Author> authorsToLinkToBook = new ArrayList<>();
    private Map<Book, Integer> cart;

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
            displayAlert("Available quantity must be numerical!\n");
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
    }


    private void handleAddNewBookButton(ActionEvent actionEvent)
    {
        if(!isAnyFieldEmptyorNotValid())
        {
            if(!doesISBNExists(ISBNTextField.getText()))
            {
                ModelBooks DBBook = new ModelDatabaseBooks();
                Book book = fetchBookInformation();

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
                displayAlert("A book with this ISBN already exists!");
            }
        }
    }

    private boolean doesISBNExists(String ISBN)
    {
        ModelBooks DBBook = new ModelDatabaseBooks();
        return DBBook.getSpecificBook(ISBN).getISBN().equals(ISBN);
    }

    private Book fetchBookInformation()
    {
        Book book = new Book();

        book.setISBN(ISBNTextField.getText().trim());
        book.setTitle(titleTextField.getText().trim());
        book.setAuthors(authorsToLinkToBook);
        book.setDescription(descriptionTextArea.getText().trim());
        book.setFormat(formatComboBox.getValue());
        book.setGenre(genreComboBox.getValue());
        book.setLanguage(languageComboBox.getValue());
        book.setPublishingHouse(publishingHouseComboBox.getValue());
        book.setPages(Integer.parseInt(pagesTextField.getText().trim()));
        book.setPoints(Integer.parseInt(librocardPointsTextField.getText().trim()));
        book.setMaxQuantity(Integer.parseInt(availableQuantitySpinner.getEditor().textProperty().get()));
        book.setPrice(new BigDecimal(priceTextField.getText().trim()));
        book.setPublicationYear(Integer.parseInt(publicationYearTextField.getText().trim()));
        book.setImagePath(imagePathTextField.getText().trim());

        return book;
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

    private boolean isAnyFieldEmptyorNotValid()
    {
        StringBuilder error = new StringBuilder();
        Optional<ButtonType> result;

        if(ISBNTextField.getText().trim().equals(""))
            error.append("- ISBN must be filled!\n");

        if(!isISBNvalid(ISBNTextField.getText().trim()) && !isASINvalid(ISBNTextField.getText().trim()))
                error.append("- ISBN has no format 10 or 13 or ASIN!\n");

        if(titleTextField.getText().trim().isEmpty())
            error.append("- Title must be filled!\n");

        if(descriptionTextArea.getText().trim().isEmpty())
            error.append("- Description must be filled!\n");
        if(isNumerical(descriptionTextArea.getText().trim()))
            error.append("- Description must be at least alphabetic\n");

        if(publicationYearTextField.getText().trim().isEmpty())
            error.append("- Publication year must be filled\n");

        if(!isNumerical(publicationYearTextField.getText().trim()))
        {
            error.append("- Publication year must be numerical\n");
        }
        else
        {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if(Integer.parseInt(publicationYearTextField.getText().trim()) < 1000 || Integer.parseInt(publicationYearTextField.getText().trim()) > year)
                error.append(String.format("- Publication year must be between 1000 and %d\n",year));
        }

        if(pagesTextField.getText().trim().isEmpty())
            error.append("- Number of pages must be filled!\n");
        if(!isNumerical(pagesTextField.getText().trim()))
            error.append("- Number of pages must be numerical!\n");

        if(librocardPointsTextField.getText().trim().isEmpty())
            error.append("- LibroCard Points must be filled!\n");
        if(!isNumerical(librocardPointsTextField.getText().trim()))
            error.append("- Librocard Points must be numerical!\n");

        //this 'if' should never happen, but let's keep it for stability
        if(     !isNumerical(availableQuantitySpinner.getEditor().textProperty().get().trim()) ||
                availableQuantitySpinner.getValue() == null ||
                availableQuantitySpinner.getEditor().textProperty().get().trim().equals(""))
        {
            error.append("- Available Quantity must be numerical!\n");
            availableQuantitySpinner.getEditor().textProperty().set("0");
        }
        else
        {
            if (Integer.parseInt(availableQuantitySpinner.getValue().toString().trim()) > 100) {
                result = displayConfirmation("The quantity available is greater 100, are you sure?\n").showAndWait();

                if (result.get() != ButtonType.OK) {
                    displayAlert("Ok select a new available quantity!");
                    return true;
                }
            }
        }

        if(imagePathTextField.getText().trim().isEmpty())
            error.append("- No imagePath specified!\n");

        //check if I've at least an author for the book
        if(authorsToLinkToBook.isEmpty())
            error.append("- Book needs at least one author!\n");

        if(priceTextField.getText().trim().isEmpty())
            error.append("- Price must be filled!\n");

        if(!isNumerical(priceTextField.getText().trim()))
        {
            error.append("- Price must be numerical!\n");
        }
        else
        {
            if(Float.parseFloat(priceTextField.getText().trim()) > 1000)
            {
                result = displayConfirmation("The price that you insert is greater than 1000€, are you sure?\n").showAndWait();
                if(result.get() != ButtonType.OK)
                {
                    displayAlert("Ok select a new price!");
                    return true;
                }
            }
        }

        //displayConfirmation();
        if(!error.toString().isEmpty())
            displayAlert(error.toString());

        return !error.toString().isEmpty();
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

    private Alert displayConfirmation(String s)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Check Book Information!");
        alert.setContentText(s);

        return alert;
    }

    private boolean isNumerical(String s) {
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
    }

    private boolean isISBNvalid(String s){ return s.matches("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})" +
            "[- 0-9X]{13}$|97[89]-[0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)" +
            "(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");}

    private boolean isASINvalid(String s)
    {
        return s.matches("\\b(([0-9]{9}[0-9X])|(B[0-9A-Z]{9}))\\b");
    }

}
