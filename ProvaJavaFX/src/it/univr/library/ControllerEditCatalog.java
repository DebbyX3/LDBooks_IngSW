package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerEditCatalog {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button addAuthorButton;

    @FXML
    private Button addLanguageButton;

    @FXML
    private Button addFormatButton;

    @FXML
    private Button addGenreButton;

    @FXML
    private Button addPHouseButton;

    @FXML
    private Button addNewBookButton;

    @FXML
    private Button editBookButton;

    private User manager;

    @FXML
    private void initialize()
    {
        //setto il listener
        addAuthorButton.setOnAction(this::handleAddAuthorButton);
        addFormatButton.setOnAction(this::handleAddFormatButton);
        addGenreButton.setOnAction(this::handleAddGenreButton);
        addLanguageButton.setOnAction(this::handleAddLanguageButton);
        addPHouseButton.setOnAction(this::handleAddPHouseButton);
        addNewBookButton.setOnAction(this::handleAddNewBookButton);
        editBookButton.setOnAction(this::handleEditBookButton);

    }

    public void setManager(User manager)
    {
        this.manager = manager;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox);
    }

    private void handleAddGenreButton(ActionEvent actionEvent)
    {

    }

    private void handleAddLanguageButton(ActionEvent actionEvent)
    {
        StageManager addNewLanguage = new StageManager();
        addNewLanguage.setStageAddNewLanguage((Stage) addLanguageButton.getScene().getWindow(), manager);
    }

    private void handleAddPHouseButton(ActionEvent actionEvent)
    {

    }

    private void handleAddNewBookButton(ActionEvent actionEvent)
    {

    }

    private void handleEditBookButton(ActionEvent actionEvent)
    {

    }


    private void handleAddAuthorButton(ActionEvent actionEvent)
    {
        StageManager addNewAuthor = new StageManager();
        addNewAuthor.setStageAddNewAuthor((Stage) addAuthorButton.getScene().getWindow(), manager);
    }

    private void handleAddFormatButton(ActionEvent actionEvent)
    {

    }

}
