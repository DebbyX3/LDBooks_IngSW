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
        StageManager addGenre = new StageManager();
        addGenre.setStageAddGenre((Stage) addGenreButton.getScene().getWindow(), manager);
    }

    private void handleAddLanguageButton(ActionEvent actionEvent)
    {
        StageManager addNewLanguage = new StageManager();
        addNewLanguage.setStageAddNewLanguage((Stage) addLanguageButton.getScene().getWindow(), manager);
    }

    private void handleAddPHouseButton(ActionEvent actionEvent)
    {
        StageManager addNewPHouse = new StageManager();
        addNewPHouse.setStageAddNewPhouse((Stage) addPHouseButton.getScene().getWindow(), manager);
    }

    private void handleAddNewBookButton(ActionEvent actionEvent)
    {
        StageManager addNewBook = new StageManager();
        addNewBook.setStageAddNewBook((Stage) addNewBookButton.getScene().getWindow(), manager);
    }

    private void handleEditBookButton(ActionEvent actionEvent)
    {
        StageManager editBook = new StageManager();
        editBook.setStageEditBook((Stage) editBookButton.getScene().getWindow(), manager);
    }


    private void handleAddAuthorButton(ActionEvent actionEvent)
    {
        StageManager addNewAuthor = new StageManager();
        addNewAuthor.setStageAddNewAuthor((Stage) addAuthorButton.getScene().getWindow(), manager);
    }

    private void handleAddFormatButton(ActionEvent actionEvent)
    {
        StageManager addFormat = new StageManager();
        addFormat.setStageAddFormat((Stage) addFormatButton.getScene().getWindow(), manager);
    }

}
