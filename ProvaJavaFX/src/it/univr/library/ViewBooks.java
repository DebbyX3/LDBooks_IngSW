package it.univr.library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.math.BigDecimal;
import java.util.*;

public class ViewBooks implements View
{
    @Override
    public void buildCatalog(ArrayList<Book> books, VBox catalogVBox)
    {
        List<Format> formats = new ArrayList<>();
        List<BigDecimal> prices = new ArrayList<>();
        Set<Author> authors = new TreeSet<>();
        String imagePath = null;

        Book originalBook = null;

        if(!books.isEmpty())
            originalBook = books.get(0);

        for (Book currentBook : books)
        {
            //if the book has NOT the same title, NOT a common author and NOT the same lang
            //then, the book is NOT the same, it's a new book
            if(! (currentBook.getTitle().equals(originalBook.getTitle()) &&
                    !Collections.disjoint(currentBook.getAuthors(), originalBook.getAuthors()) && //true if list1 contains at least one element from list2
                    currentBook.getLanguage().equals(originalBook.getLanguage()))) {

                buildBook(catalogVBox, originalBook.getTitle(), authors, formats, prices, imagePath, originalBook.getLanguage());

                formats.clear();
                prices.clear();
                authors.clear();
                imagePath = null;

                originalBook = currentBook;
            }

            //if the book has the same title, at least 1 equals author and same lang
            //then, it's the same book!
            formats.add(currentBook.getFormat());
            prices.add(currentBook.getPrice());
            authors.addAll(currentBook.getAuthors());  //no duplicates

            if(imagePath == null)
                imagePath = currentBook.getImagePath();
        }

        if(!books.isEmpty())
            buildBook(catalogVBox, originalBook.getTitle(), authors, formats, prices, imagePath, originalBook.getLanguage());
    }

    //ci sto ancora pensando
    /*private void buildBook(VBox bookVBox, Book book)
    {

    }*/

    /* TODO: 06/10/2019:  Dividere la funzione in pezzi e sistemare in modo che prenda tutti i libri, vedi commento funzione sopra
    */
    private void buildBook(VBox bookVBox, String title, Set<Author> authors, List<Format> formats, List<BigDecimal> prices, String imagePath, Language language)
    {
        /* **** SETTING HBOX PANE **** */
        HBox bookHBox = new HBox();
        bookHBox.setPrefHeight(106.0);
        bookHBox.setPrefWidth(743.0);

        VBox.setMargin(bookHBox, new Insets(4, 4, 4, 4));

        /* **** SETTING IMAGEVIEW PANE **** */
        ImageView bookImageView;

        try
        {
            //bookImageView = new ImageView(new Image(element.getImagePath()));
            bookImageView = new ImageView(new Image(imagePath));
        }
        catch(NullPointerException | IllegalArgumentException e)
        {
            bookImageView = new ImageView(new Image("/images/coverNotAvailable.png"));
        }

        bookImageView.setFitHeight(100);
        bookImageView.setFitWidth(100);
        bookImageView.setPickOnBounds(true);
        bookImageView.setPreserveRatio(true);

        bookHBox.setMargin(bookImageView, new Insets(4.0, 2.0, 2.0, 2.0));

        /* **** SETTING GRID PANE **** */
        GridPane bookGridPane = new GridPane();

        bookGridPane.setAlignment(Pos.CENTER);
        bookGridPane.setPrefHeight(108.0);
        bookGridPane.setPrefWidth(718.0);

        /* **** COLUMNS **** */
        ColumnConstraints bookGridPaneColumn1 = new ColumnConstraints();
        bookGridPaneColumn1.setPercentWidth(60);
        bookGridPaneColumn1.setHgrow(Priority.SOMETIMES);

        ColumnConstraints bookGridPaneColumn2 = new ColumnConstraints();
        bookGridPaneColumn2.setPercentWidth(40);
        bookGridPaneColumn2.setHgrow(Priority.SOMETIMES);

        bookGridPane.getColumnConstraints().addAll(bookGridPaneColumn1, bookGridPaneColumn2);

        /* **** ROWS **** */
        //manca il vgrow sometimes
        for (int i = 0; i < 4; i++)
            bookGridPane.getRowConstraints().add(new RowConstraints(27.0, 27.0, Double.MAX_VALUE));

        bookHBox.setMargin(bookGridPane, new Insets(0, 0, 0, 8));

        /* **** SETTING LABELS **** */
        //Label bookTitleLabel = new Label(element.getTitle());
        Label bookTitleLabel = new Label(title);
        bookTitleLabel.setAlignment(Pos.CENTER);
        bookTitleLabel.setContentDisplay(ContentDisplay.CENTER);
        bookTitleLabel.setFont(new Font("System Bold", 14.0));
        GridPane.setConstraints(bookTitleLabel, 0, 0); // label in column 0, row 0

        //Label bookAuthorsLabel = new Label("by " + Arrays.toString(element.getAuthors()));
        Label bookAuthorsLabel = new Label("by " + setToString(authors));
        bookAuthorsLabel.setAlignment(Pos.CENTER);
        bookAuthorsLabel.setContentDisplay(ContentDisplay.CENTER);
        GridPane.setConstraints(bookAuthorsLabel, 0, 1); // label in column 0, row 1

        //Label bookLanguageLabel = new Label("Language: " + element.getLanguage());
        Label bookLanguageLabel = new Label("Language: " + language.getName());
        bookLanguageLabel.setAlignment(Pos.CENTER);
        bookLanguageLabel.setContentDisplay(ContentDisplay.CENTER);
        GridPane.setConstraints(bookLanguageLabel, 0, 3); // label in column 0, row 3

        List<Label> bookFormatsPricesLabel = new ArrayList<>();

        for (int i = 0; i < formats.size(); i++) {
            bookFormatsPricesLabel.add(new Label(prices.get(i) + "€ - " + formats.get(i).getName()));

            if (i >= bookGridPane.getRowCount())
                bookGridPane.getRowConstraints().add(new RowConstraints(27.0, 27.0, Double.MAX_VALUE));

            GridPane.setConstraints(bookFormatsPricesLabel.get(i), 1, i);
        }

        bookGridPane.getChildren().addAll(bookFormatsPricesLabel);

        /* **** SETTING LINE SEPARATOR **** */
        Separator separatorLine = new Separator();
        separatorLine.setPrefWidth(200.0);

        /* **** SETTING HBOXCURSOR **** */
        bookHBox.setCursor(Cursor.HAND);

        /* **** ADDING LABELS TO GRIDPANE **** */
        bookGridPane.getChildren().addAll(bookTitleLabel, bookAuthorsLabel, bookLanguageLabel);

        /* **** ADDING IMAGE AND GRIDPANE TO HBOX  **** */
        bookHBox.getChildren().add(bookImageView);
        bookHBox.getChildren().add(bookGridPane);

        /* **** ADDING HBOX AND LINE TO VBOX **** */
        bookVBox.getChildren().addAll(bookHBox, separatorLine);
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
