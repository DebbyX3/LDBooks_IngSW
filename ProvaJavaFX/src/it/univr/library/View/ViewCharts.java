package it.univr.library.View;

import it.univr.library.Charts;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public interface ViewCharts
{
    public void buildChart(TableView chartsTableView, ArrayList<Charts> charts);
}
