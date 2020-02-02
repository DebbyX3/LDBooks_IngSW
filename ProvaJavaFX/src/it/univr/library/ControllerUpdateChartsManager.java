package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

public class ControllerUpdateChartsManager {

    @FXML
    private VBox chartsVBox;

    @FXML
    private TableView<?> chartsTableView;

    @FXML
    private VBox bookNewInformationVBox;

    @FXML
    private TextField rankTextField;

    @FXML
    private Label isbnLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorsLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private TextField weekInTextField;

    @FXML
    private Button insertBookButton;

    @FXML
    private Button deleteBookButton;

    @FXML
    private ComboBox<Genre> genreComboBox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Category> categoryComboBox;
    private ObservableList<Category> categoryComboboxData = FXCollections.observableArrayList();


    @FXML
    private Button filterButton;

    @FXML
    private ComboBox<String> BookCombobox;
    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private Button selectBookButton;


    @FXML
    private HBox headerHBox;

    private User manager;
    private Map<Book, Integer> cart;


    @FXML
    private void initialize()
    {

        bookNewInformationVBox.setDisable(true);
        selectBookButton.setDisable(true);

        //Inizializza combobox Genre
        populateGenreFilter();
        genreComboBox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreComboBox.getSelectionModel().selectFirst();

        populateCategory();
        categoryComboBox.setItems(categoryComboboxData);
        categoryComboBox.getSelectionModel().selectFirst();


        //populateCharts();
        //handler bottone filtra
        filterButton.setOnAction(this::handleFilterButton);
        selectBookButton.setOnAction(this::handleSelectBookButton);
        deleteBookButton.setOnAction(this::handleDeleteBookButton);
        insertBookButton.setOnAction(this::handleInsertUpdateBookButton);

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


    private void populateGenreFilter()
    {
        Model DBGenres = new ModelDatabaseGenres();
        genreComboboxData.add(new Genre("All"));
        genreComboboxData.addAll(DBGenres.getGenres());
    }

    private void populateCategory()
    {
        Model DBCategory = new ModelDatabaseCharts();
        categoryComboboxData.add(new Category("All"));
        categoryComboboxData.addAll(DBCategory.getCategory());
    }

    private void handleFilterButton(ActionEvent actionEvent)
    {
        // fetch value from comboBox
        ChartFilter chartToCreate = new ChartFilter(genreComboBox.getValue(), categoryComboBox.getValue());
        chartsTableView.getColumns().clear();
        populateChartsWithFilter(chartToCreate);
    }

    private void populateChartsWithFilter(ChartFilter filters)
    {
        // prepare all the stuff to create new View
        Model DBCharts = new ModelDatabaseCharts();
        Model DBBooks = new ModelDatabaseBooks();
        View viewCharts = new ViewCharts();
        chartsTableView.getColumns().clear();

        // check what is the chart to create:
        if(filters.getGenre().equals(new Genre("All")) && filters.getCategory().equals(new Category("All")))
            viewCharts.buildChart(chartsTableView,DBCharts.getGeneralCharts());
        else if(!filters.getGenre().equals(new Genre("All")) && filters.getCategory().equals(new Category("All")))
            viewCharts.buildChart(chartsTableView,DBCharts.getChartsForGenre(filters));
        else if(filters.getGenre().equals(new Genre("All")) && !filters.getCategory().equals(new Category("All")))
            viewCharts.buildChart(chartsTableView,DBCharts.getChartsForCategory(filters));
        else
          viewCharts.buildChart(chartsTableView,DBCharts.getChartsForCategoryAndGenre(filters));



        books.clear();
        BookCombobox.getSelectionModel().clearSelection();
        BookCombobox.getItems().clear();

        // fill book ComboBox for the specific filter fields:
        if(filters.getGenre().equals(new Genre("All")))
            books.addAll(DBBooks.getAllBooks(new ChartFilter()));
        else
            books.addAll(DBBooks.getAllBooks(filters));

        // add books in the ComboBox
        //BookCombobox.getSelectionModel().
        BookCombobox.setItems(bookIsbnAndTitle(books));

        // enable book ComboBox and button
        BookCombobox.setDisable(false);
        selectBookButton.setDisable(false);
    }

    private void handleSelectBookButton(ActionEvent actionEvent)
    {
        // clear old fields
        rankTextField.clear();
        weekInTextField.clear();

        // fetch all the information of the book selected and fill all the fields
        Model DBSinglebook = new ModelDatabaseBooks();
        String[] isbn_Title = BookCombobox.getValue().split(" ");
        Book b = DBSinglebook.getSpecificBooksForGenre(isbn_Title[0]);
        isbnLabel.setText(b.getISBN());
        titleLabel.setText(b.getTitle());
        String authors = b.getAuthors().toString();
        authorsLabel.setText( authors.substring(1,authors.length()-1));
        genreLabel.setText(b.getGenre().toString());

        // fetch from DB the books of the specific filter and check if the selected book is in there
        Model DBCharts = new ModelDatabaseCharts();
        ArrayList<Charts> booksInChart;

        if(genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
          booksInChart = DBCharts.getGeneralCharts();
        else if(!genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
            booksInChart = DBCharts.getChartsForGenre(new ChartFilter(genreComboBox.getValue()));
        else if(genreComboBox.getValue().equals(new Genre("All")) && !categoryComboBox.getValue().equals(new Category("All")))
            booksInChart = DBCharts.getChartsForCategory(new ChartFilter(categoryComboBox.getValue()));
        else
            booksInChart = DBCharts.getChartsForCategoryAndGenre(new ChartFilter(genreComboBox.getValue(),categoryComboBox.getValue()));

        //now check if the book selected is in there and update all the fields
        for (Charts bookInChart: booksInChart) {
            if(bookInChart.getISBN().equals(b.getISBN()))
            {
                rankTextField.setText(bookInChart.getRank().toString());
                weekInTextField.setText(bookInChart.getWeeksIn().toString());
            }
        }

        bookNewInformationVBox.setDisable(false);
    }

    private void handleInsertUpdateBookButton(ActionEvent actionEvent)
    {


    }


    private void updateChartsView(ArrayList<Charts> chartUpdated) {

    }

    private void handleDeleteBookButton(ActionEvent actionEvent)
    {

    }


    private boolean isValidField()
    {
        return false;
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

    private void populateBooks(Genre filterGenre)
    {

    }

    private void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }

    private boolean isNumerical(String s) {
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
    }


}


