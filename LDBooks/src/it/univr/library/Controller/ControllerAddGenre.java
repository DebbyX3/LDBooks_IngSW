package it.univr.library.Controller;

import it.univr.library.Book;
import it.univr.library.Genre;
import it.univr.library.Model.ModelDatabaseGenres;
import it.univr.library.Model.ModelGenres;
import it.univr.library.StageManager;
import it.univr.library.User;
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

import java.util.Map;

public class ControllerAddGenre {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button addNewGenreButton;

    @FXML
    private TextField newGenreTextField;

    @FXML
    private ListView<Genre> genresListView;
    private ObservableList<Genre> genres = FXCollections.observableArrayList();

    private User manager;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        populateGenres();
        genresListView.setItems(genres);

        addNewGenreButton.setOnAction(this::handleAddNewGenre);
    }

    public void setManager(User manager)
    {
        this.manager = manager;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(manager, headerHBox, cart);
    }

    private void populateGenres()
    {
        ModelGenres DBgenres = new ModelDatabaseGenres();
        genres.addAll(DBgenres.getGenres());
    }

    private void handleAddNewGenre(ActionEvent actionEvent)
    {
        ControllerAlert alerts = new ControllerAlert();
        Genre newGenre = new Genre(newGenreTextField.getText().trim());

        if (!newGenre.getName().isEmpty())
        {
            boolean exist = false;

            for (Genre genre: genres)
            {
                if (newGenre.getName().toUpperCase().equals(genre.getName().toUpperCase())) {
                    exist = true;
                    break;
                }
            }

            if(!exist)
            {
                //if the authors doesn't already exists so insert into db
                ModelGenres DBinsertNewGenre = new ModelDatabaseGenres();
                DBinsertNewGenre.addNewGenre(newGenre.getName());

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewGenreButton.getScene().getWindow(), manager, cart);
            }
            else
            {
                alerts.displayAlert("Genre already exists!");
            }
        }
        else
        {
            alerts.displayAlert("Genre name must be filled!");
        }

    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }
}
