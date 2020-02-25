package it.univr.library.Controller;

import it.univr.library.Data.Author;
import it.univr.library.Data.Book;
import it.univr.library.Model.ModelAuthor;
import it.univr.library.Model.ModelDatabaseAuthor;
import it.univr.library.Utils.StageManager;
import it.univr.library.Data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Map;

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
    private Map<Book, Integer> cart;

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

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox,cart);
    }

    private void populateAuthors()
    {
        ModelAuthor DBauthors = new ModelDatabaseAuthor();
        authors.addAll((DBauthors.getAuthors()));
    }

    private void handleAddNewAuthorButton(ActionEvent actionEvent)
    {
        ControllerAlert alerts = new ControllerAlert();

        if(!nameAuthorTextField.getText().equals("") || !surnameAuthorTextField.getText().equals(""))
        {
            ModelAuthor DBinsertNewAuthor = new ModelDatabaseAuthor();
            DBinsertNewAuthor.addNewAuthor(nameAuthorTextField.getText().trim(), surnameAuthorTextField.getText().trim());

            //change scene
            StageManager addEditBooks = new StageManager();
            addEditBooks.setStageAddEditBooks((Stage) addNewAuthorButton.getScene().getWindow(), manager, cart);
        }
        else
        {
            alerts.displayAlert("Fill at least one field between name or surname!");
        }
    }
}
