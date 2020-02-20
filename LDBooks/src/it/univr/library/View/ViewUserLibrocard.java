package it.univr.library.View;

import it.univr.library.Librocard;
import it.univr.library.User;
import javafx.scene.text.Text;

public interface ViewUserLibrocard
{
    public void buildLibrocard(User user, Librocard librocard, Text nameSurnameText, Text pointsText, Text issueDateText, Text idText);
}
