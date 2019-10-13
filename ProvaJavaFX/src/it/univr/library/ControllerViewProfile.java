package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControllerViewProfile {


    @FXML
    private HBox headerHBox;

    @FXML
    private VBox informationVBox;

    User user;

    public ControllerViewProfile()
    {

    }

    @FXML
    private void initialize()
    {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }


    public void populateUserInformations()
    {
        Model DBInformations = new ModelDatabaseUserInformations();
        //genero schermata fxml con le informazioni e le riempio
        View viewInformationsUser = new ViewInformationsUser();

        viewInformationsUser.buildInformations(DBInformations.getRegisteredUser(user), informationVBox);
    }


}
