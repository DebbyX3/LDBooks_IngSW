package it.univr.library;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller
{
    public void checkTopPart(User user, HBox rightHeaderHbox)
    {
        ViewTopPart createHeader = new ViewTopPart();

        if(user == null)
        {
            createHeader.createLoginSignupButton(rightHeaderHbox);
            createHeader.createOrderStatus(rightHeaderHbox);
        }
        else
        {
            VBox userInfo = createHeader.createUserHyperlink(user, rightHeaderHbox);
            createHeader.createLogOutButton(userInfo);
        }
    }

}
