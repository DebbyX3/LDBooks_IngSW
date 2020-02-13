package it.univr.library.View;

import it.univr.library.Charts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Chart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ViewCharts implements View
{
    @Override
    public void buildChart(TableView chartsTableView, ArrayList<Charts> charts)
    {
        TableColumn Rank = new TableColumn("Rank");
        TableColumn ISBN = new TableColumn("ISBN");
        TableColumn Title = new TableColumn("Title");
        TableColumn Authors = new TableColumn("Authors");
        TableColumn Genre = new TableColumn("Genre");
        TableColumn WeeksIn = new TableColumn("WeeksIn");
        TableColumn Category = new TableColumn("Category");

        chartsTableView.getColumns().addAll(Rank, ISBN, Title, Authors, Genre, WeeksIn,Category);

        final ObservableList<Charts> chart = FXCollections.observableArrayList();
        for (Charts c: charts)
        {
           chart.add(c);
        }

        Rank.setCellValueFactory(new PropertyValueFactory<Chart,String>("Rank"));
        ISBN.setCellValueFactory(new PropertyValueFactory<Chart,String>("ISBN"));
        Title.setCellValueFactory(new PropertyValueFactory<Chart,String>("Title"));
        Authors.setCellValueFactory(new PropertyValueFactory<Chart,String>("Authors"));
        Genre.setCellValueFactory(new PropertyValueFactory<Chart,String>("Genre"));
        WeeksIn.setCellValueFactory(new PropertyValueFactory<Chart,String>("WeeksIn"));
        Category.setCellValueFactory(new PropertyValueFactory<Chart,String>("Category"));

        chartsTableView.setItems(chart);
        chartsTableView.getSortOrder().add(Rank);

    }

}
