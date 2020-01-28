package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ControllerHomepage {

    @FXML
    private Button enterButton;


    @FXML
    private void initialize()
    {
        // Setta listener bottone
        enterButton.setOnAction(this::handleEnterButton);
    }

    private void handleEnterButton(ActionEvent actionEvent) {
        StageManager catalogStage = new StageManager();
        catalogStage.setStageCatalog((Stage) enterButton.getScene().getWindow(), null, new HashMap<Book,Integer>());
    }

}
