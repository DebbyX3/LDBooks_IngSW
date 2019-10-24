package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class ControllerUpdateProfile {
    RegisteredUser registeredUser;

    @FXML
    private HBox headerHBox;
    

    public void setUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }


    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(registeredUser, headerHBox);
    }

    public void populateUserInformations() {
    }
}
