package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerAddAuthor {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button addNewAuthorButton;

    @FXML
    private ListView<Author> authorsListView;
    private ObservableList<Author> authors = FXCollections.observableArrayList();

    @FXML
    private TextField nameAuthorTextField;

    @FXML
    private TextField surnameAuthorTextField;

    private User manager;

    @FXML
    private void initialize()
    {
        populateAuthors();
        authorsListView.setItems(authors);
        addNewAuthorButton.setOnAction(this::handleAddNewAuthorButton);
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

    private void populateAuthors()
    {
        Model DBauthors = new ModelDatabaseAuthor();
        authors.addAll((DBauthors.getAuthors()));
    }

    private void handleAddNewAuthorButton(ActionEvent actionEvent)
    {
        if(!nameAuthorTextField.getText().equals("") || !surnameAuthorTextField.getText().equals(""))
        {
            Model DBinsertNewAuthor = new ModelDatabaseAuthor();
            DBinsertNewAuthor.addNewAuthor(nameAuthorTextField.getText().trim(), surnameAuthorTextField.getText().trim());

            //change scene
            StageManager addEditBooks = new StageManager();
            addEditBooks.setStageAddEditBooks((Stage) addNewAuthorButton.getScene().getWindow(), manager);
        }
        else
        {
            displayAlert("Fill at least one field between name or surname!");
        }
    }

    private void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
