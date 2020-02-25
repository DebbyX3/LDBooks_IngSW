package it.univr.library.Controller;

import it.univr.library.Data.Book;
import it.univr.library.Utils.StageManager;
import it.univr.library.Data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerUserPage
{
    @FXML
    private Button myOrdersButton;

    @FXML
    private Button viewProfileButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button myLibroCardButton;

    @FXML
    private HBox headerHBox;

    private User user;
    private Map<Book, Integer> cart;


    @FXML
    private void initialize()
    {
        //setto il listener
        myOrdersButton.setOnAction(this::handleMyOrdersButton);
        viewProfileButton.setOnAction(this::handleViewProfileButton);
        editProfileButton.setOnAction(this::handleEditProfileButton);
        myLibroCardButton.setOnAction(this::handleMyLibroCardButton);
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart=cart;
    }


    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
    }

    private void handleMyLibroCardButton(ActionEvent actionEvent)
    {
        StageManager viewLibroCard = new StageManager();
        viewLibroCard.setStageViewLibroCard((Stage) myLibroCardButton.getScene().getWindow(), user, cart);
    }

    private void handleViewProfileButton(ActionEvent actionEvent)
    {
        StageManager viewProfileStage = new StageManager();
        viewProfileStage.setStageViewProfile((Stage) viewProfileButton.getScene().getWindow(), user,cart);
    }

    private void handleEditProfileButton(ActionEvent actionEvent)
    {
        StageManager editProfileStage = new StageManager();
        editProfileStage.setStageEditProfile((Stage) editProfileButton.getScene().getWindow(), user, cart);
    }

    private void handleMyOrdersButton(ActionEvent actionEvent)
    {
        StageManager orderUserStage = new StageManager();
        orderUserStage.setStageOrderUser((Stage) myOrdersButton.getScene().getWindow(), user, cart);
    }


   
}
