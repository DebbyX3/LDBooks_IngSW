package it.univr.library;

import javafx.scene.text.Text;

public class ViewInformationsUserLibrocard implements View {

    @Override
    public void buildLibrocard(User user, Librocard librocard, Text nameSurnameText, Text pointsText, Text issueDateText, Text idText)
    {
        nameSurnameText.setText(user.getName() + " " + user.getSurname());
        pointsText.setText("Points: " + librocard.getTotalPoints());
        issueDateText.setText("Issue Date: " + librocard.getDateFromUnixTime());
        idText.setText("Id: " + librocard.getNumberID());
    }

    public static class Cities
    {

    }
}
