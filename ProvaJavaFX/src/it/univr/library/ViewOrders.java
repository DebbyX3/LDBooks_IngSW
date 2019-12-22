package it.univr.library;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class ViewOrders implements View {
    public void buildOrders(ArrayList<Order> orders,Label dateLabel,Label addressLabel,Label totalPriceLabel, Label orderLabel,
                            Label titleLabel, Label authorLabel, Label publisherLabel, Label ISBNLabel, Label priceLabel,
                            Label librocardLabel, Label statusLabel, ProgressBar progressBar, ImageView bookImageView,
                            GridPane singleOrderGridPane)
    {

        for (Order o: orders)
        {
            dateLabel.setText(o.UnixDateToString());
            addressLabel.setText(o.getAddress().toString());
            totalPriceLabel.setText(o.getTotalPrice().toString());
            orderLabel.setText(o.getCode());
            for (Book book: o.getBooks())
            {
                //TODO add a row to gripane
                singleOrderGridPane.addRow(1, new Label("prova"));
                titleLabel.setText(book.getTitle());
                for (String author: book.getAuthors())
                {
                    authorLabel.setText(author);
                }
                publisherLabel.setText(book.getPublishingHouse());
                ISBNLabel.setText(book.getISBN());
                priceLabel.setText(book.getPrice().toString() + "€");
                librocardLabel.setText(book.getPoints().toString());
                if(book.getImagePath() != null && !book.getImagePath().equals(""))
                    bookImageView.setImage(new Image(book.getImagePath()));
                else
                    bookImageView.setImage(new Image("/images/coverNotAvailable.png"));
            }

            statusLabel.setText(o.getStatus());
            if(statusLabel.getText().equals("In elaborazione"))
                progressBar.setProgress(10.0);
            else if(statusLabel.getText().equals("spedito"))
                progressBar.setProgress(60.0);
            else
                progressBar.setProgress(100);
        }

    }
}
