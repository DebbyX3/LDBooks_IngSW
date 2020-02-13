package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Map;

public class ControllerLibroCardUser {

    @FXML
    private HBox headerHBox;

    @FXML
    private Text nameSurnameText;

    @FXML
    private Text pointsText;

    @FXML
    private Text issueDateText;

    @FXML
    private Text idText;

    private User user;
    private Map<Book, Integer> cart;

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
    }


    public void populateUserLibroCard()
    {
        ModelUserLibrocard DBUserLibroCard = new ModelDatabaseUserLibrocard();
        //genero schermata fxml con le informazioni e le riempio
        View viewInformationUserLibrocard = new ViewInformationUserLibrocard();

        viewInformationUserLibrocard.buildLibrocard(user, DBUserLibroCard.getLibrocardInformation(user), nameSurnameText, pointsText, issueDateText, idText);
    }


}
