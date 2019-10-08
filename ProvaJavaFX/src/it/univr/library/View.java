package it.univr.library;

import javafx.scene.layout.VBox;

import java.util.ArrayList;

public interface View
{
    public default void buildCatalog(ArrayList<Book> books, VBox catalogVBox)
    {}
}
