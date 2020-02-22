package it.univr.library.Controller;

import javafx.scene.control.Alert;

public class ControllerAlert
{
    public void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }

    public Alert displayConfirmation(String s)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Check your input");
        alert.setHeaderText("Check your input");
        alert.setContentText(s);

        return alert;
    }
}
