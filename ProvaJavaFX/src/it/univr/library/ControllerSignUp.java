package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ControllerSignUp {

    @FXML
    private Button catalogButton;

    @FXML
    private Button chartsButton;

    @FXML
    private ImageView cartImageView;

    @FXML
    private Button signUpButton;

    @FXML
    private void initialize()
    {
        catalogButton.setOnAction(this::handleCatalogButton); //setto il listener
        chartsButton.setOnAction(this::handleChartsButton);
    }

    private void handleCatalogButton(ActionEvent event)
    {
        StageManager catalogStage = new StageManager();
        catalogStage.setStageCatalog((Stage) catalogButton.getScene().getWindow());
    }

    private void handleChartsButton(ActionEvent event)
    {
        StageManager chartsStage = new StageManager();
        chartsStage.setStageCharts((Stage) chartsButton.getScene().getWindow(), "hello");
    }
}
