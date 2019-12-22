package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControllerOrderUser {

    @FXML
    private VBox orderVBox;

    @FXML
    private HBox headerHBox;

    @FXML
    private Label dateLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label orderLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label ISBNLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label librocardLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private ImageView bookImageView;

    @FXML
    private GridPane singleOrderGridPane;

    private User user;

    @FXML
    private void initialize()
    {
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }

    private void handleLogOutButton(ActionEvent actionEvent)
    {

    }

    private void handleCartImageView(MouseEvent mouseEvent)
    {

    }


    public void populateOrderUser()
    {

        Model DBorders = new ModelDatabaseOrder();
        View viewOrders = new ViewOrders();

        viewOrders.buildOrders(DBorders.getOrders(user), dateLabel,addressLabel, totalPriceLabel, orderLabel,
                titleLabel, authorLabel, publisherLabel, ISBNLabel, priceLabel, librocardLabel, statusLabel,
                progressBar, bookImageView,singleOrderGridPane);

        }
}
