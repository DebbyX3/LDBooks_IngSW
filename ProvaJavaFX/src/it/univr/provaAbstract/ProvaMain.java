package it.univr.provaAbstract;

import it.univr.library.Address;
import it.univr.library.RegisteredClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ProvaMain extends Application {

    protected static final String INITAL_VALUE = "0";

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        final Spinner spinner = new Spinner();

        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000,
                Integer.parseInt(INITAL_VALUE)));
        spinner.setEditable(true);

        EventHandler<KeyEvent> enterKeyEventHandler;

        enterKeyEventHandler = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {

                // handle users "enter key event"
                if (event.getCode() == KeyCode.ENTER) {

                    try {
                        // yes, using exception for control is a bad solution ;-)
                        Integer.parseInt(spinner.getEditor().textProperty().get());
                    }
                    catch (NumberFormatException e) {

                        // show message to user: "only numbers allowed"

                        // reset editor to INITAL_VALUE
                        spinner.getEditor().textProperty().set(INITAL_VALUE);
                    }
                }
            }
        };

        // note: use KeyEvent.KEY_PRESSED, because KeyEvent.KEY_TYPED is to late, spinners
        // SpinnerValueFactory reached new value before key released an SpinnerValueFactory will
        // throw an exception
        spinner.getEditor().addEventHandler(KeyEvent.KEY_PRESSED, enterKeyEventHandler);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        int row = 0;

        grid.add(new Label("Spinner:"), 0, row);
        grid.add(spinner, 1, row);

        Scene scene = new Scene(grid, 350, 300);

        stage.setTitle("Hello Spinner");
        stage.setScene(scene);
        stage.show();
    }
}