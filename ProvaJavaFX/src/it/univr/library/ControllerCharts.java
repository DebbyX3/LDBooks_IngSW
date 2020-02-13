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
    private ComboBox<Genre> genreComboBox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Category> categoryComboBox;
    private ObservableList<Category> categoryComboboxData = FXCollections.observableArrayList();

    @FXML
    private HBox headerHBox;

    @FXML
    private Button filterButton;

    @FXML
    private TableView<User> chartsTableView;

    private User user;

    private Map<Book,Integer> cart;



    @FXML
    private void initialize()
    {
        //Inizializza combobox Genre
        populateGenreFilter();
        populateCategory();

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
        ModelGenres DBGenres = new ModelDatabaseGenres();
        genreComboboxData.add(new Genre("All"));
        genreComboboxData.addAll(DBGenres.getGenres());
        genreComboBox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreComboBox.getSelectionModel().selectFirst();
    }

    private void populateCategory()
    {
        ModelCharts DBCategory = new ModelDatabaseCharts();
        categoryComboboxData.add(new Category("All"));
        categoryComboboxData.addAll(DBCategory.getCategory());
        categoryComboBox.setItems(categoryComboboxData);
        categoryComboBox.getSelectionModel().selectFirst();
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
    }





}
