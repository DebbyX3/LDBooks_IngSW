package it.univr.library;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ViewCart implements View {
    @Override
    public void buildCart(Map<Book, Integer> cart, VBox cartVBox, ScrollPane cartScrollPane, ControllerCart controllerCart, Label subTotalLabel, Label shippingCostLabel, Label TotalPriceLabel, Label libroCardPointsLabel)
    {

        GridPane cartGridPane;
        //bring up the scrollpane
        cartScrollPane.setVvalue(cartScrollPane.getVmin());
        double subtotal = 0;
        double shippingCost;
        boolean onlyAudioBook = true;

        for (Book books: cart.keySet())
        {
            if (!books.getFormat().getName().equals("Digital Edition") && !books.getFormat().getName().equals("Audiobook"))
            {
                onlyAudioBook = false;
                break;
            }
        }

        if(cart.isEmpty() || onlyAudioBook)
            shippingCost = 0;
        else
            shippingCost = 5.99;

        double totalPrice = 0;
        double libroCardPoints = 0;

        for (Book book: cart.keySet())
        {

            cartGridPane = new GridPane();
            cartGridPane.setPrefWidth(695);
            cartGridPane.setPrefHeight(229);
            cartVBox.setPadding(new Insets(40, 0, 0, 0));
            VBox.setMargin(cartGridPane, new Insets(15,0,15,0));

            createCartforSingleBook(cartVBox,cartGridPane,book, controllerCart,cart);

            //separator for the new order or for last order
            Separator line = new Separator();
            line.setPrefHeight(200);
            //VBox.setMargin(line, new Insets(0,10 , 0, 0));
            cartVBox.getChildren().add(line);
            subtotal += book.getPrice().doubleValue() * cart.get(book);
            libroCardPoints += book.getPoints().doubleValue() * cart.get(book);
        }

        totalPrice = subtotal + shippingCost;

        subTotalLabel.setText(String.format("%.2f €",subtotal));
        shippingCostLabel.setText(shippingCost + "€");
        TotalPriceLabel.setText(String.format("%.2f €",totalPrice));
        libroCardPointsLabel.setText(String.valueOf(libroCardPoints));


    }

    private void createCartforSingleBook(VBox cartVBox, GridPane cartGridPane, Book book, ControllerCart controllerCart, Map<Book,Integer> cart)
    {
        /* **** COLUMNS **** */
        ColumnConstraints cartGridPaneColumn1 = new ColumnConstraints();
        cartGridPaneColumn1.setHgrow(Priority.SOMETIMES);
        cartGridPaneColumn1.setMinWidth(10.0);
        cartGridPaneColumn1.setPrefWidth(174.60);
        cartGridPaneColumn1.setMaxWidth(226.80);


        ColumnConstraints cartGridPaneColumn2 = new ColumnConstraints();
        cartGridPaneColumn2.setHgrow(Priority.SOMETIMES);
        cartGridPaneColumn2.setMinWidth(10.0);
        cartGridPaneColumn2.setPrefWidth(287.80);
        cartGridPaneColumn2.setMaxWidth(385.60);


        ColumnConstraints cartGridPaneColumn3 = new ColumnConstraints();
        cartGridPaneColumn3.setHgrow(Priority.SOMETIMES);
        cartGridPaneColumn3.setMinWidth(10.0);
        cartGridPaneColumn3.setPrefWidth(232.20);
        cartGridPaneColumn3.setMaxWidth(232.20);


        cartGridPane.getColumnConstraints().addAll(cartGridPaneColumn1, cartGridPaneColumn2, cartGridPaneColumn3);

        /* *** BASIC ROWS *** */
        RowConstraints cartGridPaneRow1 = new RowConstraints();
        cartGridPaneRow1.setVgrow(Priority.SOMETIMES);
        cartGridPaneRow1.setPrefHeight(Region.USE_COMPUTED_SIZE);



        cartGridPane.getRowConstraints().addAll(cartGridPaneRow1);

        // ADD IMAGE

        /* *** VBOX Image *** */
        VBox imageVbox = new VBox();
        imageVbox.setAlignment(Pos.CENTER);
        imageVbox.setPrefWidth(222);
        imageVbox.setPrefHeight(182);
        ImageView bookImageView;

        try
        {
            bookImageView = new ImageView(new Image(book.getImagePath()));
        }
        catch(NullPointerException | IllegalArgumentException e)
        {
            bookImageView = new ImageView(new Image("/images/coverNotAvailable.png"));
        }

        bookImageView.setFitWidth(115);
        bookImageView.setFitHeight(140);
        bookImageView.setPreserveRatio(true);

        imageVbox.getChildren().addAll(bookImageView);

        GridPane.setConstraints(imageVbox, 0,0);
        cartGridPane.getChildren().add(imageVbox);

        // BOOK INFORMATION
        /* *** BOOK INFORMATION *** */
        VBox bookInformationVbox = new VBox();
        bookInformationVbox.setAlignment(Pos.CENTER_LEFT);

        /* *** TITLE *** */
        HBox titleHbox = new HBox();
        VBox.setMargin(titleHbox, new Insets(0,0,7,0));

        Label titleFixLabel = new Label("titleFixLabel");
        titleFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        titleFixLabel.setPrefHeight(17);
        titleFixLabel.setText("Title:");
        HBox.setMargin(titleFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right
        titleFixLabel.setAlignment(Pos.CENTER_LEFT);
        titleFixLabel.setContentDisplay(ContentDisplay.LEFT);
        titleFixLabel.setFont(new Font("System Bold", 12.0));

        Label titleLabel = new Label("titleLabel");
        titleLabel.setMinWidth(Region.USE_PREF_SIZE);
        titleLabel.setPrefHeight(17);
        titleLabel.setPrefWidth(252);
        titleLabel.setMaxWidth(Region.USE_PREF_SIZE);
        titleLabel.setText(book.getTitle());
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setContentDisplay(ContentDisplay.LEFT);
        titleLabel.setFont(new Font("System", 12.0));

        titleHbox.getChildren().addAll(titleFixLabel,titleLabel);
        bookInformationVbox.getChildren().add(titleHbox);

        /* *** AUTHOR *** */
        HBox authorsHbox = new HBox();
        VBox.setMargin(authorsHbox, new Insets(0,0,7,0));

        Label authorFixLabel = new Label("authorFixLabel");
        authorFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        authorFixLabel.setPrefHeight(17);
        authorFixLabel.setText("Author:");
        HBox.setMargin(authorFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right
        authorFixLabel.setAlignment(Pos.CENTER_LEFT);
        authorFixLabel.setContentDisplay(ContentDisplay.LEFT);
        authorFixLabel.setFont(new Font("System Bold", 12.0));

        Label authorLabel = new Label("authorLabel");
        authorLabel.setMinWidth(Region.USE_PREF_SIZE);
        authorLabel.setPrefWidth(241);
        authorLabel.setPrefHeight(17);
        authorLabel.setMaxWidth(Region.USE_PREF_SIZE);

        int authorsCurrentElementIndex = 0;
        int authorsListSize = book.getAuthors().size();
        StringBuilder authorsString = new StringBuilder();

        for (Author author: book.getAuthors())
        {
            authorsCurrentElementIndex++;
            authorsString.append(author.getNameSurname());

            if(authorsCurrentElementIndex != authorsListSize)
                authorsString.append(", ");
        }

        authorLabel.setText(authorsString.toString());

        authorLabel.setAlignment(Pos.CENTER_LEFT);
        authorLabel.setContentDisplay(ContentDisplay.LEFT);
        authorLabel.setFont(new Font("System", 12.0));

        authorsHbox.getChildren().addAll(authorFixLabel,authorLabel);
        bookInformationVbox.getChildren().add(authorsHbox);

        /* *** PUBLISHER *** */
        HBox publisherHbox = new HBox();
        VBox.setMargin(publisherHbox, new Insets(0,0,7,0));

        Label publisherFixLabel = new Label("publisherFixLabel");
        publisherFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        publisherFixLabel.setPrefHeight(17);
        publisherFixLabel.setText("Publisher:");
        HBox.setMargin(publisherFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right
        publisherFixLabel.setAlignment(Pos.CENTER_LEFT);
        publisherFixLabel.setContentDisplay(ContentDisplay.LEFT);
        publisherFixLabel.setFont(new Font("System Bold", 12.0));

        Label publisherLabel = new Label("publisherLabel");
        publisherLabel.setMinWidth(Region.USE_PREF_SIZE);
        publisherLabel.setPrefWidth(400);
        publisherLabel.setPrefHeight(17);
        publisherLabel.setText(book.getPublishingHouse().getName());
        publisherLabel.setAlignment(Pos.CENTER_LEFT);
        publisherLabel.setContentDisplay(ContentDisplay.LEFT);
        publisherLabel.setFont(new Font("System", 12.0));

        publisherHbox.getChildren().addAll(publisherFixLabel,publisherLabel);
        bookInformationVbox.getChildren().add(publisherHbox);

        /* *** ISBN *** */
        HBox isbnHbox = new HBox();
        VBox.setMargin(isbnHbox, new Insets(0,0,7,0));

        Label isbnFixLabel = new Label("isbnFixLabel");
        isbnFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        isbnFixLabel.setPrefHeight(17);
        isbnFixLabel.setText("ISBN:");
        HBox.setMargin(isbnFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right
        isbnFixLabel.setAlignment(Pos.CENTER_LEFT);
        isbnFixLabel.setContentDisplay(ContentDisplay.LEFT);
        isbnFixLabel.setFont(new Font("System Bold", 12.0));

        Label isbnLabel = new Label("isbnLabel");
        isbnLabel.setMinWidth(Region.USE_PREF_SIZE);
        isbnLabel.setPrefWidth(400);
        isbnLabel.setPrefHeight(17);
        isbnLabel.setText(book.getISBN());
        isbnLabel.setAlignment(Pos.CENTER_LEFT);
        isbnLabel.setContentDisplay(ContentDisplay.LEFT);
        isbnLabel.setFont(new Font("System", 12.0));

        isbnHbox.getChildren().addAll(isbnFixLabel,isbnLabel);
        bookInformationVbox.getChildren().add(isbnHbox);

        /* *** PRICE *** */
        HBox priceHbox = new HBox();
        VBox.setMargin(priceHbox, new Insets(0,0,7,0));

        Label priceFixLabel = new Label("priceFixLabel");
        priceFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        priceFixLabel.setPrefHeight(17);
        priceFixLabel.setText("Price:");
        HBox.setMargin(priceFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right
        priceFixLabel.setAlignment(Pos.CENTER_LEFT);
        priceFixLabel.setContentDisplay(ContentDisplay.LEFT);
        priceFixLabel.setFont(new Font("System Bold", 12.0));

        Label priceLabel = new Label("priceLabel");
        priceLabel.setMinWidth(Region.USE_PREF_SIZE);
        priceLabel.setPrefHeight(17);
        priceLabel.setText(book.getPrice().toString() + "€");
        priceLabel.setAlignment(Pos.CENTER_LEFT);
        priceLabel.setContentDisplay(ContentDisplay.LEFT);
        priceLabel.setFont(new Font("System", 12.0));

        priceHbox.getChildren().addAll(priceFixLabel, priceLabel);
        bookInformationVbox.getChildren().add(priceHbox);

        /* *** LIBROCARD POINTS *** */
        HBox librocardPointsHbox = new HBox();
        VBox.setMargin(librocardPointsHbox, new Insets(0,0,0,0));

        Label librocardPointsFixLabel = new Label("librocardPointsFixLabel");
        librocardPointsFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        librocardPointsFixLabel.setPrefHeight(17);
        librocardPointsFixLabel.setText("LibroCard points:");
        HBox.setMargin(librocardPointsFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right
        librocardPointsFixLabel.setAlignment(Pos.CENTER_LEFT);
        librocardPointsFixLabel.setContentDisplay(ContentDisplay.LEFT);
        librocardPointsFixLabel.setFont(new Font("System Bold", 12.0));

        Label librocardPointsLabel = new Label("librocardPointsLabel");
        librocardPointsLabel.setMinWidth(Region.USE_PREF_SIZE);
        librocardPointsLabel.setPrefHeight(17);
        librocardPointsLabel.setText(book.getPoints().toString());
        librocardPointsLabel.setAlignment(Pos.CENTER_LEFT);
        librocardPointsLabel.setContentDisplay(ContentDisplay.LEFT);
        librocardPointsLabel.setFont(new Font("System", 12.0));

        librocardPointsHbox.getChildren().addAll(librocardPointsFixLabel,librocardPointsLabel);
        bookInformationVbox.getChildren().add(librocardPointsHbox);

        GridPane.setConstraints(bookInformationVbox, 1, 0);
        cartGridPane.getChildren().add(bookInformationVbox);

        // FINAL INFORMATION VBOX
        VBox bookFinalInformationVbox = new VBox();
        bookFinalInformationVbox.setAlignment(Pos.CENTER);
        //VBox.setMargin(bookFinalInformationVbox, new Insets(0,0,0,10));


        //FORMAT
        HBox formatHbox = new HBox();
        formatHbox.setAlignment(Pos.CENTER_LEFT);
        formatHbox.setPrefWidth(222);
        formatHbox.setPrefHeight(115);

        Label formatLabel = new Label("format");
        formatLabel.setMinWidth(Region.USE_PREF_SIZE);
        formatLabel.setPrefWidth(223);
        formatLabel.setPrefHeight(83);
        formatLabel.setText(book.getFormat().toString());
        formatLabel.setAlignment(Pos.CENTER_LEFT);
        formatLabel.setContentDisplay(ContentDisplay.LEFT);
        formatLabel.setFont(new Font("System Bold", 14.0));

        formatHbox.getChildren().add(formatLabel);

        //QUANTITY
        HBox quantityHbox = new HBox();
        quantityHbox.setPrefWidth(222);
        quantityHbox.setPrefHeight(17);
        VBox.setMargin(quantityHbox, new Insets(5,0,0,0));

        Label quantityFixLabel = new Label("quantityFixLabel");
        quantityFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        quantityFixLabel.setPrefHeight(17);
        quantityFixLabel.setText("Quantity:");
        quantityFixLabel.setAlignment(Pos.CENTER_LEFT);
        quantityFixLabel.setContentDisplay(ContentDisplay.LEFT);
        quantityFixLabel.setFont(new Font("System", 12.0));
        HBox.setMargin(quantityFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right

        Label quantityLabel = new Label("quantityLabel");
        quantityLabel.setMinWidth(Region.USE_PREF_SIZE);
        quantityLabel.setPrefWidth(175);
        quantityLabel.setPrefHeight(17);
        quantityLabel.setText(String.valueOf(cart.get(book)));
        quantityLabel.setAlignment(Pos.CENTER_LEFT);
        quantityLabel.setContentDisplay(ContentDisplay.LEFT);
        quantityLabel.setFont(new Font("System", 12.0));

        quantityHbox.getChildren().addAll(quantityFixLabel,quantityLabel);

        //LIBROCARD POINTS
        HBox totalLibroCardPointsHbox = new HBox();
        totalLibroCardPointsHbox.setPrefWidth(222);
        totalLibroCardPointsHbox.setPrefHeight(17);
        VBox.setMargin(totalLibroCardPointsHbox, new Insets(5,0,0,0));

        Label totalLibroCardPointsFixLabel = new Label("totalLibroCardPointsFixLabel");
        totalLibroCardPointsFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        totalLibroCardPointsFixLabel.setPrefHeight(17);
        totalLibroCardPointsFixLabel.setText("Total LibroCard Points:");
        totalLibroCardPointsFixLabel.setAlignment(Pos.CENTER_LEFT);
        totalLibroCardPointsFixLabel.setContentDisplay(ContentDisplay.LEFT);
        totalLibroCardPointsFixLabel.setFont(new Font("System", 12.0));
        HBox.setMargin(totalLibroCardPointsFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right

        Label totalLibroCardPointsLabel = new Label("totalLibroCardPointsLabel");
        totalLibroCardPointsLabel.setMinWidth(Region.USE_PREF_SIZE);
        totalLibroCardPointsLabel.setPrefWidth(175);
        totalLibroCardPointsLabel.setPrefHeight(17);
        double totalLibroCardPoints = cart.get(book) * book.getPoints();
        totalLibroCardPointsLabel.setText(String.format("%.2f",totalLibroCardPoints));
        totalLibroCardPointsLabel.setAlignment(Pos.CENTER_LEFT);
        totalLibroCardPointsLabel.setContentDisplay(ContentDisplay.LEFT);
        totalLibroCardPointsLabel.setFont(new Font("System", 12.0));

        totalLibroCardPointsHbox.getChildren().addAll(totalLibroCardPointsFixLabel,totalLibroCardPointsLabel);

        //PRICE
        HBox totalPricesHbox = new HBox();
        totalPricesHbox.setPrefWidth(222);
        totalPricesHbox.setPrefHeight(17);
        VBox.setMargin(totalPricesHbox, new Insets(5,0,0,0));

        Label totalPriceFixLabel = new Label("totalPriceFixLabel");
        totalPriceFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        totalPriceFixLabel.setPrefHeight(17);
        totalPriceFixLabel.setText("Total Price:");
        totalPriceFixLabel.setAlignment(Pos.CENTER_LEFT);
        totalPriceFixLabel.setContentDisplay(ContentDisplay.LEFT);
        totalPriceFixLabel.setFont(new Font("System ", 12.0));
        HBox.setMargin(totalPriceFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right

        Label totalPriceLabel = new Label("totalPriceLabel");
        totalPriceLabel.setMinWidth(Region.USE_PREF_SIZE);
        totalPriceLabel.setPrefWidth(175);
        totalPriceLabel.setPrefHeight(17);
        double totalPriceL = cart.get(book) * book.getPrice().doubleValue();
        totalPriceLabel.setText(String.format("%.2f €",totalPriceL));
        totalPriceLabel.setAlignment(Pos.CENTER_LEFT);
        totalPriceLabel.setContentDisplay(ContentDisplay.LEFT);
        totalPriceLabel.setFont(new Font("System", 12.0));

        totalPricesHbox.getChildren().addAll(totalPriceFixLabel,totalPriceLabel);

        //REMOVE BUTTON
        HBox removeButtonHbox = new HBox();
        removeButtonHbox.setAlignment(Pos.CENTER_LEFT);
        removeButtonHbox.setMinWidth(222);
        removeButtonHbox.setPrefHeight(106);
        VBox.setMargin(removeButtonHbox, new Insets(5,0,0,0));

        Button removeButton = new Button();
        removeButton.setPrefHeight(25);
        removeButton.setPrefWidth(122);
        removeButton.setText("Remove from cart");
        removeButton.setFont(new Font("System Italic", 12.0));
        StageManager cartView = new StageManager();
        removeButton.setOnAction(actionEvent -> controllerCart.handleRemoveBookFromCart(new Book(book), removeButton));

        removeButtonHbox.getChildren().add(removeButton);

        bookFinalInformationVbox.getChildren().addAll(formatHbox, quantityHbox,totalLibroCardPointsHbox,totalPricesHbox,removeButtonHbox);
        GridPane.setConstraints(bookFinalInformationVbox, 2, 0);
        cartGridPane.getChildren().add(bookFinalInformationVbox);
        cartVBox.getChildren().add(cartGridPane);


    }
}

