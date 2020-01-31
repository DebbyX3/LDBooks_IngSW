package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.Map;

public class ControllerCharts {

    @FXML
    private ComboBox genreComboBox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

    @FXML
    private ComboBox categoryComboBox;
    private ObservableList<String> categoryComboboxData = FXCollections.observableArrayList();

    @FXML
    private HBox headerHBox;

    @FXML
    private Button filterButton;

    @FXML
    private TableView<User> chartsTableView;

    private User user;

    private Map<Book,Integer> cart;

    public ControllerCharts()
    {
        // Riempio il genere
        populateGenreFilter();
    }

    @FXML
    private void initialize()
    {
        populateGenreFilter();
        genreComboBox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreComboBox.getSelectionModel().selectFirst();

        populateCategory();
        categoryComboBox.setItems(categoryComboboxData);
        categoryComboBox.getSelectionModel().selectFirst();

        filterButton.setOnAction(this::handleFilterButton);
    }


    public void setUser(User user)
    {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
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
        Genre genre = (Genre) genreComboBox.getValue();
        String category = (String) categoryComboBox.getValue();

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




}
