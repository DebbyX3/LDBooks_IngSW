package it.univr.library.View;

import it.univr.library.Data.Librocard;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public interface ViewManagerLibrocard
{
    public void buildLibroCard(ArrayList<Librocard> librocards, VBox LibrocardVBox, ScrollPane LibroCardScrollPane);
}
