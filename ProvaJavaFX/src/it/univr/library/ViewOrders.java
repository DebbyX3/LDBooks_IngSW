package it.univr.library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewOrders implements View {

    @Override
    public void buildOrders(ArrayList<Order> orders, VBox orderVBox, ScrollPane orderScrollPane)
    {
        GridPane orderGridPane;

        //bring up the scrollpane
        orderScrollPane.setVvalue(orderScrollPane.getVmin());

        for (Order order: orders)
        {
            // create the grid pane for the single order
            orderGridPane = new GridPane();
            orderGridPane.setPrefWidth(884.0);
            orderGridPane.setPrefHeight(464.0);
            VBox.setMargin(orderGridPane, new Insets(10,0,0,0));

            // create the fix external labels such as dateOrder, OrderCode, ecc...
            createExternalLabel(orderGridPane, order);

            //declaration for the next single row for each book
            RowConstraints orderGridPaneRow3;

            for (Book book: order.getBooks())
            {
                //create a new row for the new book
                orderGridPaneRow3 = new RowConstraints();
                orderGridPaneRow3.setVgrow(Priority.SOMETIMES);
                orderGridPaneRow3.setMaxHeight(200);
                orderGridPaneRow3.setPrefHeight(180);
                orderGridPaneRow3.setMinHeight(180);
                orderGridPane.getRowConstraints().add(orderGridPaneRow3);

                //build new book
                buildSingleBookInformation(book, orderGridPane);
            }

            //add new row for the status bar
            RowConstraints orderGridPaneRow4 = new RowConstraints();
            orderGridPaneRow4.setMinHeight(30);
            orderGridPaneRow4.setPrefHeight(114);
            orderGridPaneRow4.setMaxHeight(194.39);

            orderGridPane.getRowConstraints().add(orderGridPaneRow4);

            createLastRowOrderInformation(order, orderGridPane);
            orderVBox.getChildren().add(orderGridPane);

            //separator for the new order or for last order
            Separator line = new Separator();
            line.setPrefHeight(200);
            VBox.setMargin(line, new Insets(0,10 , 0, 0));
            orderVBox.getChildren().add(line);

        }
    }

    private void createExternalLabel(GridPane orderGridPane, Order order)
    {
        /* **** COLUMNS **** */
        ColumnConstraints orderGridPaneColumn1 = new ColumnConstraints();
        orderGridPaneColumn1.setHgrow(Priority.SOMETIMES);
        orderGridPaneColumn1.setMinWidth(10.0);
        orderGridPaneColumn1.setPrefWidth(213.40);
        orderGridPaneColumn1.setMaxWidth(292.60);
        orderGridPaneColumn1.setPercentWidth(25.0);

        ColumnConstraints orderGridPaneColumn2 = new ColumnConstraints();
        orderGridPaneColumn2.setHgrow(Priority.SOMETIMES);
        orderGridPaneColumn2.setMinWidth(10.0);
        orderGridPaneColumn2.setMaxWidth(385.59);
        orderGridPaneColumn2.setPrefWidth(385.59);
        orderGridPaneColumn2.setPercentWidth(48.0);

        ColumnConstraints orderGridPaneColumn3 = new ColumnConstraints();
        orderGridPaneColumn3.setHgrow(Priority.SOMETIMES);
        orderGridPaneColumn3.setMinWidth(10.0);
        orderGridPaneColumn3.setMaxWidth(284.80);
        orderGridPaneColumn3.setPrefWidth(284.80);
        orderGridPaneColumn3.setPercentWidth(28.0);

        orderGridPane.getColumnConstraints().addAll(orderGridPaneColumn1, orderGridPaneColumn2, orderGridPaneColumn3);

        /* *** BASIC ROWS *** */
        RowConstraints orderGridPaneRow1 = new RowConstraints();
        orderGridPaneRow1.setVgrow(Priority.SOMETIMES);
        orderGridPaneRow1.setMinHeight(25.0);
        orderGridPaneRow1.setPrefHeight(25.0);
        orderGridPaneRow1.setMaxHeight(45.60);

        RowConstraints orderGridPaneRow2 = new RowConstraints();
        orderGridPaneRow2.setVgrow(Priority.SOMETIMES);
        orderGridPaneRow2.setMinHeight(25.0);
        orderGridPaneRow2.setPrefHeight(25.0);
        orderGridPaneRow2.setMaxHeight(45.60);

        orderGridPane.getRowConstraints().addAll(orderGridPaneRow1, orderGridPaneRow2);

        /* *** ADD BASIC LABELS *** */
        HBox orderMadeHbox = new HBox();
        orderMadeHbox.setAlignment(Pos.TOP_LEFT);
        orderMadeHbox.setPrefHeight(24);
        GridPane.setMargin(orderMadeHbox, new Insets(0,0,0,10));

        Label orderMadeFixLabel = new Label("dateLabel");
        orderMadeFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        orderMadeFixLabel.setPrefHeight(17);
        orderMadeFixLabel.setText("Order made:");
        orderMadeFixLabel.setAlignment(Pos.CENTER_LEFT);
        orderMadeFixLabel.setContentDisplay(ContentDisplay.LEFT);
        orderMadeFixLabel.setFont(new Font("System Bold", 12.0));
        HBox.setMargin(orderMadeFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right

        Label orderMadeLabel = new Label("dateOrder");
        orderMadeLabel.setMinWidth(Region.USE_PREF_SIZE);
        orderMadeLabel.setPrefHeight(17);
        orderMadeLabel.setText(order.UnixDateToString());
        orderMadeLabel.setAlignment(Pos.CENTER_LEFT);
        orderMadeLabel.setContentDisplay(ContentDisplay.LEFT);
        orderMadeLabel.setFont(new Font("System", 12.0));

        orderMadeHbox.getChildren().addAll(orderMadeFixLabel, orderMadeLabel);
        GridPane.setConstraints(orderMadeHbox, 0,0);

        HBox sentToHbox = new HBox();
        sentToHbox.setAlignment(Pos.TOP_LEFT);

        Label sentToFixLabel = new Label("sentToFixLabel");
        sentToFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        sentToFixLabel.setPrefHeight(17);
        sentToFixLabel.setText("Sent to:");
        sentToFixLabel.setAlignment(Pos.CENTER_LEFT);
        sentToFixLabel.setContentDisplay(ContentDisplay.LEFT);
        sentToFixLabel.setFont(new Font("System Bold", 12.0));
        HBox.setMargin(sentToFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right

        Label sentToLabel = new Label("sentToLabel");
        sentToLabel.setMinWidth(Region.USE_PREF_SIZE);
        sentToLabel.setPrefHeight(17);
        sentToLabel.setText(order.getAddress().toString());
        sentToLabel.setAlignment(Pos.CENTER_LEFT);
        sentToLabel.setContentDisplay(ContentDisplay.LEFT);
        sentToLabel.setFont(new Font("System", 12.0));

        sentToHbox.getChildren().addAll(sentToFixLabel, sentToLabel);
        GridPane.setConstraints(sentToHbox, 1,0);

        HBox orderCodeHbox = new HBox();
        orderCodeHbox.setAlignment(Pos.TOP_LEFT);
        GridPane.setMargin(orderCodeHbox, new Insets(0,0,0,10));

        Label orderCodeFixLabel = new Label("orderCodeFixLabel");
        orderCodeFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        orderCodeFixLabel.setPrefHeight(17);
        orderCodeFixLabel.setText("Order Code:");
        orderCodeFixLabel.setAlignment(Pos.CENTER_LEFT);
        orderCodeFixLabel.setContentDisplay(ContentDisplay.LEFT);
        orderCodeFixLabel.setFont(new Font("System Bold", 12.0));
        HBox.setMargin(orderCodeFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right

        Label orderCodeLabel = new Label("orderCodeLabel");
        orderCodeLabel.setMinWidth(Region.USE_PREF_SIZE);
        orderCodeLabel.setPrefHeight(17);
        orderCodeLabel.setText(order.getCode());
        orderCodeLabel.setAlignment(Pos.CENTER_LEFT);
        orderCodeLabel.setContentDisplay(ContentDisplay.LEFT);
        orderCodeLabel.setFont(new Font("System", 12.0));

        orderCodeHbox.getChildren().addAll(orderCodeFixLabel, orderCodeLabel);
        GridPane.setConstraints(orderCodeHbox, 0,1);

        HBox mailOrderHBox = new HBox();

        mailOrderHBox.setAlignment(Pos.TOP_LEFT);

        Label mailOrderFixLabel = new Label("mailOrderFixLabel");
        mailOrderFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        mailOrderFixLabel.setPrefHeight(17);
        mailOrderFixLabel.setText("Mail:");
        mailOrderFixLabel.setAlignment(Pos.CENTER_LEFT);
        mailOrderFixLabel.setContentDisplay(ContentDisplay.LEFT);
        mailOrderFixLabel.setFont(new Font("System Bold", 12.0));
        HBox.setMargin(mailOrderFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right



        Label mailOrderLabel = new Label("mailOrderLabel");
        mailOrderLabel.setMinWidth(Region.USE_PREF_SIZE);
        mailOrderLabel.setPrefHeight(17);
        if(order.getEmailNotRegisteredUser() != null)
        {
            mailOrderLabel.setText(order.getEmailNotRegisteredUser());
        }
        else
            mailOrderLabel.setText(order.getEmailRegisteredUser());
        mailOrderLabel.setAlignment(Pos.CENTER_LEFT);
        mailOrderLabel.setContentDisplay(ContentDisplay.LEFT);
        mailOrderLabel.setFont(new Font("System", 12.0));

        mailOrderHBox.getChildren().addAll(mailOrderFixLabel, mailOrderLabel);
        GridPane.setConstraints(mailOrderHBox, 1,1);

        HBox totalPriceHbox = new HBox();
        totalPriceHbox.setAlignment(Pos.TOP_LEFT);

        Label totalPriceFixLabel = new Label("totalPriceFixLabel");
        totalPriceFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        totalPriceFixLabel.setPrefHeight(17);
        totalPriceFixLabel.setText("Total Price:");
        totalPriceFixLabel.setAlignment(Pos.CENTER_LEFT);
        totalPriceFixLabel.setContentDisplay(ContentDisplay.LEFT);
        totalPriceFixLabel.setFont(new Font("System Bold", 12.0));
        HBox.setMargin(totalPriceFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right


        Label totalPriceLabel = new Label("totalPriceLabel");
        totalPriceLabel.setMinWidth(Region.USE_PREF_SIZE);
        totalPriceLabel.setPrefHeight(17);
        totalPriceLabel.setText(order.getTotalPrice().toString() + "€");
        totalPriceLabel.setAlignment(Pos.CENTER_LEFT);
        totalPriceLabel.setContentDisplay(ContentDisplay.LEFT);
        totalPriceLabel.setFont(new Font("System", 12.0));

        totalPriceHbox.getChildren().addAll(totalPriceFixLabel, totalPriceLabel);
        GridPane.setConstraints(totalPriceHbox, 2,0);

        HBox balancePointsHbox = new HBox();
        balancePointsHbox.setAlignment(Pos.TOP_LEFT);


        Label balancePointsFixLabel = new Label("balancePointsFixLabel");
        balancePointsFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        balancePointsFixLabel.setPrefHeight(17);
        balancePointsFixLabel.setText("Balance Points:");
        balancePointsFixLabel.setAlignment(Pos.CENTER_LEFT);
        balancePointsFixLabel.setContentDisplay(ContentDisplay.LEFT);
        balancePointsFixLabel.setFont(new Font("System Bold", 12.0));
        HBox.setMargin(balancePointsFixLabel, new Insets(0, 5, 0, 0)); //int top, int left, int bottom, int right


        Label balancePointsLabel = new Label("balancePointsLabel");
        balancePointsLabel.setMinWidth(Region.USE_PREF_SIZE);
        balancePointsLabel.setPrefHeight(17);
        balancePointsLabel.setText(Integer.toString(order.getBalancePoints()));
        balancePointsLabel.setAlignment(Pos.CENTER_LEFT);
        balancePointsLabel.setContentDisplay(ContentDisplay.LEFT);
        balancePointsLabel.setFont(new Font("System", 12.0));

        balancePointsHbox.getChildren().addAll(balancePointsFixLabel, balancePointsLabel);
        GridPane.setConstraints(balancePointsHbox, 2,1);

        /* *** ADD ALL THE LABELS TO GRID PANE */

        orderGridPane.getChildren().addAll(orderMadeHbox,sentToHbox,orderCodeHbox,mailOrderHBox,totalPriceHbox,balancePointsHbox);

    }

    private void buildSingleBookInformation(Book book, GridPane orderGridPane)
    {
        addImageBook(orderGridPane, book);

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
        titleLabel.setPrefWidth(400);
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
        authorLabel.setPrefWidth(400);
        authorLabel.setPrefHeight(17);

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
        priceLabel.setPrefWidth(400);
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
        librocardPointsLabel.setPrefWidth(400);
        librocardPointsLabel.setPrefHeight(17);
        librocardPointsLabel.setText(book.getPoints().toString());
        librocardPointsLabel.setAlignment(Pos.CENTER_LEFT);
        librocardPointsLabel.setContentDisplay(ContentDisplay.LEFT);
        librocardPointsLabel.setFont(new Font("System", 12.0));

        librocardPointsHbox.getChildren().addAll(librocardPointsFixLabel,librocardPointsLabel);
        bookInformationVbox.getChildren().add(librocardPointsHbox);

        GridPane.setConstraints(bookInformationVbox, 1, orderGridPane.getRowCount()-1);
        orderGridPane.getChildren().add(bookInformationVbox);
    }

    private void createLastRowOrderInformation(Order order, GridPane orderGridPane)
    {
        HBox statusFixHbox = new HBox();
        statusFixHbox.setAlignment(Pos.CENTER_RIGHT);
        GridPane.setMargin(statusFixHbox, new Insets(0, 15 , 0, 0));
        statusFixHbox.setPrefWidth(200);
        statusFixHbox.setPrefHeight(100);

        Label statusOrderFixLabel = new Label("statusOrderFixLabel");
        statusOrderFixLabel.setMinWidth(Region.USE_PREF_SIZE);
        statusOrderFixLabel.setPrefWidth(206);
        statusOrderFixLabel.setPrefHeight(101);
        statusOrderFixLabel.setText("Status:");
        statusOrderFixLabel.setAlignment(Pos.CENTER);
        statusOrderFixLabel.setContentDisplay(ContentDisplay.LEFT);
        statusOrderFixLabel.setFont(new Font("System Bold", 12.0));

        statusFixHbox.getChildren().add(statusOrderFixLabel);
        orderGridPane.getChildren().add(statusFixHbox);
        GridPane.setConstraints(statusFixHbox,0,orderGridPane.getRowCount()-1);

        ProgressBar progressBarOrder = new ProgressBar();
        progressBarOrder.setStyle("-fx-accent: orange");
        progressBarOrder.setMinWidth(Region.USE_COMPUTED_SIZE);
        progressBarOrder.setPrefWidth(415);
        progressBarOrder.setPrefHeight(18);

        if(order.getStatus().equals("In progress"))
            progressBarOrder.setProgress(0.1);
        else if(order.getStatus().equals("Send"))
            progressBarOrder.setProgress(0.6);
        else
            progressBarOrder.setProgress(1);

        orderGridPane.getChildren().add(progressBarOrder);
        GridPane.setConstraints(progressBarOrder, 1, orderGridPane.getRowCount()-1);

        HBox statusHbox = new HBox();
        statusHbox.setAlignment(Pos.CENTER);
        GridPane.setMargin(statusHbox, new Insets(0,  0, 0, 15));
        statusHbox.setPrefWidth(200);
        statusHbox.setPrefHeight(100);

        Label statusOrderLabel = new Label("statusOrderLabel");
        statusOrderLabel.setMinWidth(Region.USE_PREF_SIZE);
        statusOrderLabel.setPrefWidth(283);
        statusOrderLabel.setPrefHeight(17);
        statusOrderLabel.setText(order.getStatus());
        statusOrderLabel.setAlignment(Pos.CENTER_LEFT);
        statusOrderLabel.setContentDisplay(ContentDisplay.LEFT);
        statusOrderLabel.setFont(new Font("System Bold", 12.0));

        statusHbox.getChildren().add(statusOrderLabel);
        orderGridPane.getChildren().add(statusHbox);
        GridPane.setConstraints(statusHbox,2, orderGridPane.getRowCount()-1);
    }

    private void addImageBook(GridPane orderGridPane, Book book)
    {
        /* *** VBOX Image *** */
        VBox imageVbox = new VBox();
        imageVbox.setAlignment(Pos.CENTER);
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

        GridPane.setConstraints(imageVbox, 0,orderGridPane.getRowCount()-1);
        orderGridPane.getChildren().add(imageVbox);
    }
}
