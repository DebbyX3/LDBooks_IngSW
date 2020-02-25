package it.univr.library.View;

import it.univr.library.Data.Librocard;
import it.univr.library.Data.User;
import javafx.scene.text.Text;

public class ViewFXUserLibrocard implements ViewUserLibrocard
{
    @Override
    public void buildLibrocard(User user, Librocard librocard, Text nameSurnameText, Text pointsText, Text issueDateText, Text idText)
    {
        nameSurnameText.setText(user.getName() + " " + user.getSurname());
        pointsText.setText("Points: " + librocard.getTotalPoints());
        issueDateText.setText("Issue Date: " + librocard.LibroCardDate());
        idText.setText("Id: " + librocard.getNumberID());
    }
}
