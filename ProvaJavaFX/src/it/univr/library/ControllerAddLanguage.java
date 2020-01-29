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

import java.util.Map;

public class ControllerAddLanguage {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button addNewLanguageButton;

    @FXML
    private TextField newLanguageTextField;

    @FXML
    private ListView<Language> languagesListView;
    private ObservableList<Language> languages = FXCollections.observableArrayList();

    private User manager;
    private Map<Book, Integer> cart;

    @FXML
    private void initialize()
    {
        populateLanguages();
        languagesListView.setItems(languages);

        addNewLanguageButton.setOnAction(this::handleAddNewLanguageButton);
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
        controllerHeader.createHeader(manager, headerHBox, cart);
    }


    private void populateLanguages()
    {
        Model DBlanguages = new ModelDatabaseLanguage();
        languages.addAll((DBlanguages.getLanguages()));
    }

    private void handleAddNewLanguageButton(ActionEvent actionEvent)
    {
        Language newLanguage = new Language(newLanguageTextField.getText().trim());

        if(!newLanguage.getName().isEmpty())
        {
            boolean exist = false;
            for (Language language: languages)
            {
                if (language.equals(newLanguage)) {
                    exist = true;
                    break;
                }
            }

            if(!exist)
            {
                //if the authors doesn't already exists so insert into db
                Model DBinsertNewLanguage = new ModelDatabaseLanguage();
                DBinsertNewLanguage.addNewLanguage(newLanguage);

                //change scene
                StageManager addEditBooks = new StageManager();
                addEditBooks.setStageAddEditBooks((Stage) addNewLanguageButton.getScene().getWindow(), manager, cart);
            }
            else
            {
                displayAlert("Language already exists!");
            }
        }
        else
        {
            displayAlert("Language name must be filled!");
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
