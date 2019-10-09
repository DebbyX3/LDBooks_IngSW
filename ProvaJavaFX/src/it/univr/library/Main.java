package it.univr.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
    Per caricare su github: (si usa prima di tutto git status)
    1) git add nome_file
    2) git commit -m "messaggio che indica la modifica effettuata"
    3) git push per uploadare il file

    Per scaricare (assicurarsi essere sincronizzati con github)
*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*ControllerCatalog catalog = new ControllerCatalog();
        catalog.setSceneCatalog(primaryStage);*/

        Parent root = FXMLLoader.load(getClass().getResource("fxml/catalog.fxml"));

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();

        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/catalog.fxml"));
        Parent root = loader.load();
        ControllerCatalog controller = loader.getController();
        controller.setStage(primaryStage);
        */

        /* metodo1
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/catalog.fxml"));

        ControllerCatalog controller = new ControllerCatalog();   //da chiamare prima di set controller
        loader.setController(controller);

        Parent root = loader.load();
        controller.setRoot(root);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();*/


        /* metodo 2
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/catalog.fxml"));
        loader.setController(new ControllerCatalog());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}



        /*FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        System.out.println(selectedFile);*/