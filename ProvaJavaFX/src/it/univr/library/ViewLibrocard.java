package it.univr.library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class ViewLibrocard implements View {
    public void buildLibroCard(ArrayList<Librocard> librocards, VBox LibrocardVBox, ScrollPane LibroCardScrollPane) {

        GridPane libroCardGridPane;

        //bring up the scrollpane
        LibroCardScrollPane.setVvalue(LibroCardScrollPane.getVmin());

        for (Librocard librocard : librocards) {
            // create the grid pane for the single order
            libroCardGridPane = new GridPane();
            libroCardGridPane.setPrefWidth(884.0);
            libroCardGridPane.setPrefHeight(464.0);
            VBox.setMargin(libroCardGridPane, new Insets(10, 0, 0, 0));

            /* **** COLUMNS **** */
            ColumnConstraints libroCardGridPaneColumn1 = new ColumnConstraints();
            libroCardGridPaneColumn1.setHgrow(Priority.SOMETIMES);
            libroCardGridPaneColumn1.setMinWidth(10.0);
            libroCardGridPaneColumn1.setPrefWidth(213.40);
            libroCardGridPaneColumn1.setMaxWidth(292.60);
            libroCardGridPaneColumn1.setPercentWidth(25.0);

            libroCardGridPane.getColumnConstraints().add(libroCardGridPaneColumn1);

            /* *** BASIC ROWS *** */
            RowConstraints libroCardGridPaneRow1 = new RowConstraints();
            libroCardGridPaneRow1.setVgrow(Priority.SOMETIMES);
            libroCardGridPaneRow1.setMinHeight(25.0);
            libroCardGridPaneRow1.setPrefHeight(25.0);
            libroCardGridPaneRow1.setMaxHeight(45.60);

            RowConstraints libroCardGridPaneRow2 = new RowConstraints();
            libroCardGridPaneRow2.setVgrow(Priority.SOMETIMES);
            libroCardGridPaneRow2.setMinHeight(25.0);
            libroCardGridPaneRow2.setPrefHeight(25.0);
            libroCardGridPaneRow2.setMaxHeight(45.60);

            RowConstraints libroCardGridPaneRow3 = new RowConstraints();
            libroCardGridPaneRow3.setVgrow(Priority.SOMETIMES);
            libroCardGridPaneRow3.setMinHeight(25.0);
            libroCardGridPaneRow3.setPrefHeight(25.0);
            libroCardGridPaneRow3.setMaxHeight(45.60);

            RowConstraints libroCardGridPaneRow4 = new RowConstraints();
            libroCardGridPaneRow4.setVgrow(Priority.SOMETIMES);
            libroCardGridPaneRow4.setMinHeight(25.0);
            libroCardGridPaneRow4.setPrefHeight(25.0);
            libroCardGridPaneRow4.setMaxHeight(45.60);


            libroCardGridPane.getRowConstraints().addAll(libroCardGridPaneRow1, libroCardGridPaneRow2, libroCardGridPaneRow3, libroCardGridPaneRow4);

            /* *** ADD BASIC LABELS *** */

            //**** ID ****//
            HBox libroCardIdUser = new HBox();
            libroCardIdUser.setAlignment(Pos.TOP_LEFT);
            libroCardIdUser.setPrefWidth(223);
            libroCardIdUser.setPrefHeight(24);
            GridPane.setMargin(libroCardIdUser, new Insets(0,0,0,10));

            Label userIdFixLabel = new Label("FixId");
            userIdFixLabel.setMinWidth(Region.USE_PREF_SIZE);
            userIdFixLabel.setText("ID User: ");
            userIdFixLabel.setAlignment(Pos.CENTER_LEFT);
            userIdFixLabel.setContentDisplay(ContentDisplay.LEFT);
            userIdFixLabel.setFont(new Font("System Bold", 12.0));

            Label userIDLabel = new Label("ID");
            userIDLabel.setMinWidth(Region.USE_PREF_SIZE);
            userIDLabel.setText(librocard.getNumberID());
            userIDLabel.setAlignment(Pos.CENTER_LEFT);
            userIDLabel.setContentDisplay(ContentDisplay.LEFT);
            userIDLabel.setFont(new Font("System", 12.0));

            libroCardIdUser.getChildren().addAll(userIdFixLabel, userIDLabel);
            GridPane.setConstraints(libroCardIdUser, 0,0);


            //**** MAIL ****//
            HBox libroCardMailUser = new HBox();
            libroCardMailUser.setAlignment(Pos.TOP_LEFT);
            libroCardMailUser.setPrefWidth(223);
            libroCardMailUser.setPrefHeight(24);
            GridPane.setMargin(libroCardMailUser, new Insets(0,0,0,10));

            Label userMailFixLabel = new Label("FixMail");
            userMailFixLabel.setMinWidth(Region.USE_PREF_SIZE);
            userMailFixLabel.setText("Mail User: ");
            userMailFixLabel.setAlignment(Pos.CENTER_LEFT);
            userMailFixLabel.setContentDisplay(ContentDisplay.LEFT);
            userMailFixLabel.setFont(new Font("System Bold", 12.0));

            Label userMailLabel = new Label("mail");
            userMailLabel.setMinWidth(Region.USE_PREF_SIZE);
            userMailLabel.setText(librocard.getEmail());
            userMailLabel.setAlignment(Pos.CENTER_LEFT);
            userMailLabel.setContentDisplay(ContentDisplay.LEFT);
            userMailLabel.setFont(new Font("System", 12.0));

            libroCardMailUser.getChildren().addAll(userMailFixLabel, userMailLabel);
            GridPane.setConstraints(libroCardMailUser, 0,1);


            //**** TOTAL POINTS ****//
            HBox libroCardTotalPoints = new HBox();
            libroCardTotalPoints.setAlignment(Pos.TOP_LEFT);
            libroCardTotalPoints.setPrefWidth(223);
            libroCardTotalPoints.setPrefHeight(24);
            GridPane.setMargin(libroCardTotalPoints, new Insets(0,0,0,10));

            Label userTotalPointsFixLabel = new Label("FixTotalPoints");
            userTotalPointsFixLabel.setMinWidth(Region.USE_PREF_SIZE);
            userTotalPointsFixLabel.setText("Total Points LibroCard: ");
            userTotalPointsFixLabel.setAlignment(Pos.CENTER_LEFT);
            userTotalPointsFixLabel.setContentDisplay(ContentDisplay.LEFT);
            userTotalPointsFixLabel.setFont(new Font("System Bold", 12.0));

            Label userTotalPointsLabel = new Label("totalPoints");
            userTotalPointsLabel.setMinWidth(Region.USE_PREF_SIZE);
            userTotalPointsLabel.setPrefWidth(127);
            userTotalPointsLabel.setPrefHeight(17);
            userTotalPointsLabel.setText(librocard.getTotalPoints().toString());
            userTotalPointsLabel.setAlignment(Pos.CENTER_LEFT);
            userTotalPointsLabel.setContentDisplay(ContentDisplay.LEFT);
            userTotalPointsLabel.setFont(new Font("System", 12.0));

            libroCardTotalPoints.getChildren().addAll(userTotalPointsFixLabel, userTotalPointsLabel);
            GridPane.setConstraints(libroCardTotalPoints, 0,2);

            //**** ISSUE DATE ****//
            HBox libroCardIssueDate = new HBox();
            libroCardIssueDate.setAlignment(Pos.TOP_LEFT);
            libroCardIssueDate.setPrefWidth(223);
            libroCardIssueDate.setPrefHeight(24);
            GridPane.setMargin(libroCardIssueDate, new Insets(0,0,0,10));

            Label issueDateFixLabel = new Label("FixIssueDate");
            issueDateFixLabel.setMinWidth(Region.USE_PREF_SIZE);
            issueDateFixLabel.setText("Issue Date LibroCard: ");
            issueDateFixLabel.setAlignment(Pos.CENTER_LEFT);
            issueDateFixLabel.setContentDisplay(ContentDisplay.LEFT);
            issueDateFixLabel.setFont(new Font("System Bold", 12.0));

            Label issueDateLabel = new Label("totalPoints");
            issueDateLabel.setMinWidth(Region.USE_PREF_SIZE);
            issueDateLabel.setPrefWidth(127);
            issueDateLabel.setPrefHeight(17);
            issueDateLabel.setText(librocard.LibroCardDate());
            issueDateLabel.setAlignment(Pos.CENTER_LEFT);
            issueDateLabel.setContentDisplay(ContentDisplay.LEFT);
            issueDateLabel.setFont(new Font("System", 12.0));

            libroCardIssueDate.getChildren().addAll(issueDateFixLabel, issueDateLabel);
            GridPane.setConstraints(libroCardIssueDate, 0,3);

            /* *** ADD ALL THE LABELS TO GRID PANE */
            libroCardGridPane.getChildren().addAll(libroCardIdUser,libroCardMailUser,libroCardTotalPoints,libroCardIssueDate);
            LibrocardVBox.getChildren().add(libroCardGridPane);

            //separator for the new order or for last order
            Separator line = new Separator();
            line.setPrefHeight(200);
            VBox.setMargin(line, new Insets(0,10 , 0, 0));
            LibrocardVBox.getChildren().add(line);


        }
    }
}