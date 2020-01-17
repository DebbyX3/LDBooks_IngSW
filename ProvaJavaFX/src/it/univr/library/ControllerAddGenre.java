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
        controllerHeader.createHeader(manager, headerHBox);
    }

    private void populateGenres()
    {
        Model DBgenres = new ModelDatabaseGenres();
        genres.addAll(DBgenres.getGenres());
    }

    private void handleAddNewGenre(ActionEvent actionEvent)
    {

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
                Model DBinsertNewGenre = new ModelDatabaseGenres();
                DBinsertNewGenre.addNewGenre(newGenre.getName());

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewGenreButton.getScene().getWindow(), manager);
            }
            else
            {
                displayAlert("Genre already exists!");
            }
        }
        else
        {
            displayAlert("Genre name must be filled!");
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
