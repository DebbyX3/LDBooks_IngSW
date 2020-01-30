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
    private ComboBox<String> categoryComboBox;
    private ObservableList<String> categoryComboboxData = FXCollections.observableArrayList();


    @FXML
    private Button filterButton;

    @FXML
    private ComboBox<String> BookCombobox;
    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private Button selectBookButton;

    @FXML
    private TextField categoryTextField;

    @FXML
    private HBox headerHBox;

    private User manager;
    private Map<Book, Integer> cart;


    @FXML
    private void initialize()
    {

        //Inizializza combobox Genre
        populateGenreFilter();
        genreComboBox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreComboBox.getSelectionModel().selectFirst();

        populateCategory();
        categoryComboBox.setItems(categoryComboboxData);
        categoryComboBox.getSelectionModel().selectFirst();


        populateBooks();
        BookCombobox.setItems(bookIsbnAndTitle(books));
        BookCombobox.getSelectionModel().selectFirst();


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
        categoryComboboxData.add("All");
        categoryComboboxData.addAll(DBCategory.getCategory());
    }

    private void handleFilterButton(ActionEvent actionEvent)
    {

        ChartFilter filter = new ChartFilter();
        Genre genre =  genreComboBox.getValue();
        String category = categoryComboBox.getValue();

        if(!genre.equals(new Genre("All"))) // if the genre is not "all", add the genre to the filter
            filter.setGenre(genre);
        if(!category.equals("All")) // if the lang is not "all", add the lang to the filter
            filter.setCategory(category);

        chartsTableView.getColumns().clear();
        populateCharts(filter);
    }

    private void populateCharts(ChartFilter filter) {
        Model DBCharts = new ModelDatabaseCharts();
        View viewCharts = new ViewCharts();
        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView,DBCharts.getCharts(filter));
    }

    private void handleSelectBookButton(ActionEvent actionEvent)
    {

        Model DBSinglebook = new ModelDatabaseBooks();
        String[] isbn_Title = BookCombobox.getValue().split(" ");
        Book b = DBSinglebook.getSpecificBook(isbn_Title[0]);
        isbnLabel.setText(b.getISBN());
        titleLabel.setText(b.getTitle());
        String authors = b.getAuthors().toString();
        authorsLabel.setText( authors.substring(1,authors.length()-1));
        genreLabel.setText(b.getGenre().toString());

        // change chartView based on book selected
        populateCharts(new ChartFilter(new Genre(genreLabel.getText())));

        Model charts = new ModelDatabaseCharts();
        ArrayList<Charts> c = charts.getCharts(new ChartFilter(b.getGenre()));

        for (Charts chart: c) {
            if(chart.getISBN().equals(b.getISBN()))
            {
                rankTextField.setText(chart.getRank().toString());
                weekInTextField.setText(chart.getWeeksIn().toString());
                categoryTextField.setText(chart.getCategory());
            }
        }
    }

    private void handleInsertUpdateBookButton(ActionEvent actionEvent)
    {
        ChartFilter filter = new ChartFilter(new Genre(genreLabel.getText()), categoryTextField.getText());
        ArrayList<Charts> chartsForGenre = getChartFromGenre(filter);
        Charts bookToUpdateInCharts = null;

        if (isValidField()) {
            for (Charts chart : chartsForGenre) {
                if (chart.getISBN().equals(isbnLabel.getText())) {
                    bookToUpdateInCharts = chart;
                    break;
                }
            }

            if (bookToUpdateInCharts != null) {

                //UPDATE RANK IN TABLE VIEW:

                //book already exists, so just update
                Integer initRank = bookToUpdateInCharts.getRank();
                bookToUpdateInCharts.setRank(Integer.parseInt(rankTextField.getText()));
                Integer finalRank = Integer.parseInt(rankTextField.getText());
                ArrayList<Charts> modified;


                if (initRank < finalRank) {
                    modified = new ArrayList<>();
                    for (Charts chartsInView : chartsForGenre) {
                        if (!chartsInView.getISBN().equals(isbnLabel.getText()) && (chartsInView.getRank() > initRank && chartsInView.getRank() <= finalRank)) {
                            chartsInView.setRank(chartsInView.getRank() - 1);
                            modified.add(chartsInView);
                        }
                    }
                }
                else
                {
                    modified = new ArrayList<>();
                    for (Charts chartsInView : chartsForGenre) {
                        if (!chartsInView.getISBN().equals(isbnLabel.getText()) && (chartsInView.getRank() < initRank && chartsInView.getRank() >= finalRank)) {
                            chartsInView.setRank(chartsInView.getRank() + 1);
                            modified.add(chartsInView);
                        }
                    }
                }

                bookToUpdateInCharts.setWeeksIn(Integer.parseInt(weekInTextField.getText()));
                bookToUpdateInCharts.setCategory(categoryTextField.getText());

                modified.add(bookToUpdateInCharts);
                ModelDatabaseCharts updateDB = new ModelDatabaseCharts();

                for (Charts updateChartDB: modified)
                    updateDB.updateCharts(updateChartDB);

                updateChartsView(chartsForGenre);
            }
            else
            {
                Charts bookToInsertOnTheCharts = new Charts();
                bookToInsertOnTheCharts.setISBN(isbnLabel.getText());
                bookToInsertOnTheCharts.setRank(Integer.parseInt(rankTextField.getText()));
                bookToInsertOnTheCharts.setWeeksIn(Integer.parseInt(weekInTextField.getText()));
                bookToInsertOnTheCharts.setCategory(categoryTextField.getText());

                Integer initRank = 0;
                Integer finalRank = Integer.parseInt(rankTextField.getText());
                ArrayList<Charts> modified;

                modified = new ArrayList<>();
                for (Charts chartsInView : chartsForGenre)
                {
                    if (!chartsInView.getISBN().equals(isbnLabel.getText()) && (chartsInView.getRank() <= initRank && chartsInView.getRank() >= finalRank)) {
                        chartsInView.setRank(chartsInView.getRank() + 1);
                        modified.add(chartsInView);
                    }
                }

                //insert new book to charts
                ModelDatabaseCharts insertToCharts = new ModelDatabaseCharts();
                insertToCharts.insertBookOnTheCharts(bookToInsertOnTheCharts);

                ModelDatabaseCharts updateDB = new ModelDatabaseCharts();

                for (Charts updateChartDB: modified)
                    updateDB.updateCharts(updateChartDB);

                rankTextField.clear();
                weekInTextField.clear();
                updateChartsView(chartsForGenre);

            }
        }

    }


    private void updateChartsView(ArrayList<Charts> chartUpdated) {
        View viewCharts = new ViewCharts();
        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView,chartUpdated);

    }

    private void handleDeleteBookButton(ActionEvent actionEvent)
    {
        if(isValidField())
        {
            ModelDatabaseCharts deleteBookFromCharts = new ModelDatabaseCharts();
            deleteBookFromCharts.deleteBookFromCharts(isbnLabel.getText());
            populateCharts(new ChartFilter(new Genre(genreLabel.getText()), categoryComboBox.getValue()));
        }

    }

    private ArrayList<Charts> getChartFromGenre(ChartFilter filter)
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


