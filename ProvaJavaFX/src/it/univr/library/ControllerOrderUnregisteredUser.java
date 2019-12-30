package it.univr.library;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerOrderUnregisteredUser {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button trackOrderButton;

    @FXML
    private TextField trackingCodeTextField;

    @FXML
    private TextField mailTextField;

    private User user;

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", Pattern.CASE_INSENSITIVE);


    @FXML
    private void initialize()
    {
        trackOrderButton.setOnAction(this::handleTrackOrderButton);
    }


    public void setUser(User user)
    {
        this.user = user;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox);
    }

    private void handleTrackOrderButton(ActionEvent actionEvent)
    {
        Model DBgetOrderUserNotReg = new ModelDatabaseOrder();
        StringBuilder error = new StringBuilder();
        String mailNotRegUser;
        String orderCode;
        ArrayList<Order> order = new ArrayList<>();

        //first check if every field is compiled
        if(!isAnyFieldNullOrEmpty())
        {
            //if everything's fine so take all text from textfields
            mailNotRegUser = mailTextField.getText();
            orderCode = trackingCodeTextField.getText();

            //if the mail is valid
            if(isMailValid(mailNotRegUser, error))
            {
                //take the order from DB
                try
                {
                    order = DBgetOrderUserNotReg.getOrderNotRegisteresUser(mailNotRegUser, orderCode);
                }
                catch (NullPointerException e)
                {
                    displayAlert("There is not Track Code associates to this mail!\n" +
                                    "Check your inputs");
                }

                //and show it
                StageManager orderUnregisteredUserStage = new StageManager();
                orderUnregisteredUserStage.setStageUnregOrderUserView((Stage) trackOrderButton.getScene().getWindow(), user, order);
            }
            else
                displayAlert(error.toString());

        }
        else
            displayAlert("All text field must be filled!");
    }

    private boolean isAnyFieldNullOrEmpty()
    {
        return mailTextField == null || mailTextField.getText().isEmpty()
                || trackOrderButton == null || trackingCodeTextField.getText().isEmpty();
    }

    private boolean isMailValid(String emailStr, StringBuilder error) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        if(!matcher.find())
            error.append("- Mail is not in the right format (abc@mail.com)\n");

        return  error.toString().isEmpty();
    }

    private void displayAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Check your input");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }


}
