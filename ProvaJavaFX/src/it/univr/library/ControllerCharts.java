package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class ControllerCharts {

    @FXML
    private ComboBox genreCombobox;
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
        genreCombobox.setItems(genreComboboxData);    //setto il combobox del genere con i dati messi in generecomboboxdata
        genreCombobox.getSelectionModel().selectFirst();

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
        Filter filter = new Filter((Genre) genreCombobox.getValue());
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
        Filter filter = new Filter((Genre) genreCombobox.getValue());


        chartsTableView.getColumns().clear();
        viewCharts.buildChart(chartsTableView, DBCharts.getCharts(filter));
    }


}
