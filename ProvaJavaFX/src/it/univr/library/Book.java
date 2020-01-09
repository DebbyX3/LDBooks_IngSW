package it.univr.library;

import javafx.scene.image.Image;

import java.math.BigDecimal;
import java.util.List;

public class Book
{
    private String ISBN;
    private String title;
    private List<Author> authors;
    private String description;
    private Integer points;
    private BigDecimal price;
    private Integer publicationYear;
    private PublishingHouse publishingHouse;
    // TODO: 06/10/2019 : mettere Genre e non String (forse)
    private Genre genre;
    private Language language;
    private Integer maxQuantity;
    private Integer pages;
    private Format format;
    private String imagePath;

    public Book(){}

    public Book(String ISBN, String title, List<Author> authors, String description, Integer points, BigDecimal price, Integer publicationYear, PublishingHouse publishingHouse, Genre genre, Language language, Integer maxQuantity, Integer pages, Format format, String imagePath) {
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.points = points;
        this.price = price;
        this.publicationYear = publicationYear;
        this.publishingHouse = publishingHouse;
        this.genre = genre;
        this.language = language;
        this.maxQuantity = maxQuantity;
        this.pages = pages;
        this.format = format;
        this.imagePath = imagePath;
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder(ISBN + "\n" + title + "\n");

        for (Author element: authors)
            result.append(element.getNameSurname()).append(", ");

        return  result + "\n" +
                description + "\n" + points + "\n" + price + "\n" + publicationYear + "\n" + publishingHouse + "\n" +
                genre + "\n" + language + "\n" + maxQuantity + "\n" + pages + "\n" + format + "\n" + imagePath + "\n";
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setAuthors() {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
