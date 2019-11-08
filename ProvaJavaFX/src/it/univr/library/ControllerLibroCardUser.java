package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;

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

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }


    public void populateUserLibroCard()
    {
        Model DBUserLibroCard = new ModelDatabaseUserLibrocard();
        //genero schermata fxml con le informazioni e le riempio
        View viewInformationsUserLibrocard = new ViewInformationsUserLibrocard();

        viewInformationsUserLibrocard.buildLibrocard(user, DBUserLibroCard.getLibrocardInformation(user), nameSurnameText, pointsText, issueDateText, idText);
    }
}
