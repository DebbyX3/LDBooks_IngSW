package it.univr.library.Controller;

import it.univr.library.*;
import it.univr.library.Model.ModelBooks;
import it.univr.library.Model.ModelDatabaseBooks;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class ControllerBooks
{
    public boolean isAnyFieldEmptyOrNotValid(String ISBN, String title, String description, String publicationYear, String pages,
                                             String librocardPoints, String availableQuantity, List authorsToLinkToBook, ListView authorListView, String price)
    {
        ControllerAlert alerts = new ControllerAlert();

        StringBuilder error = new StringBuilder();
        Optional<ButtonType> result;

        if(ISBN.trim().equals(""))
            error.append("- ISBN must be filled!\n");
        else
            if(!isISBNvalid(ISBN.trim()) && !isASINvalid(ISBN.trim()))
                error.append("- ISBN has no format 10 or 13 or ASIN!\n");

        if(title.trim().isEmpty())
            error.append("- Title must be filled!\n");

        if(description.trim().isEmpty())
            error.append("- Description must be filled!\n");
        else
            if(isNumerical(description.trim()))
                error.append("- Description must be at least alphabetic\n");

        if(publicationYear.trim().isEmpty())
            error.append("- Publication year must be filled\n");

        if(!isNumerical(publicationYear.trim()))
        {
            error.append("- Publication year must be numerical and/or positive\n");
        }
        else
        {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if(Integer.parseInt(publicationYear.trim()) < 1000 || Integer.parseInt(publicationYear.trim()) > year)
                error.append(String.format("- Publication year must be between 1000 and %d\n",year));
        }

        if(pages.trim().isEmpty())
            error.append("- Number of pages must be filled\n");
        else if(!isNumerical(pages.trim()))
            error.append("- Number of pages must be numerical and/or positive\n");

        if(librocardPoints.trim().isEmpty())
            error.append("- LibroCard Points must be filled\n");
        else if(!isNumerical(librocardPoints.trim()))
            error.append("- Librocard Points must be numerical and/or positive\n");

        //this 'if' should never happen, but let's keep it for stability
        if(     !isNumerical(availableQuantity.trim()) ||
                availableQuantity.trim().equals(""))
        {
            error.append("- Available Quantity must be numerical and/or positive\n");
        }
        else
        {
            if (Integer.parseInt(availableQuantity.trim()) > 100) {
                result = alerts.displayConfirmation("The quantity available is greater 100, are you sure?\n").showAndWait();

                if (result.get() != ButtonType.OK) {
                    alerts.displayAlert("Select a new available quantity");
                    return true;
                }
            }
        }

        //check if I've at least an author for the book
        if(authorsToLinkToBook.isEmpty() && authorListView.getItems().isEmpty())
            error.append("- Book needs at least one author\n");

        if(price.trim().isEmpty())
            error.append("- Price must be filled\n");

        if(!isNumerical(price.trim()))
        {
            error.append("- Price must be numerical and/or positive\n");
        }
        else
        {
            if(Float.parseFloat(price.trim()) > 1000)
            {
                result = alerts.displayConfirmation("The price that you insert is greater than 1000€, are you sure?\n").showAndWait();
                if(result.get() != ButtonType.OK)
                {
                    alerts.displayAlert("Select a new price!");
                    return true;
                }
            }
        }

        if(!error.toString().isEmpty())
            alerts.displayAlert(error.toString());

        return !error.toString().isEmpty();
    }

    public Book fetchBookInformation(String ISBN, String title, List authors, String description, Format format, Genre genre,
                                     Language language, PublishingHouse publishingHouse, String pages, String librocardPoints,
                                     String availableQuantity, String price, String publicationYear, String imagePath)
    {
        Book book = new Book();

        book.setISBN(ISBN.trim());
        book.setTitle(title.trim());
        book.setAuthors(authors);
        book.setDescription(description.trim());
        book.setFormat(format);
        book.setGenre(genre);
        book.setLanguage(language);
        book.setPublishingHouse(publishingHouse);
        book.setPages(Integer.parseInt(pages.trim()));
        book.setPoints(Integer.parseInt(librocardPoints.trim()));
        book.setMaxQuantity(Integer.parseInt(availableQuantity));
        book.setPrice(new BigDecimal(price.trim()));
        book.setPublicationYear(Integer.parseInt(publicationYear.trim()));
        book.setImagePath(imagePath.trim());

        return book;
    }

    public boolean doesISBNExists(String ISBN)
    {
        ModelBooks DBBook = new ModelDatabaseBooks();
        return DBBook.doesISBNAlreadyExists(ISBN);
    }

    private boolean isNumerical(String s) {
        return s.matches("[+]?([0-9]*[.])?[0-9]+");
    }

    private boolean isISBNvalid(String s){ return s.matches("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})" +
            "[- 0-9X]{13}$|97[89]-[0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)" +
            "(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");}

    private boolean isASINvalid(String s)
    {
        return s.matches("\\b(([0-9]{9}[0-9X])|(B[0-9A-Z]{9}))\\b");
    }
}
