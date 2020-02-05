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
import java.util.Map;
import java.util.Set;

public interface View
{
    public default void buildBookForCatalog(VBox catalogVBox, List<BookGroup> bookGroups, ControllerCatalog controllerCatalog)
    {}

    public default void buildBookForSpecificBook(VBox bookInfoVBox, Label titleLabel, Label languageLabel, BookGroup bookGroup, ControllerSpecificBook controllerSpecificBook, User user)
    {}

    public default void buildViewProfileInformation(RegisteredClient registeredUser, Label nameLabel, Label surnameLabel, Label phoneLabel, Label emailLabel, VBox addressVbox)
    {}

    public default void addEmptyAddressEditProfile(VBox addressVBox)
    {}

    public default void buildEditProfileInformation(RegisteredClient registeredUser, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField passwordTextField, Label mailLabel, VBox addressVbox)
    {}

    public default void buildChart(TableView chartsTableView, ArrayList<Charts> charts)
    {}

    public default void buildLibrocard(User user, Librocard librocard, Text nameSurnameText, Text pointsText, Text issueDateText, Text idText)
    {}

    public default void buildOrders(ArrayList<Order> orders, VBox orderVBox, ScrollPane orderScrollPane)
    {}

    public default void buildLibroCard(ArrayList<Librocard> librocards, VBox LibrocardVBox, ScrollPane LibroCardScrollPane){};

    public default void buildCart(Map<Book, Integer> cart, VBox cartVBox, ScrollPane cartScrollPane, ControllerCart controllerCart, Label subTotalLabel, Label shippingCostLabel, Label TotalPriceLabel, Label libroCardPointsLabel){};

    public default List<AddressFields> getAddressFieldsList() {return null;}
}
