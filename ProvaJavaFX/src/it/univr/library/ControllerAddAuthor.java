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
    private ListView<String> authorsListView;
    private ObservableList<String> authors = FXCollections.observableArrayList();

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
        Model DBauthors = new ModelDatabaseBooks();
        authors.addAll((DBauthors.getAuthors()));
    }

    private void handleAddNewAuthorButton(ActionEvent actionEvent)
    {
        String newNameAuthor = nameAuthorTextField.getText();
        String newSurnameAuthor = surnameAuthorTextField.getText();
        String nameSurnameNewAuthor = newNameAuthor + " " + newSurnameAuthor;

        boolean exist = false;
        for (String authors: authors)
        {
            if (authors.toUpperCase().equals(nameSurnameNewAuthor.toUpperCase()))
                exist = true;
        }

        if(!exist)
        {
            //if the authors doesn't already exists so insert into db
            Model DBinsertNewAuthor = new ModelDatabaseBooks();
            DBinsertNewAuthor.addNewAuthor(newNameAuthor, newSurnameAuthor);

            //change scene
            StageManager addEditBooks = new StageManager();
            addEditBooks.setStageAddEditBooks((Stage) addNewAuthorButton.getScene().getWindow(), manager);
        }
        else
        {
            displayAlert("Author already exists!");
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
