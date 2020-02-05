package it.univr.library;

import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.spi.CalendarDataProvider;

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