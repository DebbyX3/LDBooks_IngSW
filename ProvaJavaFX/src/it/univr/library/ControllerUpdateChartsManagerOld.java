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

public class ControllerUpdateChartsManagerOld {

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
    private HBox headerHBox;

    private User manager;
    private Map<Book, Integer> cart;
}

/*
    @FXML
    private void initialize()
    {

        bookNewInformationVBox.setDisable(true);
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


    }

    private void populateCharts(ChartFilter filter) {
        Model DBCharts = new ModelDatabaseCharts();
        View viewCharts = new ViewCharts();
        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView,DBCharts.getCharts(filter));
    }

    private void handleSelectBookButton(ActionEvent actionEvent)
    {
        rankTextField.clear();
        weekInTextField.clear();

        Model DBSinglebook = new ModelDatabaseBooks();
        String[] isbn_Title = BookCombobox.getValue().split(" ");
        Book b = DBSinglebook.getSpecificBooksForGenre(isbn_Title[0]);
        isbnLabel.setText(b.getISBN());
        titleLabel.setText(b.getTitle());
        String authors = b.getAuthors().toString();
        authorsLabel.setText( authors.substring(1,authors.length()-1));
        genreLabel.setText(b.getGenre().toString());

        // change chartView based on book selected
        ArrayList<Charts> c = getChartFromGenre(new ChartFilter(b.getGenre(),categoryComboBox.getValue()));

        for (Charts chart: c) {
            if(chart.getISBN().equals(b.getISBN()))
            {
                rankTextField.setText(chart.getRank().toString());
                weekInTextField.setText(chart.getWeeksIn().toString());
            }
        }

        bookNewInformationVBox.setDisable(false);
    }

    private void handleInsertUpdateBookButton(ActionEvent actionEvent)
    {
        ArrayList<Charts> chartsInView;

        chartsInView = getChartFromGenre(new ChartFilter(genreComboBox.getValue(),categoryComboBox.getValue()));

        Charts bookToUpdateInCharts = null;

        if (isValidField())
        {
            for (Charts chart : chartsInView) {
                if (chart.getISBN().equals(isbnLabel.getText())) {
                    bookToUpdateInCharts = chart;
                    break;
                }
            }

            if (bookToUpdateInCharts != null)
            {

                //UPDATE RANK IN TABLE VIEW:
                //book already exists, so just update
                Integer initRank = bookToUpdateInCharts.getRank();
                bookToUpdateInCharts.setRank(Integer.parseInt(rankTextField.getText()));
                Integer finalRank = Integer.parseInt(rankTextField.getText());
                ArrayList<Charts> modified;


                if (initRank < finalRank)
                {
                    modified = new ArrayList<>();

                    for (Charts chartsView : chartsInView)
                    {
                        if (!chartsView.getISBN().equals(isbnLabel.getText()) && (chartsView.getRank() > initRank && chartsView.getRank() <= finalRank)) {
                            chartsView.setRank(chartsView.getRank() - 1);
                            modified.add(chartsView);
                        }
                    }
                }
                else
                {
                    modified = new ArrayList<>();
                    for (Charts chartsView : chartsInView)
                    {
                        if (!chartsView.getISBN().equals(isbnLabel.getText()) && (chartsView.getRank() < initRank && chartsView.getRank() >= finalRank)) {
                            chartsView.setRank(chartsView.getRank() + 1);
                            modified.add(chartsView);
                        }
                    }
                }

                bookToUpdateInCharts.setWeeksIn(Integer.parseInt(weekInTextField.getText()));
                if(categoryComboBox.getValue().equals("All"))
                    bookToUpdateInCharts.setCategory("All");
                else
                    bookToUpdateInCharts.setCategory(categoryComboBox.getValue());

                System.out.println(bookToUpdateInCharts.getCategory());

                modified.add(bookToUpdateInCharts);
                ModelDatabaseCharts updateDB = new ModelDatabaseCharts();

                for (Charts updateChartDB: modified)
                {
                    updateChartDB.setCategory(categoryComboBox.getValue());
                    updateDB.updateCharts(updateChartDB);
                }


                updateChartsView(chartsInView);
            }
            else
            {
                // BOOK IS NOT IN CHART

                Charts bookToInsertOnTheCharts = new Charts();
                bookToInsertOnTheCharts.setTitle(titleLabel.getText());
                bookToInsertOnTheCharts.setISBN(isbnLabel.getText());
                bookToInsertOnTheCharts.setRank(Integer.parseInt(rankTextField.getText()));
                bookToInsertOnTheCharts.setWeeksIn(Integer.parseInt(weekInTextField.getText()));
                if(categoryComboBox.getValue().equals("All"))
                    bookToInsertOnTheCharts.setCategory("All");
                else
                    bookToInsertOnTheCharts.setCategory(categoryComboBox.getValue());

                System.out.println(bookToInsertOnTheCharts.getCategory());



                bookToInsertOnTheCharts.setGenre(new Genre(genreLabel.getText()));
                Model DBSinglebook = new ModelDatabaseBooks();
                String[] isbn_Title = BookCombobox.getValue().split(" ");
                Book b = DBSinglebook.getSpecificBooksForGenre(isbn_Title[0]);
                bookToInsertOnTheCharts.setAuthors(b.getAuthors());


                Integer finalRank = Integer.parseInt(rankTextField.getText());
                ArrayList<Charts> modified = new ArrayList<>();
                boolean empty = true;

                for (Charts chartsView : chartsInView)
                {
                    if (chartsView.getRank().equals(finalRank)) {
                        empty = false;
                        break;
                    }
                }

                if(!empty)
                {
                    for (Charts chartsView : chartsInView)
                    {
                        if (chartsView.getRank() >= finalRank)
                        {
                            chartsView.setRank(chartsView.getRank() + 1);
                            modified.add(chartsView);
                        }
                    }
                }


                ModelDatabaseCharts insertToCharts = new ModelDatabaseCharts();
                insertToCharts.insertBookOnTheCharts(bookToInsertOnTheCharts);
                chartsInView.add(bookToInsertOnTheCharts);


                //insert new book to charts
                ModelDatabaseCharts updateDB = new ModelDatabaseCharts();

                for (Charts updateChartDB: modified)
                {
                    updateChartDB.setCategory(categoryComboBox.getValue());
                    updateDB.updateCharts(updateChartDB);
                }


                updateChartsView(chartsInView);
            }
        }


        rankTextField.clear();
        weekInTextField.clear();
        bookNewInformationVBox.setDisable(true);
        BookCombobox.setDisable(true);
    }


    private void updateChartsView(ArrayList<Charts> chartUpdated) {
        View viewCharts = new ViewCharts();
        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView,chartUpdated);
    }

    private void handleDeleteBookButton(ActionEvent actionEvent)
    {
        ArrayList<Charts> modified = new ArrayList<>();
        if(isValidField())
        {
            ModelDatabaseCharts deleteBookFromCharts = new ModelDatabaseCharts();
            if(categoryComboBox.getValue().equals("All"))
                deleteBookFromCharts.deleteBookFromCharts(isbnLabel.getText(),"All");
            else
                deleteBookFromCharts.deleteBookFromCharts(isbnLabel.getText(),categoryComboBox.getValue());

            // UPDATE RANK
            ArrayList<Charts> chartsInView = getChartFromGenre(new ChartFilter(genreComboBox.getValue()));
            for (Charts c: chartsInView)
            {
                if(c.getRank() > Integer.parseInt(rankTextField.getText()))
                {
                    c.setRank(c.getRank() - 1);
                    modified.add(c);
                }

            }

            ModelDatabaseCharts updateDB = new ModelDatabaseCharts();

            for (Charts updateChartDB: modified)
            {
                updateChartDB.setCategory(categoryComboBox.getValue());
                updateDB.updateCharts(updateChartDB);
            }


            updateChartsView(chartsInView);
        }

        rankTextField.clear();
        weekInTextField.clear();
        bookNewInformationVBox.setDisable(true);

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

    private void populateBooks(Genre filterGenre)
    {
        Model DBbooks = new ModelDatabaseBooks();
        books.clear();
        if(filterGenre.equals(new Genre("All")))
            books.addAll(DBbooks.getAllBooks());
        else
            books.addAll(DBbooks.getSpecificBooksForGenre(filterGenre));
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

*/
