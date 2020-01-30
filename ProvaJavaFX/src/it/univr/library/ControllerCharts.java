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
    private ComboBox categoryCombobox;
    private ObservableList<Genre> genreComboboxData = FXCollections.observableArrayList();

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
        //Inizializza combobox Genre
        categoryCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        categoryCombobox.getSelectionModel().selectFirst();

        populateCharts();
        //handler bottone filtra
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

    private void populateCharts()
    {
        Model DBCharts = new ModelDatabaseCharts();
        View viewCharts = new ViewCharts();
        Filter filter = new Filter((Genre) categoryCombobox.getValue());
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
        Filter filter = new Filter((Genre) categoryCombobox.getValue());


        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView, DBCharts.getCharts(filter));
    }


}
