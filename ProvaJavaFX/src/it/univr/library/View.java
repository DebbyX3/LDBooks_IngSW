package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public interface View
{
    public default void buildCatalog(ArrayList<Book> books, VBox catalogVBox)
    {}

    public default void buildInformations(RegisteredUser registeredUser, VBox infomationHBox)
    {}


    public default void buildChart(ArrayList<Charts> charts, TableView chartsTableView, TableColumn<Charts, Integer> rankTableColumn,
                           TableColumn<Charts, String> ISBNTableColumn, TableColumn<Charts, String> titleTableColumn,
                           TableColumn<Charts, List<String>> authorsTableColumn, TableColumn<Charts, String> genreTableColumn,
                           TableColumn<Charts, Integer> weeksInTableColumn)
    {}
}
