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
    public default void buildBookForCatalog(VBox catalogVBox, List<BookGroup> bookGroups, ControllerCatalog controllerCatalog)
    {}

    public default void buildBookForSpecificBook(VBox bookInfoVBox, Label titleLabel, Label languageLabel, BookGroup bookGroup, ControllerSpecificBook controllerSpecificBook)
    {}

    public default void buildInformations(RegisteredClient registeredUser, Label nameLabel, Label surnameLabel, Label phoneLabel, Label emailLabel, VBox addressVbox)
    {}

    public default void buildInformationsEdit(RegisteredClient registeredUser, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, Label mailLabel, VBox addressVbox)
    {}

    public default void buildChart(TableView chartsTableView, ArrayList<Charts> charts)
    {}

    public default void buildLibrocard(User user, Librocard librocard, Text nameSurnameText, Text pointsText, Text issueDateText, Text idText)
    {}

    public default void buildOrders(ArrayList<Order> orders, VBox orderVBox, ScrollPane orderScrollPane)
    {}

    public default void buildLibroCard(ArrayList<Librocard> librocards, VBox LibrocardVBox, ScrollPane LibroCardScrollPane){};

    public default void buildCart(ArrayList<Book> books, VBox cartVBox, ScrollPane cartScrollPane){};


}
