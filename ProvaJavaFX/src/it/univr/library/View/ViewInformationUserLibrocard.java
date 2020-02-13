package it.univr.library.View;

import it.univr.library.Librocard;
import it.univr.library.User;
import javafx.scene.text.Text;

public class ViewInformationUserLibrocard implements View {

    @Override
    public void buildLibrocard(User user, Librocard librocard, Text nameSurnameText, Text pointsText, Text issueDateText, Text idText)
    {
        nameSurnameText.setText(user.getName() + " " + user.getSurname());
        pointsText.setText("Points: " + librocard.getTotalPoints());
        issueDateText.setText("Issue Date: " + librocard.LibroCardDate());
        idText.setText("Id: " + librocard.getNumberID());
    }

    public static class Cities
    {

    }
}
