package it.univr.library;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class ViewTopPart implements View
{
    public void createLoginSignupButton(HBox rightHeaderHbox)
    {
        /*
        <Button fx:id="loginSignUpButton" alignment="CENTER" setcontentDisplay="CENTER" mnemonicParsing="false" prefHeight="32.0" prefWidth="124.0" style="-fx-background-color: #ffa939;" text="Login - Sign Up" textAlignment="CENTER" wrapText="true">
                           <font>
                              <Font size="13.0" />
                           </font>
                           <cursor>
                              <Cursor fx:constant="HAND" />
                           </cursor>
                           <HBox.margin>
                              <Insets right="10.0" />
                           </HBox.margin>
                        </Button>
        */
      
      Button loginSignUpButton = new Button();

      loginSignUpButton.setId("loginSignUpButton");
      loginSignUpButton.setAlignment(Pos.CENTER);
      loginSignUpButton.setContentDisplay(ContentDisplay.CENTER);
      loginSignUpButton.setPrefHeight(32.0);
      loginSignUpButton.setPrefWidth(124.0);
      loginSignUpButton.setStyle("-fx-background-color: #ffa939");
      loginSignUpButton.setText("Login - Sign Up");
      loginSignUpButton.setTextAlignment(TextAlignment.CENTER);
      loginSignUpButton.setWrapText(true);

    }


}
