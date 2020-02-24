package it.univr.library.View;

import it.univr.library.*;
import it.univr.library.Controller.ControllerCatalog;
import it.univr.library.Controller.ControllerSpecificBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class ViewFXBooks implements ViewBooks
{
    /* TODO: 06/10/2019:  Dividere la funzione in pezzi
    */
    @Override
    public void buildBookForCatalog(VBox catalogVBox, List<BookGroup> bookGroups, ControllerCatalog controllerCatalog)
    {
        HBox bookHBox;
        HBox imageHBox;
        ImageView bookImageView;
        GridPane bookGridPane;
        ColumnConstraints bookGridPaneColumn1;
        ColumnConstraints bookGridPaneColumn2;
        Label bookTitleLabel;
        Label bookAuthorsLabel;
        Label bookLanguageLabel;
        Label bookGenreLabel;
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
            imageHBox = new HBox();
            imageHBox.setAlignment(Pos.CENTER);

            bookImageView = new ImageView();

            bookImageView.setFitHeight(115);
            bookImageView.setFitWidth(115);
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
            for (int i = 0; i < 5; i++)
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

            bookGenreLabel = new Label("Genre: " + bookGroup.getBooks().get(0).getGenre());
            bookGenreLabel.setAlignment(Pos.CENTER);
            bookGenreLabel.setContentDisplay(ContentDisplay.CENTER);
            GridPane.setConstraints(bookGenreLabel, 0, 4); // label in column 0, row 3

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
                if(!currentBook.getImagePath().trim().equals("") && currentBook.getImagePath() != null)
                {
                    try {
                        bookImageView.setImage(new Image(currentBook.getImagePath()));
                    }
                    catch (NullPointerException | IllegalArgumentException e) {}
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

            /* **** ADDING AUTHORS TO LABEL AND ADDING LABELS TO GRIDPANE **** */
            bookAuthorsLabel.setText("by " + setToString(authors));
            bookGridPane.getChildren().addAll(bookTitleLabel, bookAuthorsLabel, bookLanguageLabel, bookGenreLabel);

            /* **** ADDING IMAGE TO IMAGEHBOX **** */
            imageHBox.getChildren().add(bookImageView);

            /* **** ADDING IMAGEHBOX TO HBOX **** */
            bookHBox.getChildren().add(imageHBox);

            /* *** ADDING GRIDPANE TO HBOX **** */
            bookHBox.getChildren().add(bookGridPane);

            /* **** ADDING HBOX AND LINE TO VBOX **** */
            catalogVBox.getChildren().addAll(bookHBox, separatorLine);

            // Add listener to the Book HBox calling a method in ControllerCatalog and passing the ISBN List
            // Note that we call a method from the same controllerCatalog object (passed as an argument)
            bookHBox.setOnMouseClicked(mouseEvent ->
                    controllerCatalog.changeSceneToSpecificBook(new BookGroup(bookGroup)));
        }
    }

    @Override
    public void buildBookForSpecificBook(VBox bookInfoVBox, Label titleLabel, Label languageLabel, BookGroup bookGroup, ControllerSpecificBook controllerSpecificBook, User user)
    {
        buildTitleAndLanguageBookForSpecificBook(titleLabel, languageLabel, bookGroup);
        buildBookInformation(bookInfoVBox, bookGroup, controllerSpecificBook, user);
    }

    private void buildTitleAndLanguageBookForSpecificBook(Label titleLabel, Label languageLabel,BookGroup bookGroup)
    {
        titleLabel.setText(bookGroup.getBooks().get(0).getTitle());
        languageLabel.setText("Language: " + bookGroup.getBooks().get(0).getLanguage());
    }

    private void buildBookInformation(VBox bookInfoVBox, BookGroup bookGroup, ControllerSpecificBook controllerSpecificBook, User user)
    {
        //build hbox

        HBox singleBookHBox;

        ImageView bookImageView;

        VBox bookSpecificationVBox;
        Label ISBNLabel;
        Label authorsLabel;
        Label genreLabel;
        Label publicationYearLabel;
        Label publishingHouseLabel;
        Label pagesLabel;
        Label librocardPointsLabel;
        Accordion descriptionAccordion;
        TitledPane descriptionTitledPane;
        TextArea descriptionTextArea;

        VBox buyingOptionsVBox;
        Label formatLabel;
        FlowPane buyFlowPane;
        Button cartButton;
        ImageView cartImageView;
        Label priceLabel;
        Label quantityLabel;
        ComboBox quantityComboBox;
        ObservableList<Genre> quantityComboBoxData;// = FXCollections.observableArrayList();
        Label availableQuantityLabel;

        Separator lineSeparator;

        for (Book currentBook: bookGroup.getBooks())
        {
            /* **** SETTING HBOX **** */
            singleBookHBox = new HBox();
            singleBookHBox.setPrefHeight(202.0);
            VBox.setMargin(singleBookHBox, new Insets(8, 8, 8, 8)); //Insets(top, right, bottom, left)

            /* **** SETTING IMAGE **** */
            bookImageView = new ImageView();
            bookImageView.setFitHeight(150.0);
            bookImageView.setFitWidth(117.0);
            bookImageView.setPickOnBounds(true);
            bookImageView.setPreserveRatio(true);

            if(!currentBook.getImagePath().trim().equals("") && currentBook.getImagePath() != null)
            {
                try {
                    bookImageView.setImage(new Image(currentBook.getImagePath()));
                }
                catch (NullPointerException | IllegalArgumentException e){
                    bookImageView.setImage(new Image("/images/coverNotAvailable.png"));
                }
            }
            else
                bookImageView.setImage(new Image("/images/coverNotAvailable.png"));

            /* **** SETTING BOOK SPECIFICATIONS VBOX **** */
            bookSpecificationVBox = new VBox();
            bookSpecificationVBox.prefHeight(182.0);
            HBox.setMargin(bookSpecificationVBox, new Insets(0, 8.0, 0, 15.0)); //Insets(top, right, bottom, left)

            /* **** SETTING ISBN LABEL **** */
            ISBNLabel = new Label("ISBN: " + currentBook.getISBN());
            ISBNLabel.prefHeight(18.0);
            ISBNLabel.prefWidth(456.0);
            ISBNLabel.setLayoutX(10.0);
            ISBNLabel.setLayoutY(10.0);
            VBox.setMargin(ISBNLabel, new Insets(0, 0, 5.0, 0)); //Insets(top, right, bottom, left)

            /* **** SETTING AUTHORS LABEL **** */
            authorsLabel = new Label("Authors: " + setToString(new TreeSet<Author>(currentBook.getAuthors()))); // sorted!
            authorsLabel.prefHeight(18.0);
            //authorsLabel.prefWidth(456.0);
            authorsLabel.setLayoutX(10.0);
            authorsLabel.setLayoutY(10.0);
            VBox.setMargin(authorsLabel, new Insets(0, 0, 5.0, 0)); //Insets(top, right, bottom, left)

            /* **** SETTING GENRE LABEL **** */
            genreLabel = new Label("Genre: " + currentBook.getGenre());
            genreLabel.prefHeight(18.0);
            //genreLabel.prefWidth(456.0);
            genreLabel.setLayoutX(10.0);
            genreLabel.setLayoutY(10.0);
            VBox.setMargin(genreLabel, new Insets(0, 0, 5.0, 0)); //Insets(top, right, bottom, left)

            /* **** SETTING PUBLICATION YEAR LABEL **** */
            publicationYearLabel = new Label("Publication Year: " + currentBook.getPublicationYear());
            publicationYearLabel.prefHeight(18.0);
            //publicationYearLabel.prefWidth(456.0);
            publicationYearLabel.setLayoutX(10.0);
            publicationYearLabel.setLayoutY(10.0);
            VBox.setMargin(publicationYearLabel, new Insets(0, 0, 5.0, 0)); //Insets(top, right, bottom, left)

            /* **** SETTING PUBLISHING HOUSE LABEL **** */
            publishingHouseLabel = new Label("Publishing House: " + currentBook.getPublishingHouse());
            publishingHouseLabel.prefHeight(18.0);
            //publishingHouseLabel.prefWidth(456.0);
            publishingHouseLabel.setLayoutX(10.0);
            publishingHouseLabel.setLayoutY(10.0);
            VBox.setMargin(publishingHouseLabel, new Insets(0, 0, 5.0, 0)); //Insets(top, right, bottom, left)

            /* **** SETTING PAGES LABEL **** */
            pagesLabel = new Label("Pages: " + currentBook.getPages());
            pagesLabel.prefHeight(18.0);
            //pagesLabel.prefWidth(456.0);
            pagesLabel.setLayoutX(10.0);
            pagesLabel.setLayoutY(10.0);
            VBox.setMargin(pagesLabel, new Insets(0, 0, 5.0, 0)); //Insets(top, right, bottom, left)

            /* **** SETTING LIBROCARD POINTS LABEL **** */
            librocardPointsLabel = new Label("Librocards Point: " + currentBook.getPoints());
            librocardPointsLabel.prefHeight(18.0);
            //librocardPointsLabel.prefWidth(456.0);
            librocardPointsLabel.setLayoutX(10.0);
            librocardPointsLabel.setLayoutY(10.0);
            VBox.setMargin(librocardPointsLabel, new Insets(0, 0, 5.0, 0)); //Insets(top, right, bottom, left)

            /* **** SETTING DESCRIPTION ACCORDION **** */
            descriptionTextArea = new TextArea(currentBook.getDescription());
            descriptionTextArea.setWrapText(true);

            descriptionAccordion = new Accordion();
            descriptionTitledPane = new TitledPane("Description", descriptionTextArea);

            descriptionTitledPane.setAnimated(true);
            descriptionTitledPane.prefHeight(127.0);
           // descriptionTitledPane.prefWidth(456.0);

            /* **** SETTING VBOX BUYING OPTIONS **** */
            buyingOptionsVBox = new VBox();

            buyingOptionsVBox.setAlignment(Pos.CENTER);
            buyingOptionsVBox.setPrefHeight(234.0);

            /* **** SETTING FORMAT LABEL **** */
            formatLabel = new Label(currentBook.getFormat().toString());
            formatLabel.setAlignment(Pos.CENTER);
            formatLabel.setContentDisplay(ContentDisplay.CENTER);
            formatLabel.setTextAlignment(TextAlignment.CENTER);
            formatLabel.setPrefHeight(20.0);
            formatLabel.setPrefWidth(315.0);
            formatLabel.setFont(new Font(14.0));

            /* **** SETTING BUY FLOWPANE **** */
            buyFlowPane = new FlowPane();
            buyFlowPane.setAlignment(Pos.CENTER);
            buyFlowPane.setPrefHeight(82.0);
            buyFlowPane.setPrefWidth(329.0);

            /* **** SET CART BUTTON **** */
            cartButton = new Button();
            cartButton.setPrefWidth(88.0);
            cartButton.setPrefHeight(47.0);
            cartButton.setStyle("-fx-background-color: #ffa939;");
            cartButton.setCursor(Cursor.HAND);
            FlowPane.setMargin(cartButton, new Insets(0, 0, 0, 10)); //Insets(top, right, bottom, left)

            cartImageView = new ImageView();
            cartImageView.setFitWidth(48.0);
            cartImageView.setFitHeight(32.0);
            cartImageView.setPickOnBounds(true);
            cartImageView.setPreserveRatio(true);

            try {
                cartImageView.setImage(new Image("/images/cart.png"));
                cartButton.setGraphic(cartImageView);
            }
            catch (NullPointerException | IllegalArgumentException e){
                cartButton.setText("Buy it!");
            }

            // Disable button if the user is a manager
            if(user instanceof Manager)
                cartButton.setDisable(true);

            /* **** SETTING PRICE LABEL **** */
            priceLabel = new Label(currentBook.getPrice().toString() + "€");
            priceLabel.setAlignment(Pos.CENTER);
            priceLabel.setContentDisplay(ContentDisplay.CENTER);
            priceLabel.setTextAlignment(TextAlignment.CENTER);
            //priceLabel.setPrefWidth(94.0);
            priceLabel.setPrefHeight(34.0);
            priceLabel.setFont(new Font(23.0));
            FlowPane.setMargin(priceLabel, new Insets(0, 5, 0, 5)); //Insets(top, right, bottom, left)

            /* **** SETTING QUANTITY LABEL **** */
            quantityLabel = new Label("Qty ");
            FlowPane.setMargin(quantityLabel, new Insets(0, 0, 0, 10)); //Insets(top, right, bottom, left)

            /* **** SETTING QUANTITY COMBOBOX **** */
            quantityComboBox = new ComboBox();
            ObservableList<Integer> quantity = FXCollections.observableArrayList();

            for(int i = 1; i <= currentBook.getMaxQuantity(); i++)
               quantity.add(i);

            quantityComboBox.setItems(quantity);
            if(currentBook.getMaxQuantity() > 0)
                quantityComboBox.setValue(1);
            else
                quantityComboBox.setValue(0);

            ComboBox finalQuantityComboBox = quantityComboBox;
            Button finalCartButton = cartButton;
            cartButton.setOnAction(actionEvent -> controllerSpecificBook.handleAddBookToCart(new Book(currentBook), (Integer) finalQuantityComboBox.getValue(), finalCartButton));

            /* **** SETTING AVAILABLE QUANTITY LABEL **** */
            availableQuantityLabel = new Label("Available quantity: " + currentBook.getMaxQuantity());
            availableQuantityLabel.setAlignment(Pos.CENTER);
            availableQuantityLabel.setContentDisplay(ContentDisplay.CENTER);
            availableQuantityLabel.setTextAlignment(TextAlignment.CENTER);
            availableQuantityLabel.setPrefHeight(18.0);
            availableQuantityLabel.setPrefWidth(315.0);

            /* **** SETTING SEPARATOR LINE **** */
            lineSeparator = new Separator();
            lineSeparator.prefWidth(200.0);

            /* **** ADDING TITLEDPANE TO ACCORDION **** */
            descriptionAccordion.getPanes().add(descriptionTitledPane);

            /* **** ADDING LABELS AND ACCORDION TO BOOK SPECIFICATION VBOX **** */
            bookSpecificationVBox.getChildren().addAll(ISBNLabel, authorsLabel, genreLabel, publicationYearLabel,
                    publishingHouseLabel, pagesLabel, librocardPointsLabel, descriptionAccordion);

            /* **** ADDING CART BUTTON, PRICE, QTY LABEL AND COMBOBOX TO BUY FLOWPANE **** */
            buyFlowPane.getChildren().addAll(cartButton, priceLabel, quantityLabel, quantityComboBox);

            /* *** ADDING FORMAT LABEL, BUY FLOWPANE AND AVAIL QTY LABEL **** */
            buyingOptionsVBox.getChildren().addAll(formatLabel, buyFlowPane, availableQuantityLabel);

            /* **** ADDING IMAGE, BOOK SPECIFICATIONS AND BUYING OPTIONS TO SINGLE BOOK HBOX **** */
            singleBookHBox.getChildren().addAll(bookImageView, bookSpecificationVBox, buyingOptionsVBox);

            /* **** ADD A SINGLE BOOK AND A SEPARATOR LINE TO THE BOOKS INFORMATION VBOX **** */
            bookInfoVBox.getChildren().addAll(singleBookHBox, lineSeparator);
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
