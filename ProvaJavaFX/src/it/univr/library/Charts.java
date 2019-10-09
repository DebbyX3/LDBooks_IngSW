package it.univr.library;

import java.util.List;

public class Charts
{
    private Integer rank;
    private Integer weeksIn;
    private String ISBN;
    private String title;
    private List<String> authors;
    // TODO: 09/10/2019 : mettere forse genre in oggetto Genre 
    private String genre;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getWeeksIn() {
        return weeksIn;
    }

    public void setWeeksIn(Integer weeksIn) {
        this.weeksIn = weeksIn;
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

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
