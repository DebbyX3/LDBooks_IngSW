package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.chart.Chart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public interface View
{
    public default void buildCatalog(ArrayList<Book> books, VBox catalogVBox)
    {}

    public default void buildInformations(RegisteredUser registeredUser, Label nameLabel, Label surnameLabel, Label phoneLabel, Label emailLabel, VBox addressVbox)
    {}

    public default void buildInformationsEdit(RegisteredUser registeredUser, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, Label mailLabel, VBox addressVbox)
    {}

    public default void buildChart(ArrayList<Charts> charts, TableView chartsTableView, TableColumn<Charts, Integer> rankTableColumn,
                           TableColumn<Charts, String> ISBNTableColumn, TableColumn<Charts, String> titleTableColumn,
                           TableColumn<Charts, List<String>> authorsTableColumn, TableColumn<Charts, String> genreTableColumn,
                           TableColumn<Charts, Integer> weeksInTableColumn)
    {}

    public default void buildLibrocard(User user, Librocard librocard, Text nameSurnameText, Text pointsText, Text issueDateText, Text idText)
    {}

    public default void buildOrders(ArrayList<Order> orders, VBox orderVBox)
    {}
}
