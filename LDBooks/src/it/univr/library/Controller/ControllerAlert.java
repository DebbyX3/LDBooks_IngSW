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
        alert.setTitle("Confirm");
        alert.setHeaderText("Confirm");
        alert.setContentText(s);

        alert.showAndWait();

        return alert;
    }

    public void displayInformation(String s)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setContentText(s);

        alert.showAndWait();
    }
}
