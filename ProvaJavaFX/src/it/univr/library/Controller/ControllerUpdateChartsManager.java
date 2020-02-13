package it.univr.library.Controller;

import it.univr.library.*;
import it.univr.library.Model.*;
import it.univr.library.View.View;
import it.univr.library.View.ViewCharts;
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
        ModelGenres DBGenres = new ModelDatabaseGenres();
        genreComboboxData.add(new Genre("All"));
        genreComboboxData.addAll(DBGenres.getGenres());
    }

    private void populateCategory()
    {
        ModelCharts DBCategory = new ModelDatabaseCharts();
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
        ModelCharts DBCharts = new ModelDatabaseCharts();
        ModelBooks DBBooks = new ModelDatabaseBooks();
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
        BookCombobox.getSelectionModel().selectFirst();


        if(!books.isEmpty())
        {
            // enable book ComboBox and button
            BookCombobox.setDisable(false);
            selectBookButton.setDisable(false);
        }

    }

    private void handleSelectBookButton(ActionEvent actionEvent)
    {
        // clear old fields
        rankTextField.clear();
        weekInTextField.clear();

        // fetch all the information of the book selected and fill all the fields
        ModelBooks DBSinglebook = new ModelDatabaseBooks();
        String[] isbn_Title = BookCombobox.getValue().split(" ");
        Book b = DBSinglebook.getSpecificBooksForGenre(isbn_Title[0]);
        isbnLabel.setText(b.getISBN());
        titleLabel.setText(b.getTitle());
        String authors = b.getAuthors().toString();
        authorsLabel.setText( authors.substring(1,authors.length()-1));
        genreLabel.setText(b.getGenre().toString());

        // fetch from DB the books of the specific filter and check if the selected book is in there
        ArrayList<Charts> booksInChart = getBooksInChartView();

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

    private ArrayList<Charts> getBooksInChartView()
    {
        ArrayList<Charts> booksInChart;
        ModelCharts DBCharts = new ModelDatabaseCharts();

        if(genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
            booksInChart = DBCharts.getGeneralCharts();
        else if(!genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
            booksInChart = DBCharts.getChartsForGenre(new ChartFilter(genreComboBox.getValue()));
        else if(genreComboBox.getValue().equals(new Genre("All")) && !categoryComboBox.getValue().equals(new Category("All")))
            booksInChart = DBCharts.getChartsForCategory(new ChartFilter(categoryComboBox.getValue()));
        else
            booksInChart = DBCharts.getChartsForCategoryAndGenre(new ChartFilter(genreComboBox.getValue(),categoryComboBox.getValue()));

        return booksInChart;
    }

    private void handleInsertUpdateBookButton(ActionEvent actionEvent)
    {
        ArrayList<Charts> booksInChart = getBooksInChartView();
        Charts book = null;

        //check if the book is in there
        if(isValidField())
        {
            for (Charts bookInChart: booksInChart) {
                if(bookInChart.getISBN().equals(isbnLabel.getText()))
                {
                    book = bookInChart;
                    break;
                }
            }

            // the book is already in the chart so just update the information such as rank and weeksIn
            if(book != null)
            {
                //take the old rank value and the new value to update view
                Integer initRank = book.getRank();
                Integer finalRank = Integer.parseInt(rankTextField.getText());


               //create an ArrayList of book in chart that will be modify, the first one is the book selected
                ArrayList<Charts> booksModified = new ArrayList<>();

                int precRank = 0;


                if(initRank < finalRank)
                {
                    for (Charts bookInChart : booksInChart)
                    {
                        if(bookInChart.getRank() - precRank <= 1)
                        {
                            if (!bookInChart.getISBN().equals(isbnLabel.getText()) && (bookInChart.getRank() > initRank && bookInChart.getRank() <= finalRank)) {
                                bookInChart.setRank(bookInChart.getRank() - 1);
                                booksModified.add(bookInChart);
                            }
                        }
                        else // if there is space between ranks stop updating chart
                            break;
                        precRank = bookInChart.getRank();
                    }
                }
                else
                {
                    for (Charts bookInChart : booksInChart)
                    {
                        if(bookInChart.getRank() - precRank <= 1)
                        {
                            if (!bookInChart.getISBN().equals(isbnLabel.getText()) && (bookInChart.getRank() < initRank && bookInChart.getRank() >= finalRank)) {
                                bookInChart.setRank(bookInChart.getRank() + 1);
                                booksModified.add(bookInChart);
                            }
                        }
                        else
                            break;

                        precRank = bookInChart.getRank();
                    }
                }

                //set the new rank for the specific book to be update into DB
                book.setRank(Integer.parseInt(rankTextField.getText()));
                book.setWeeksIn(Integer.parseInt(weekInTextField.getText()));
                booksModified.add(book);

                //Now update DB with the arrayList booksModified
                ModelCharts DBupdateCharts = new ModelDatabaseCharts();

                //check which chart update and update db
                if(genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreAllCategoryAll(bookToUpdate);

                }
                else if(!genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreSelectedCategoryAll(bookToUpdate);
                }
                else if(genreComboBox.getValue().equals(new Genre("All")) && !categoryComboBox.getValue().equals(new Category("All")))
                {
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreAllCategorySelected(bookToUpdate);
                }
                else
                {
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreSelectedCategorySelected(bookToUpdate);
                }

                //now update the view with the new values for the books
                ChartFilter chartToCreate = new ChartFilter(genreComboBox.getValue(), categoryComboBox.getValue());
                chartsTableView.getColumns().clear();
                populateChartsWithFilter(chartToCreate);

            }
            else
            {
                // the book is not in the chart yet, so create a new Chart and insert into the chart
                book = new Charts();
                book.setISBN(isbnLabel.getText());



                // check if there're books to update
                ArrayList<Charts> booksModified = new ArrayList<>();
                boolean empty = true;


                for (Charts bookInChart : booksInChart)
                {
                    if (bookInChart.getRank().equals(Integer.parseInt(rankTextField.getText())))
                    {
                        empty = false;
                        break;
                    }
                }

                int precRank = 0;

                if(!empty)
                {
                    for (Charts bookInChart : booksInChart)
                    {
                        if(bookInChart.getRank() - precRank <= 1)
                        {
                            if (bookInChart.getRank() >= Integer.parseInt(rankTextField.getText()))
                            {
                                bookInChart.setRank(bookInChart.getRank() + 1);
                                booksModified.add(bookInChart);
                            }
                        }
                        else
                            break;

                    }
                }

                book.setRank(Integer.parseInt(rankTextField.getText()));
                book.setWeeksIn(Integer.parseInt(weekInTextField.getText()));

                // based on filter set the category and genre and insert into DB the new book and update
                ModelCharts DBInsertBookIntoChart = new ModelDatabaseCharts();
                ModelCharts DBupdateCharts = new ModelDatabaseCharts();

                if(genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {
                    DBInsertBookIntoChart.insertBookOnTheChartsAllGenreAllCategory(book);
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreAllCategoryAll(bookToUpdate);
                }
                else if(!genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {
                    book.setGenreChart(genreComboBox.getValue().getName());
                    DBInsertBookIntoChart.insertBookOnTheChartsSelectedGenreAllCategory(book);
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreSelectedCategoryAll(bookToUpdate);
                }
                else if(genreComboBox.getValue().equals(new Genre("All")) && !categoryComboBox.getValue().equals(new Category("All")))
                {
                    book.setCategory(categoryComboBox.getValue());
                    DBInsertBookIntoChart.insertBookOnTheChartsAllGenreSelectedCategory(book);
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreAllCategorySelected(bookToUpdate);
                }
                else
                {
                    book.setCategory(categoryComboBox.getValue());
                    book.setGenreChart(genreComboBox.getValue().getName());
                    DBInsertBookIntoChart.insertBookOnTheChartsSelectedGenreSelectedCategory(book);
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreSelectedCategorySelected(bookToUpdate);
                }

                //now update the view with the new values for the books
                ChartFilter chartToCreate = new ChartFilter(genreComboBox.getValue(), categoryComboBox.getValue());
                chartsTableView.getColumns().clear();
                populateChartsWithFilter(chartToCreate);

            }
        }

        rankTextField.clear();
        weekInTextField.clear();
        bookNewInformationVBox.setDisable(true);
        selectBookButton.setDisable(true);
        BookCombobox.setDisable(true);
    }


    private void handleDeleteBookButton(ActionEvent actionEvent)
    {

        if(isValidField())
        {
            //now update the rank for the others books
            ArrayList<Charts> booksInChart = getBooksInChartView();
            boolean exists = false;

            for (Charts bookInChart: booksInChart)
            {
                if(bookInChart.getISBN().equals(isbnLabel.getText()))
                {
                    exists = true;
                    break;
                }

            }

            if(exists)
            {
                ModelCharts deleteBookFromCharts = new ModelDatabaseCharts();

                //check what is the book to remove and remove it from the right chart
                if(genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {
                    deleteBookFromCharts.deleteBookFromChartsAllGenreAllCategory(isbnLabel.getText());
                }
                else if(!genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {
                    deleteBookFromCharts.deleteBookFromChartsSelectedGenreAllCategory(isbnLabel.getText(), genreComboBox.getValue());
                }
                else if(genreComboBox.getValue().equals(new Genre("All")) && !categoryComboBox.getValue().equals(new Category("All")))
                {
                    deleteBookFromCharts.deleteBookFromChartsAllGenreSelectedCategory(isbnLabel.getText(), categoryComboBox.getValue());
                }
                else
                {
                    deleteBookFromCharts.deleteBookFromChartsSelectedGenreSelectedCategory(isbnLabel.getText(), genreComboBox.getValue(),categoryComboBox.getValue());
                }


                ArrayList<Charts> booksModified = new ArrayList<>();
                int precRank = 0;

                for (Charts bookInChart: booksInChart)
                {
                    // check if there is a space between ranks
                    if(bookInChart.getRank() - precRank <= 1)
                    {
                        if(bookInChart.getRank() > Integer.parseInt(rankTextField.getText()))
                        {
                            bookInChart.setRank(bookInChart.getRank() - 1);
                            booksModified.add(bookInChart);
                        }
                    }
                    else
                        break;
                    precRank = bookInChart.getRank();
                }

                //Update charts and the view
                ModelCharts DBupdateCharts = new ModelDatabaseCharts();

                if(genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreAllCategoryAll(bookToUpdate);
                }
                else if(!genreComboBox.getValue().equals(new Genre("All")) && categoryComboBox.getValue().equals(new Category("All")))
                {

                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreSelectedCategoryAll(bookToUpdate);
                }
                else if(genreComboBox.getValue().equals(new Genre("All")) && !categoryComboBox.getValue().equals(new Category("All")))
                {
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreAllCategorySelected(bookToUpdate);
                }
                else
                {
                    for (Charts bookToUpdate: booksModified)
                        DBupdateCharts.updateChartsGenreSelectedCategorySelected(bookToUpdate);
                }

                //now update the view with the new values for the books
                ChartFilter chartToCreate = new ChartFilter(genreComboBox.getValue(), categoryComboBox.getValue());
                chartsTableView.getColumns().clear();
                populateChartsWithFilter(chartToCreate);
            }
            else
            {
                displayAlert("The selected book is not in the chart!");
            }

        }

        rankTextField.clear();
        weekInTextField.clear();
        bookNewInformationVBox.setDisable(true);
        selectBookButton.setDisable(true);
        BookCombobox.setDisable(true);
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


