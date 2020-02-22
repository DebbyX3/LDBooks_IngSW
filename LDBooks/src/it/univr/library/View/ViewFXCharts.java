package it.univr.library.View;

import it.univr.library.Charts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class ViewFXCharts implements ViewCharts
{
    @Override
    public void buildChart(TableView chartsTableView, ArrayList<Charts> charts)
    {
        TableColumn rank = new TableColumn("Rank");
        TableColumn ISBN = new TableColumn("ISBN");
        TableColumn title = new TableColumn("Title");
        TableColumn authors = new TableColumn("Authors");
        TableColumn genre = new TableColumn("Genre");
        TableColumn weeksIn = new TableColumn("WeeksIn");
        TableColumn category = new TableColumn("Category");

        chartsTableView.getColumns().addAll(rank, ISBN, title, authors, genre, weeksIn, category);

        final ObservableList<Charts> chart = FXCollections.observableArrayList();
        chart.addAll(charts);

        rank.setCellValueFactory(new PropertyValueFactory<Chart, String>("Rank"));
        ISBN.setCellValueFactory(new PropertyValueFactory<Chart, String>("ISBN"));
        title.setCellValueFactory(new PropertyValueFactory<Chart, String>("Title"));
        authors.setCellValueFactory(new PropertyValueFactory<Chart, String>("Authors"));
        genre.setCellValueFactory(new PropertyValueFactory<Chart, String>("Genre"));
        weeksIn.setCellValueFactory(new PropertyValueFactory<Chart, String>("WeeksIn"));
        category.setCellValueFactory(new PropertyValueFactory<Chart, String>("Category"));

        chartsTableView.setItems(chart);
        chartsTableView.getSortOrder().add(rank);
    }
}
