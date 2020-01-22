package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ControllerUpdateChartsManager {

    @FXML
    private VBox chartsVBox;

    @FXML
    private TableView<?> chartsTableView;

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
    private ComboBox<Genre> genreCombobox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

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

    @FXML
    private void initialize()
    {

        //Inizializza combobox Genre
        populateGenreFilter();
        genreCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();

        populateBooks();
        BookCombobox.setItems(bookIsbnAndTitle(books));
        BookCombobox.getSelectionModel().selectFirst();


        populateCharts();
        //handler bottone filtra
        filterButton.setOnAction(this::handleFilterButton);
        selectBookButton.setOnAction(this::handleSelectBookButton);
        deleteBookButton.setOnAction(this::handleDeleteBookButton);
        insertBookButton.setOnAction(this::handleInsertBookButton);

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

    private void populateCharts()
    {
        Model DBCharts = new ModelDatabaseCharts();
        View viewCharts = new ViewCharts();
        Filter filter = new Filter(genreCombobox.getValue());
        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView, DBCharts.getCharts(filter));
    }

    private void populateGenreFilter()
    {
        Model DBGenres = new ModelDatabaseGenres();
        genreComboboxData.addAll(DBGenres.getGenres());
    }

    private void handleFilterButton(ActionEvent actionEvent)
    {
        Model DBCharts = new ModelDatabaseCharts();
        View viewCharts = new ViewCharts();
        Filter filter = new Filter(genreCombobox.getValue());


        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView, DBCharts.getCharts(filter));
    }

    private void handleSelectBookButton(ActionEvent actionEvent)
    {

        Model DBSinglebook = new ModelDatabaseBooks();
        String[] isbn_Title = BookCombobox.getValue().split(" ");
        Book b = DBSinglebook.getSpecificBook(isbn_Title[0]);
        isbnLabel.setText(b.getISBN());
        titleLabel.setText(b.getTitle());
        String authors = b.getAuthors().toString();
        authorsLabel.setText( authors.substring(1,authors.length()-2));
        genreLabel.setText(b.getGenre().toString());

        Model charts = new ModelDatabaseCharts();
        ArrayList<Charts> c = charts.getCharts(new Filter(b.getGenre(), b.getLanguage()));
        for (Charts chart: c) {
            if(chart.getISBN().equals(b.getISBN()))
            {
                rankTextField.setText(chart.getRank().toString());
                weekInTextField.setText(chart.getWeeksIn().toString());
            }

        }
    }

    private void handleInsertBookButton(ActionEvent actionEvent)
    {

    }

    private void handleDeleteBookButton(ActionEvent actionEvent)
    {

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
}
