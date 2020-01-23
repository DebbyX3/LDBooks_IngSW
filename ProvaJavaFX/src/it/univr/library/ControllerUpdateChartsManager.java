package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
        insertBookButton.setOnAction(this::handleInsertUpdateBookButton);

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

    private void populateCharts() {
        updateChartView(new Filter(genreCombobox.getValue()));
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
        //TODO check if is it possibile to use iterator().hasNext()
        String authors = b.getAuthors().toString();
        authorsLabel.setText( authors.substring(1,authors.length()-1));
        genreLabel.setText(b.getGenre().toString());

        // change chartView based on book selected
        updateChartView(new Filter(new Genre(genreLabel.getText())));

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

    private void handleInsertUpdateBookButton(ActionEvent actionEvent)
    {
        Filter filter = new Filter(new Genre(genreLabel.getText()));
        ArrayList<Charts> chartsForGenre = getChartFromGenre(filter);
        Charts bookToUpdateInCharts = null;

        if (isValidField())
        {
            for (Charts chart: chartsForGenre)
            {
                if(chart.getISBN().equals(isbnLabel.getText()))
                {
                    bookToUpdateInCharts = chart;
                    break;
                }
            }

            if(bookToUpdateInCharts != null)
            {
                //book already exists, so just update
                bookToUpdateInCharts.setRank(Integer.parseInt(rankTextField.getText()));
                bookToUpdateInCharts.setWeeksIn(Integer.parseInt(weekInTextField.getText()));
                ModelDatabaseCharts updateCharts = new ModelDatabaseCharts();
                updateCharts.updateCharts(bookToUpdateInCharts);
                updateChartView(new Filter(bookToUpdateInCharts.getGenre()));

            }
            else
            {
                Charts bookToInsertOnTheCharts = new Charts();
                bookToInsertOnTheCharts.setISBN(isbnLabel.getText());
                bookToInsertOnTheCharts.setRank(Integer.parseInt(rankTextField.getText()));
                bookToInsertOnTheCharts.setWeeksIn(Integer.parseInt(weekInTextField.getText()));

                //insert new book to charts
                ModelDatabaseCharts insertToCharts = new ModelDatabaseCharts();
                insertToCharts.insertBookOnTheCharts(bookToInsertOnTheCharts);

                updateChartView(new Filter(genreCombobox.getValue()));
            }
        }
    }

    private void handleDeleteBookButton(ActionEvent actionEvent)
    {
        if(isValidField())
        {
            ModelDatabaseCharts deleteBookFromCharts = new ModelDatabaseCharts();
            deleteBookFromCharts.deleteBookFromCharts(isbnLabel.getText());
            updateChartView(new Filter(new Genre(genreLabel.getText())));
        }

    }

    private ArrayList<Charts> getChartFromGenre(Filter filter)
    {
        Model DBCharts = new ModelDatabaseCharts();
        return DBCharts.getCharts(filter);
    }

    private boolean isValidField()
    {
        StringBuilder error = new StringBuilder();
        if(rankTextField.getText().equals(""))
            error.append("- Rank field must be filled!\n");
        else
            if(!isNumerical(rankTextField.getText()))
                error.append("- Rank field must be numerical!\n");

        if(weekInTextField.getText().equals(""))
            error.append("- Week In must be filled!\n");
        else
            if(!isNumerical(weekInTextField.getText()))
                error.append("- Week In must be numerical!\n");

        if(!error.toString().isEmpty())
            displayAlert(error.toString());

        return error.toString().isEmpty();
    }

    private void updateChartView(Filter filter)
    {
        Model DBCharts = new ModelDatabaseCharts();
        View viewCharts = new ViewCharts();
        genreCombobox.setValue(filter.getGenre());
        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView, DBCharts.getCharts(filter));
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
