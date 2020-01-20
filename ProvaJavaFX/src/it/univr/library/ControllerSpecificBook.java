package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControllerSpecificBook
{
    @FXML
    private VBox bookInfoVBox;

    @FXML
    private HBox headerHBox;

    User user;

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }
}
