package it.univr.library;

import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ViewCharts implements View
{
    @Override
    public void buildChart(ArrayList<Charts> charts, TableView chartsTableView, TableColumn<Charts, Integer> rankTableColumn,
                                   TableColumn<Charts, String> ISBNTableColumn, TableColumn<Charts, String> titleTableColumn,
                                   TableColumn<Charts, List<String>> authorsTableColumn, TableColumn<Charts, String> genreTableColumn,
                                   TableColumn<Charts, Integer> weeksInTableColumn)
    {
        rankTableColumn.setCellValueFactory(new PropertyValueFactory<Charts, Integer>("id   "));
        //ISBNTableColumn.setCellValueFactory(new PropertyValueFactory<Charts, String>("name"));
        //titleTableColumn.setCellValueFactory(new PropertyValueFactory<Charts, String>("active"));
       // authorsTableColumn.setCellValueFactory(new PropertyValueFactory<Charts, List<String>>("id"));
        ////genreTableColumn.setCellValueFactory(new PropertyValueFactory<Charts, String>("name"));
       // weeksInTableColumn.setCellValueFactory(new PropertyValueFactory<Charts, Integer>("active"));

        chartsTableView.getItems().setAll(charts);
    }

}
