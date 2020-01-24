package it.univr.library;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.util.*;

public class ViewBooks implements View
{
    /* TODO: 06/10/2019:  Dividere la funzione in pezzi
    */
    @Override
    public void buildBook(VBox catalogVBox, List<BookGroup> bookGroups, ControllerCatalog controllerCatalog)
    {
        HBox bookHBox;
        ImageView bookImageView;
        GridPane bookGridPane;
        ColumnConstraints bookGridPaneColumn1;
        ColumnConstraints bookGridPaneColumn2;
        Label bookTitleLabel;
        Label bookAuthorsLabel;
        Label bookLanguageLabel;
        Separator separatorLine;
        Set<Author> authors;
        List<Label> bookFormatsPricesLabel;

        for (BookGroup bookGroup: bookGroups)
        {
            /* **** STARTING HBOX **** */
            bookHBox = new HBox();
            bookHBox.setPrefHeight(106.0);
            bookHBox.setPrefWidth(743.0);

            VBox.setMargin(bookHBox, new Insets(4, 4, 4, 4));

            /* **** SETTING IMAGEVIEW PANE **** */
            bookImageView = new ImageView();

            bookImageView.setFitHeight(100);
            bookImageView.setFitWidth(100);
            bookImageView.setPickOnBounds(true);
            bookImageView.setPreserveRatio(true);

            HBox.setMargin(bookImageView, new Insets(4.0, 2.0, 2.0, 2.0));

            /* **** SETTING GRID PANE **** */
            bookGridPane = new GridPane();

            bookGridPane.setAlignment(Pos.CENTER);
            bookGridPane.setPrefHeight(108.0);
            bookGridPane.setPrefWidth(718.0);

            /* **** COLUMNS **** */
            bookGridPaneColumn1 = new ColumnConstraints();
            bookGridPaneColumn1.setPercentWidth(70);
            bookGridPaneColumn1.setHgrow(Priority.SOMETIMES);

            bookGridPaneColumn2 = new ColumnConstraints();
            bookGridPaneColumn2.setPercentWidth(30);
            bookGridPaneColumn2.setHgrow(Priority.SOMETIMES);

            bookGridPane.getColumnConstraints().addAll(bookGridPaneColumn1, bookGridPaneColumn2);

            /* **** ROWS **** */
            //manca il vgrow sometimes
            for (int i = 0; i < 4; i++)
                bookGridPane.getRowConstraints().add(new RowConstraints(27.0, 27.0, Double.MAX_VALUE));

            HBox.setMargin(bookGridPane, new Insets(0, 0, 0, 8));

            /* **** SETTING LABELS **** */
            bookTitleLabel = new Label(bookGroup.getBooks().get(0).getTitle());
            bookTitleLabel.setAlignment(Pos.CENTER);
            bookTitleLabel.setContentDisplay(ContentDisplay.CENTER);
            bookTitleLabel.setFont(new Font("System Bold", 14.0));
            GridPane.setConstraints(bookTitleLabel, 0, 0); // label in column 0, row 0

            bookAuthorsLabel = new Label();
            bookAuthorsLabel.setAlignment(Pos.CENTER);
            bookAuthorsLabel.setContentDisplay(ContentDisplay.CENTER);
            GridPane.setConstraints(bookAuthorsLabel, 0, 1); // label in column 0, row 1

            bookLanguageLabel = new Label("Language: " + bookGroup.getBooks().get(0).getLanguage());
            bookLanguageLabel.setAlignment(Pos.CENTER);
            bookLanguageLabel.setContentDisplay(ContentDisplay.CENTER);
            GridPane.setConstraints(bookLanguageLabel, 0, 3); // label in column 0, row 3

            bookFormatsPricesLabel = new ArrayList<>();
            authors = new TreeSet<>();

            int i = 0;

            for (Book currentBook : bookGroup.getBooks())
            {
                /* **** AUTHORS **** */
                authors.addAll(currentBook.getAuthors());

                /* **** PRICES AND FORMAT **** */
                bookFormatsPricesLabel.add(new Label(currentBook.getPrice() + "€ - " + currentBook.getFormat()));

                // add a row to the gridpane
                if(i >= bookGroup.groupSize())
                    bookGridPane.getRowConstraints().add(new RowConstraints(27.0, 27.0, Double.MAX_VALUE));

                GridPane.setConstraints(bookFormatsPricesLabel.get(i), 1, i);

                /* **** IMAGE **** */
                if(!currentBook.getImagePath().equals("") && currentBook.getImagePath() != null)
                {
                    try
                    {
                        bookImageView.setImage(new Image(currentBook.getImagePath()));
                    }
                    catch (NullPointerException | IllegalArgumentException e)
                    {}
                }

                i++;
            }

            /* **** CHECK IF IMAGE IS EXISTING **** */
            if(bookImageView.getImage() == null)
                bookImageView.setImage(new Image("/images/coverNotAvailable.png"));

            /* **** ADDING FORMATS AND PRICES TO GRIDPANE **** */
            bookGridPane.getChildren().addAll(bookFormatsPricesLabel);

            /* **** SETTING LINE SEPARATOR **** */
            separatorLine = new Separator();
            separatorLine.setPrefWidth(200.0);

            /* **** SETTING HBOXCURSOR **** */
            bookHBox.setCursor(Cursor.HAND);

            /* **** ADDING AUTHORS TO LABEL AND ADDING LABELS TO GRIDPANE**** */
            bookAuthorsLabel.setText("by " + setToString(authors));
            bookGridPane.getChildren().addAll(bookTitleLabel, bookAuthorsLabel, bookLanguageLabel);

            /* **** ADDING IMAGE TO HBOX **** */
            bookHBox.getChildren().add(bookImageView);

            /* *** ADDING GRIDPANE TO HBOX **** */
            bookHBox.getChildren().add(bookGridPane);

            /* **** ADDING HBOX AND LINE TO VBOX **** */
            catalogVBox.getChildren().addAll(bookHBox, separatorLine);

            // Add listener to the Book HBox calling a method in ControllerCatalog and passing the ISBN List
            // Note that we call a method from the same controllerCatalog object (passed as an argument)
            bookHBox.setOnMouseClicked(mouseEvent ->
                    controllerCatalog.changeSceneToSpecificBook(new ArrayList<String>(bookGroup.getBooksISBN())));
        }
    }

    private String setToString(Set<Author> setStr)
    {
        StringBuilder result = new StringBuilder();
        Iterator<Author> iter = setStr.iterator();

        while (iter.hasNext())
        {
            result.append(iter.next());

            if(iter.hasNext())
                result.append(", ");
        }

        return result.toString();
    }
}
