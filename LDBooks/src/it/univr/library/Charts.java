package it.univr.library;

import java.util.List;

public class Charts
{
    private Integer id;
    private Integer rank;
    private String ISBN;
    private String title;
    private List<Author> authors;
    private Genre genre;
    private String genreChart;
    private Integer weeksIn;
    private Category category;

    public Charts(Integer id, Integer rank, String ISBN, String title, List<Author> authors, Genre genre, String genreChart,
                  Integer weeksIn, Category category)
    {
        this.id = id;
        this.rank = rank;
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.genre = genre;
        this.genreChart = genreChart;
        this.weeksIn = weeksIn;
        this.category = category;
    }

    public Charts(){};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getGenreChart() {
        return genreChart;
    }

    public void setGenreChart(String genreChart) {
        this.genreChart = genreChart;
    }
}
